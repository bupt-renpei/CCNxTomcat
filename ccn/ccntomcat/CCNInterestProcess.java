package ccntomcat;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SignatureException;

import org.apache.tomcat.util.buf.MessageBytes;
import org.apache.tomcat.util.http.mapper.Mapper;
import org.apache.tomcat.util.http.mapper.MappingData;
import org.ccnx.ccn.CCNHandle;
import org.ccnx.ccn.profiles.SegmentationProfile;
import org.ccnx.ccn.protocol.ContentName;
import org.ccnx.ccn.protocol.ContentObject;
import org.ccnx.ccn.protocol.Interest;
import org.ccnx.ccn.protocol.MalformedContentNameStringException;
import org.ccnx.ccn.protocol.PublisherPublicKeyDigest;

import ccntomcat.CCNTrieTree.Vertex;

public class CCNInterestProcess implements Runnable{
	private Interest interest;
	private CCNHandle handle;
	CCNConnector ccnconnector;
	
	public CCNInterestProcess(Interest interest, CCNHandle handle,CCNConnector ccnconnector) {
		super();
		this.interest = interest;
		this.handle = handle;
		this.ccnconnector = ccnconnector;
	}
	
	/*
	 * //Tu: if not push request, judge the request Type.
	 * @see org.ccnx.ccn.CCNInterestHandler#handleInterest(org.ccnx.ccn.protocol.Interest)
	 * 
	 * @para interest's realUri
	 * @return MappingData
	 */
	public MappingData judgeRequest(String realUri){
		Mapper mapper = ccnconnector.mapper;
		MappingData mappingdata = new MappingData();
		MessageBytes serverName = null,Uri = null;
		serverName = MessageBytes.newInstance();
		Uri = MessageBytes.newInstance();
		serverName.setChars("localhost".toCharArray(), 0, "localhost".length());
		Uri.setChars(realUri.toCharArray(),0,realUri.length());
		try {
			System.out.println("serverName:"+serverName.toString()+" Uri:"+Uri.toString()+" realUri is:"+realUri);
			mapper.map(serverName, Uri, mappingdata);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("请求类型无法确定");
			return null;
		}
		return mappingdata;
	}
	
	public boolean checkUri(String uri){
		
		if (uri.indexOf("%FD") != -1 || uri.indexOf("%00") != -1
				|| uri.indexOf("%C1") != -1 || uri.indexOf("%EA") != -1
				|| uri.indexOf("%DA") != -1 || uri.equals("/") ) {
			return false;
		}
		return true;

	}
	
	@Override
	public void run() {
		String uri = ccnconnector.getUri(interest);
		String realUri = ccnconnector.getRealUri(uri);
		//Tu:if this interest is express by this handler, escape this.
		if(this.ccnconnector.waitForMatch!=null){
			if(realUri.compareTo(this.ccnconnector.waitForMatch)==0){
				//System.out.println("Don't accept the request:"+this.waitForMatch);
				this.ccnconnector.waitForMatch = null;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				synchronized(ccnconnector.receiveInterest){
					ccnconnector.receiveInterest.remove(interest.toString());
				}
				//thrnumber.decrementAndGet();
				return;
			}
		}
		
	
		
		//Tu:if this is a push request, receive the file.
		String post_noti = "post_notification";
		if(realUri.indexOf("/"+post_noti+"/")!=-1){
			System.out.println("push require");
			int cut = realUri.indexOf(post_noti);
			String aftercut = realUri.substring(cut+post_noti.length());
			System.out.println(aftercut);
			//Tu: this push hashtable record the 3 times request, avoid repeating overwrite.
			if(ccnconnector.pushHash.containsKey(aftercut))
			{
				
				if(ccnconnector.pushHash.get(aftercut)==2){
					ccnconnector.pushHash.remove(aftercut);
				}
				else{
					ccnconnector.pushHash.put(aftercut, ccnconnector.pushHash.get(aftercut)+1);
				}
				return;
			}
			ccnconnector.pushHash.put(aftercut,1);
			//Tu: Start a new thread to get file from the client.
			System.out.println("insert from file:"+aftercut);
			this.ccnconnector.waitForMatch = aftercut;
			//aftercut = aftercut.substring(aftercut.indexOf("/path")+5);
			System.out.println("insert to file:"+ccnconnector.rootPath+"/receive");
			String toInsert = ccnconnector.rootPath+"/receive"+aftercut;
			CCNGetfileThread getfile = new CCNGetfileThread(aftercut,toInsert);
			Thread t1 = new Thread(getfile);
			t1.start();
			
			synchronized(ccnconnector.receiveInterest){
				ccnconnector.receiveInterest.remove(interest.toString());
			}
			//thrnumber.decrementAndGet();
			return;
		}
	
		//Tu: if not push request, judge the request Type.
		//System.out.println("Format:"+getFileFormat(realUri)+"  realUri:"+realUri);
		MappingData mappingdata = judgeRequest(realUri);
		System.out.println("after mapping "+mappingdata.wrapper.toString());
		//Tu: if results exist, return the results from ccncache.
		
		
		String path =ccnconnector.testhash.parsePath(uri,3);
		String trieTreeName,hashName;
		String paraMeter="para";
		if(path.indexOf("/"+paraMeter+"/")!=-1)
		{
			hashName = path.substring(0,path.indexOf("/para/"));
			trieTreeName = CCNConnector.parsePara(path.substring(path.indexOf("/"+paraMeter+"/")+paraMeter.length()+1));
		}
		else
		{
			hashName = path;
			trieTreeName = "";
		}
		System.out.println("hash path:"+"/localhost/root"+hashName+" trieTree:"+trieTreeName);
		Vertex vertex = ccnconnector.testhash.FindPath("/localhost/ROOT"+hashName, trieTreeName);
		///seg7test1.jsp/%C1.META.M/.header/%FD%05Mk%1A%3D%81/%00
		if (vertex!=null) {
				System.out.println("Find Vertex success! hash path:"+"/localhost/root"+hashName+" trieTree:"+trieTreeName);
				CCNJumpList ccnjumplist = vertex.getVertexJumpList();
				try {
					Thread.sleep(1);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(ccnjumplist!=null){
					
						
						
					//ccnjumplist.printJumpList();
					String insertToFind = interest.name().toString();
					ContentObject co;
					System.out.println("455 line insertToFind is "+insertToFind);
					if (checkUri(uri)){
						System.out.println("457 line after check");
						//int numOfCo = ccnjumplist.size();
						ContentName cn = null;
						try {
							cn = ContentName.fromURI(insertToFind);
							System.out.println("ContentName cn is:"+cn.toString());
						} catch (MalformedContentNameStringException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//for(int i=0;i<numOfCo-1;i++){
						String JumpListToFind;
						if(cn.toString().indexOf("%")==-1){
							ContentName interestName =  SegmentationProfile.segmentName(cn, 0);
							JumpListToFind = interestName.toString();
						}else{
							JumpListToFind = insertToFind;
						}
						CCNFileNode filenode = ccnjumplist.findFileNode(JumpListToFind);
						if(filenode!=null){
							System.out.println("507 ContentObject find success");
							co = filenode.getCob();
						}else{
							System.out.println("ContentObject find failed");	
							synchronized(ccnconnector.receiveInterest){
								ccnconnector.receiveInterest.remove(interest.toString());
							}
							//thrnumber.decrementAndGet();
							return;
						}
						try {
							PublisherPublicKeyDigest publisher = this.handle.keyManager().getDefaultKeyID();
							Key signingKey = this.handle.keyManager().getSigningKey(publisher);									
							try {
								co.sign(signingKey);
							} catch (InvalidKeyException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SignatureException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							this.handle.put(co);
							System.out.println("length:"+co.contentLength());
							byte[] lengthOfCo = ccnconnector.intToByteArray1(ccnjumplist.size());
							co.signedInfo().setFinalBlockID(lengthOfCo);
							System.out.println("put ContentObject success");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("return true");
	
						synchronized(ccnconnector.receiveInterest){
							ccnconnector.receiveInterest.remove(interest.toString());
						}
						return;
					}
					System.out.println("interest to find is "+ insertToFind +"\nafter process is "+ccnconnector.removeVersion(insertToFind));
					CCNFileNode filenode = ccnjumplist.findFileNode(ccnconnector.removeVersion(insertToFind));
						
					if(filenode!=null){
						System.out.println("ContentObject find success");
						co = filenode.getCob();
					}else{
						synchronized(ccnconnector.receiveInterest){
							ccnconnector.receiveInterest.remove(interest.toString());
						}
						//thrnumber.decrementAndGet();
						System.out.println("ContentObject find failed");
						return;
					}
					try {
						PublisherPublicKeyDigest publisher = this.handle.keyManager().getDefaultKeyID();
						Key signingKey = this.handle.keyManager().getSigningKey(publisher);
						try {
							co.sign(signingKey);
						} catch (InvalidKeyException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SignatureException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						this.handle.put(co);
						System.out.println("put ContentObject success");
						//ccnjumplist.setTimes(ccnjumplist.getTimes()+1);
						byte[] lengthOfCo = ccnconnector.intToByteArray1(ccnjumplist.size());
						co.signedInfo().setFinalBlockID(lengthOfCo);
						synchronized(ccnconnector.receiveInterest){
							ccnconnector.receiveInterest.remove(interest.toString());
						}
						//thrnumber.decrementAndGet();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
				}
			}
			//Tu:if it is GET request
			if(realUri.indexOf("/"+paraMeter+"/")!=-1){
				ccnconnector.testhash.addPath(2, "/localhost/ROOT"+hashName, trieTreeName);
				ccnconnector.ccnendpoint.processInterest(interest);

				synchronized(ccnconnector.receiveInterest){
					ccnconnector.receiveInterest.remove(interest.toString());
				}
				return;
			}
			//Tu: process the request and put results into ccncache 
			if(mappingdata.wrapper==null){
				if(realUri.indexOf(".jsp")!=-1||realUri.indexOf("servlet")!=-1){
					if (checkUri(uri)) {
						ccnconnector.testhash.addPath(2, "/localhost/ROOT"+hashName, trieTreeName);
						ccnconnector.ccnendpoint.processInterest(interest);
						synchronized(ccnconnector.receiveInterest){
							ccnconnector.receiveInterest.remove(interest.toString());
						}
						return;
					}
				}
			}
			if(!mappingdata.wrapper.toString().equalsIgnoreCase("StandardEngine[Catalina].StandardHost[localhost].StandardContext[].StandardWrapper[default]")){
				if (checkUri(uri)) {
					//tu 20141224 modify 
					ContentName CNForRegister = null;
					try {
						CNForRegister = ContentName.fromURI(uri);
						ccnconnector._filteredNames.add(CNForRegister);
				
					} catch (MalformedContentNameStringException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ccnconnector._filteredNames.add(CNForRegister);
					ccnconnector.testhash.addPath(2, "/localhost/ROOT"+hashName, trieTreeName);
					ccnconnector.ccnendpoint.handler.process(interest);
					Vertex firstVertex = ccnconnector.testhash.FindPath("/localhost/ROOT"+hashName, trieTreeName);
					if (firstVertex!=null) {
						CCNJumpList jumplist = firstVertex.getVertexJumpList();
						if(jumplist!=null){
							String firstToFind = interest.name().toString();
							ContentName firstcn = null;
							try {
								firstcn = ContentName.fromURI(firstToFind);
								System.out.println("First ContentName cn is:"+firstcn.toString());
							} catch (MalformedContentNameStringException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							ContentName finterestName =  SegmentationProfile.segmentName(firstcn, 0);
							String FirstJumpListToFind = finterestName.toString();
							System.out.println("First JumpList to find ContentName is:"+FirstJumpListToFind);
							CCNFileNode filenode = jumplist.findFileNode(FirstJumpListToFind);
							ContentObject firstCO;
							if(filenode.getFileName().equals(FirstJumpListToFind)){
								System.out.println("First success!");
								firstCO = filenode.getCob();
								byte[] lengthOfCo = ccnconnector.intToByteArray1(jumplist.size());
								firstCO.signedInfo().setFinalBlockID(lengthOfCo);

							}else{
							synchronized(ccnconnector.receiveInterest){
								ccnconnector.receiveInterest.remove(interest.toString());
							}
							return;
						}
						try {
							PublisherPublicKeyDigest publisher = this.handle.keyManager().getDefaultKeyID();
							Key signingKey = this.handle.keyManager().getSigningKey(publisher);
							try {
								firstCO.sign(signingKey);
							} catch (InvalidKeyException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SignatureException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							this.handle.put(firstCO);
							

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						synchronized(ccnconnector.receiveInterest){
							ccnconnector.receiveInterest.remove(interest.toString());
						}
						//thrnumber.decrementAndGet();
						return;
					}
				}
				//CCNCacheDelThread ccncachedelete = new CCNCacheDelThread(testhash,"/localhost/ROOT"+hashName,trieTreeName,100000);
				synchronized(ccnconnector.receiveInterest){
					ccnconnector.receiveInterest.remove(interest.toString());
				}
				//thrnumber.decrementAndGet();
				return;
			}
		}
					//Vertex vertex = testhash.FindPath("localhost/root"+trieTreeName);

			//}
			
		
	}
	
	
}