package ccntomcat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SignatureException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.coyote.Response;
import org.apache.tomcat.util.buf.MessageBytes;
import org.apache.tomcat.util.http.mapper.Mapper;
import org.apache.tomcat.util.http.mapper.MappingData;
import org.ccnx.ccn.CCNHandle;
import org.ccnx.ccn.CCNInterestHandler;
import org.ccnx.ccn.config.ConfigurationException;
import org.ccnx.ccn.io.CCNFileOutputStream;
import org.ccnx.ccn.io.CCNOutputStream;
import org.ccnx.ccn.io.RepositoryFileOutputStream;
import org.ccnx.ccn.io.RepositoryOutputStream;
import org.ccnx.ccn.profiles.SegmentationProfile;
import org.ccnx.ccn.protocol.ContentName;
import org.ccnx.ccn.protocol.ContentObject;
import org.ccnx.ccn.protocol.Interest;
import org.ccnx.ccn.protocol.MalformedContentNameStringException;
import org.ccnx.ccn.protocol.PublisherPublicKeyDigest;
import org.ccnx.ccn.utils.CommonParameters;

import ccntomcat.CCNTrieTree.Vertex;

public class CCNConnector extends QueuedInterestHandler<Interest> implements CCNInterestHandler {

	
	protected CCNHandle ccnhandle;//, ccnhandle1, ccnhandle2, ccnhandle3,ccnhandle4, ccnhandle5;
	protected ContentName ccnprefix;//, ccnprefix1, ccnprefix2, ccnprefix3,ccnprefix4, ccnprefix5;

	protected CCNOutputStream ccnostream;
	
	protected CCNConcurrentHashMap<String, Interest> uriHashMap;
	protected Hashtable<String,Integer> pushHash;

	protected CCNConcurrentHashMap<String,Long> repoHashMap;
	
	protected HashSet<ContentName> _filteredNames = new HashSet<ContentName>();

	protected testHash testhash = testHash.getTestHash();
	
	protected int ccncount = 0;
	
	protected int RTTime = 100;
	
	protected Mapper mapper;
	
	protected boolean running;
	
	public Mapper getMapper() {
		return mapper;
	}

	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}
	protected String waitForMatch = null;
	
	protected String workPath = null;

	public boolean isPreLoad = false;
	
	public CCNPreLoad ccnPreLoad = null;
	
	public CCNStaticContentWatch ccnContentWatch = null;
	//protected String workPath = null;
	protected String rootPath;
	public String getRootPath() {
		return rootPath;
	}


	protected File rootFilePath;
	public CCNPreLoad getCcnPreLoad() {
		return ccnPreLoad;
	}

	public CCNConnector() {
		this.uriHashMap = new CCNConcurrentHashMap<String,Interest>(1024,0.75f,1024);
		this.repoHashMap = new CCNConcurrentHashMap<String,Long>(1024,0.75f,1024);
		int Threadnum = NUMOFTHREAD;
		this.pool = Executors.newFixedThreadPool(Threadnum);
		String dirPath = System.getProperty("user.dir");
		/*
		System.out.println("-----------------1th workPath----------------------");

		System.out.println("workPath is "+dirPath);

		System.out.println("-----------------1th workPath----------------------");
		*/
		workPath = dirPath.replaceAll("bin", "webapps");
		/*
		System.out.println("-----------------2th workPath----------------------");

		System.out.println("workPath is "+workPath);

		System.out.println("-----------------2th workPath----------------------");
		*/
		//jar
		//rootPath = workPath + "/ROOT";
		//local computer
		rootPath = workPath + "/webapps/ROOT";

		System.out.println("-----------------rootPath----------------------");

		System.out.println("rootPath is "+rootPath);
		
		rootFilePath = new File(rootPath);
		
		this.running = true;
		this.pushHash = new Hashtable<String,Integer>();
	}
	
	public void destroy(){
		this.running = false;
		this.ccnhandle.close();
		this.Shutdown();
	}
	public CCNConcurrentHashMap<String, Long> getRepoHashMap() {
		return repoHashMap;
	}

	public void setRepoHashMap(CCNConcurrentHashMap<String, Long> repoHashMap) {
		this.repoHashMap = repoHashMap;
	}
	
	public CCNConcurrentHashMap<String, Interest> getUriHashMap() {
		return uriHashMap;
	}
	public void setUriHashMap(CCNConcurrentHashMap<String, Interest> uriHashMap) {
		this.uriHashMap = uriHashMap;
	}
	
	
	protected CCNEndpoint ccnendpoint;

	public void setCcnendpoint(CCNEndpoint ccnendpoint) {
		this.ccnendpoint = ccnendpoint;
	}

	public boolean openCcnHandle() {
		try {
			ccnhandle = CCNHandle.open();
			ccnhandle.setTesthash(this.testhash);
			// ccnhandle1 = CCNHandle.open();
			/*
			 * ccnhandle2 = CCNHandle.open(); ccnhandle3 = CCNHandle.open();
			 * ccnhandle4 = CCNHandle.open(); ccnhandle5 = CCNHandle.open();
			 */
			ccnprefix = ContentName.fromURI(Constants.DEFAULT_CCN_URI);
			// ccnprefix1 = ContentName.fromURI("Constants.DEFAULT_CCN_URI");
			/*
			 * ccnprefix2 = ContentName.fromURI("ccnx:/app2"); ccnprefix3 =
			 * ContentName.fromURI("ccnx:/app3"); ccnprefix4 =
			 * ContentName.fromURI("ccnx:/app4"); ccnprefix5 =
			 * ContentName.fromURI("ccnx:/app5");
			 */
			ccnhandle.registerFilter(ccnprefix, this);
			// ccnhandle1.registerFilter(ccnprefix1, this);
			/*1;fps
			 * ccnhandle2.registerFilter(ccnprefix2, this);
			 * ccnhandle3.registerFilter(ccnprefix3, this);
			 * ccnhandle4.registerFilter(ccnprefix4, this);
			 * ccnhandle5.registerFilter(ccnprefix5, this);
			 */
			ccnPreLoad = new CCNPreLoad(ccnhandle);
			
			ccnContentWatch = new CCNStaticContentWatch(ccnPreLoad,rootFilePath,this);

			ccnContentWatch.startContentWatch();	
			
			
			return true;
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedContentNameStringException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}/*catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/ catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	public CCNHandle getCcnhandle() {
		return ccnhandle;
	}

	public ContentName getCcnPrefix() {
		try {
			ccnprefix = ContentName.fromURI(Constants.DEFAULT_CCN_URI);
		} catch (MalformedContentNameStringException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ccnprefix;
	}

	public String getOriginalUri(Interest interest) {
		ContentName contentName = interest.getContentName();

		ContentName fileNamePostfix = contentName.postfix(getCcnPrefix());

		String originalUri = fileNamePostfix.toString();

		return originalUri;
	}
	
	public String getUri(Interest interest) {
		String uri = getOriginalUri(interest);
		// int index1 = uri.indexOf("-00"); //nan 2013-11-17 update
		int index1 = uri.indexOf("=00");
		int index2 = uri.indexOf("=FD");
		// int index3 = uri.ind
		int index = 0;
		if (index1 != -1 && index2 != -1) {
			index = (index1 < index2 ? index1 : index2);
		} else if (index1 != -1 && index2 == -1) {
			index = index1;
		} else if (index1 == -1 && index2 != -1) {
			index = index2;
		} else if (index1 == -1 && index2 == -1) {
			index = -1;
		}
		if (index != -1) {
			uri = uri.substring(0, index - 1);
		}
		return uri;
	}
	
	/*
	 * Tu20141226 modify
	 * //Constants.QUESTION为URI中的参数，判断参数之前的字符串。
	 * @para interest's uri
	 * @return realuri
	 */
	public String getRealUri(String uri){
		int questionPos = uri.indexOf(Constants.QUESTION);
		//String realUri = null;
		if (questionPos >= 1) {
			return uri.substring(0, questionPos - 1);
		} else {
			return uri;
		}
	}

	

	public CCNOutputStream createCCNOutputStream(Interest interest,
			Response response) {
		CCNOutputStream ccnostream = null;
		ContentName contentname;
		try {
			contentname = ContentName.fromURI(getUri(interest));
			try {
				ccnostream = new CCNFileOutputStream(contentname, ccnhandle);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ccnostream.addOutstandingInterest(interest);
		} catch (MalformedContentNameStringException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ccnostream;

	}

	public boolean flush(byte[] buf, int off, int len) {
		try {
			System.out.println("ccnconncector 337 flush()");
			ccnostream.write(buf, off, len);
			if (len < Constants.BLOCK_SIZE) {
				ccnostream.flush();
				ccnostream.close();
				return true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	

	
	@Override
	public boolean handleInterest(Interest interest){
		// TODO Auto-generated method stub
		//Yukai Tu Modify
		System.out.println("begin handleInterest");
		if (isPreLoad) {
			if(this._isRunning==false){
				this._isRunning = true;
				Thread t = new Thread(this);
				t.start();
			}
			if(interest.toString().indexOf("/selfreg")!=-1){
				return false;
			}
			System.out.println("add Interest");
			this.addInterest(interest);
			return true;
		}
		return false;
			
	}
	
	public static String parsePara(String para)
	{
		String result="";
		// /username/xiaoxiaff/password/dd
		while(true)
		{
			if(para.indexOf('/')!=-1){
				String first = para.substring(1);
				if(first.indexOf('/')!=-1){
					result +=first.substring(0,first.indexOf('/'));
					result +="=";
					System.out.println(result);
					String second = first.substring(first.indexOf('/')+1);
					System.out.println(second);
					if(second.indexOf('/')!=-1){
						result+=second.substring(0,second.indexOf('/'))+"&";
					//	System.out.println(result);
					//	System.out.println(second);
						para = second.substring(second.indexOf('/'));
					//	System.out.println(para);
					}
					else
					{
						result+=second;
					//	System.out.println(result);
						System.out.println(second);
						return result;
					}
					
					
				}
				else{
					return "false para";
				}
			}
		}
		
	}


	public String getFileFormat(String fileName){
		
		int index = fileName.indexOf(".");
		return fileName.substring(index + 1);
		
	}
	
	public String removeVersion(String str){
		//ccnx:/index.jsp/%FD%DS%DSF/%00%01
		int begin = str.indexOf("%FD");
		if(begin==-1){
			return str;
		}
		int last = begin + str.substring(begin).indexOf('/');
		System.out.println(str.substring(begin));
		System.out.println(str.substring(begin,last));
		if(last == begin-1)
			return str;
		return str.replaceAll(str.substring(begin-1,last+1),"/");
	}
	
	class CCNPreLoad {


		protected CCNOutputStream ccnostream;
		protected CCNHandle handle;
		FileWriter writer = null;

		public CCNPreLoad(CCNHandle handle) {
			this.handle = handle;
			CommonParameters.unversioned = false;			
			CommonParameters.rawMode = false;
			
			//long startTime = System.currentTimeMillis();
			/*
			try {
				writer = new FileWriter("/usr/test/tomcatlog");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			*/
			try {
				showAllFiles(rootFilePath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//writeLog(startTime);
			
			isPreLoad = true;
			
			this.handle.getTesthash().setPreLoad(true);
			
		}

		public void writeLog(long startTime){
			
			long completeTime = System.currentTimeMillis();
			long responseTime = completeTime - startTime;
			try {
				writer.write(responseTime+"\n");
				writer.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public void showAllFiles(File dir) throws Exception {
			File[] fs = dir.listFiles();
			for (int i = 0; i < fs.length; i++) {
				System.out.println(fs[i].getAbsolutePath());
				// System.out.println(fs[i].getParent());

				if (fs[i].isDirectory()) {
					if(false == fs[i].getName().equals("WEB-INF") ){
						showAllFiles(fs[i]);						
					}
				} else {// if this is a static file			
				//avoid to write dynamic file to repo
					if(false == getFileFormat(fs[i].getName()).equals("class")){
					String format = getFileFormat(fs[i].getName());
					if(false == format.equals("jsp") && false == format.equals("JSP")){
						preWrite(fs[i]);	
					}
				}
			}
			
			}
		}
		
		public void preWrite(File file) throws MalformedContentNameStringException{
			//step1:  get dabsolute path, eg: /usr/workspace/Tomcat6/webapps/ROOT/app1/file1
			String absPath = file.getAbsolutePath();

			
			//setp2: get relative path, eg: /app1/file1
			String relaPath = absPath.substring(rootPath.length());
			//step3: write file to repository;
			ContentName contentName = ContentName.fromURI("ccnx:/"
					+ relaPath);
			writeToRepo(file.getAbsolutePath(), contentName);
			recordAdd("ccnx:/"+relaPath,System.currentTimeMillis());
		}
		
		public void watchWrite(String file) throws MalformedContentNameStringException{
			//step1:  get dabsolute path, eg: /usr/workspace/Tomcat6/webapps/ROOT/app1/file1
			//file is the absolute
			//setp2: get relative path, eg: /app1/file1
			//System.out.println("workpath= "+workPath);
			String relaPath = file.substring(rootPath.length());
			//step3: write file to repository;
			ContentName contentName = ContentName.fromURI("ccnx:/"
					+ relaPath);
			writeToRepo(file, contentName);
			recordAdd("ccnx:/"+relaPath,System.currentTimeMillis());
		}
		
		public String getFileInfo(File file){
			
			String fileName = file.getName();//get file Name
			int index = fileName.lastIndexOf(".");
			String fileInfo = null;
			if(index !=-1){
				 fileInfo = "Type:" + CCNProcessor.MapFileFormat(fileName.substring(index + 1))
						+ "\nLength:" + file.length() + "\r\n";				
			}else{ // no format
				 fileInfo = "Type:" + "text/html"
						+ "\nLength:" + file.length() + "\r\n";			
			}
			return fileInfo;
		}
		
		public void recordAdd(String str, Long time){
			
			repoHashMap.addObject(str, time);
		
		}
		
		public void writeToRepo(String fileName,
				ContentName contentName) {			
			try {
				
				doPut(handle, fileName, contentName);
				
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		protected void doPut(CCNHandle handle, String fileName,
				ContentName nodeName) throws IOException, InvalidKeyException,
				ConfigurationException {
			InputStream is;

			File theFile = new File(fileName);
			if (!theFile.exists()) {
				System.out.println("No such file: " + theFile.getName());
			}
			is = new FileInputStream(theFile);

			CCNOutputStream ostream;

			if (CommonParameters.rawMode) {
				if (CommonParameters.unversioned)
					ostream = new CCNOutputStream(nodeName, handle);
				else
					ostream = new CCNFileOutputStream(nodeName, handle);
			} else {
				if (CommonParameters.unversioned)
					ostream = new RepositoryOutputStream(nodeName, handle,
							CommonParameters.local);
				else
					ostream = new RepositoryFileOutputStream(nodeName, handle,
							CommonParameters.local);
			}
			/*if (CommonParameters.timeout != null)
				ostream.setTimeout(CommonParameters.timeout);
			*/
			do_write(ostream, is, "");

			return;
		}

		private void do_write(CCNOutputStream ostream, InputStream is,
				String fileInfo) throws IOException {
			int size = CommonParameters.BLOCK_SIZE;
			int readLen = 0;
			byte[] buffer = new byte[CommonParameters.BLOCK_SIZE];
			byte[] fileInfoByte = fileInfo.getBytes();
			//add file info to buffer, and the write it to ostream
			System.arraycopy(fileInfoByte, 0, buffer, 0, fileInfoByte.length);
			ostream.write(buffer, 0, fileInfoByte.length);
			while ((readLen = is.read(buffer, 0, size)) != -1) {
				ostream.write(buffer, 0, readLen);
			}
			ostream.close();
		}
	}
	
	 public static byte[] intToByteArray1(int i) {   
		  byte[] result = new byte[4];   
		  result[0] = (byte)((i >> 24) & 0xFF);
		  result[1] = (byte)((i >> 16) & 0xFF);
		  result[2] = (byte)((i >> 8) & 0xFF); 
		  result[3] = (byte)(i & 0xFF);
		  return result;
		 }
	 
	class FutureWatch implements Runnable{
		Future future;
		CCNConnector ccnconnector;
		HashSet<String> receiveInterest;
		String InterestName;
		final long overtime = 10000;
		FutureWatch(Future future,CCNConnector ccnconnector,String InterestName){
			this.future = future;
			this.ccnconnector = ccnconnector;
			this.receiveInterest = ccnconnector.receiveInterest;
			this.InterestName = InterestName;
		}
		public void run(){
			try {
				future.get(overtime,TimeUnit.MILLISECONDS);
				synchronized (this.receiveInterest) {
					if(this.receiveInterest.contains(this.InterestName))
						this.receiveInterest.remove(this.InterestName);
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Interrupted");
				synchronized (this.receiveInterest) {
				this.receiveInterest.remove(this.InterestName);
				}
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Timeout");
				synchronized (this.receiveInterest) {
				this.receiveInterest.remove(this.InterestName);
				}
			}
		}
	}
	protected void process(Interest interest) {
		// TODO Auto-generated method stub
		
		//this.thrnumber.incrementAndGet();
		//System.out.println("thread number is "+this.thrnumber.get());
		CCNInterestProcess processInterest = new CCNInterestProcess(interest, this.getCcnhandle(), this);
		Thread t = new Thread(processInterest);
		Future future = this.pool.submit(t);
		FutureWatch futureWatch = new FutureWatch(future,this,interest.toString());
		Thread thread = new Thread(futureWatch);
		thread.start();
		
		}
		
		
		
	
}
