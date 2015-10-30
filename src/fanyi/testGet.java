package fanyi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;

import org.ccnx.ccn.CCNHandle;
import org.ccnx.ccn.config.ConfigurationException;
import org.ccnx.ccn.config.SystemConfiguration;
import org.ccnx.ccn.impl.support.DataUtils;
import org.ccnx.ccn.impl.support.Log;
import org.ccnx.ccn.io.content.CCNNetworkObject;
import org.ccnx.ccn.io.content.ContentDecodingException;
import org.ccnx.ccn.io.content.Header.HeaderObject;
import org.ccnx.ccn.io.content.UpdateListener;
import org.ccnx.ccn.profiles.SegmentationProfile;
import org.ccnx.ccn.profiles.VersioningProfile;
import org.ccnx.ccn.profiles.metadata.MetadataProfile;
import org.ccnx.ccn.protocol.ContentName;
import org.ccnx.ccn.protocol.ContentObject;
import org.ccnx.ccn.protocol.Exclude;
import org.ccnx.ccn.protocol.ExcludeAny;
import org.ccnx.ccn.protocol.ExcludeComponent;
import org.ccnx.ccn.protocol.Interest;
import org.ccnx.ccn.protocol.MalformedContentNameStringException;
import org.ccnx.ccn.protocol.PublisherPublicKeyDigest;

public class testGet implements UpdateListener{
	
	
	protected HeaderObject _header = null;
	protected HeaderObject _oldHeader = null;
	protected boolean headerRequested() {
		return (null != _header);
	}
	protected void requestHeader(ContentName baseName, PublisherPublicKeyDigest publisher) 
			throws ContentDecodingException, IOException {
		if (headerRequested())
			return; // done already
		// Ask for the header, but update it in the background, as it may not be there yet.
		_header = new HeaderObject(MetadataProfile.headerName(baseName), null, null, publisher, null, this.handle);
		if (Log.isLoggable(Log.FAC_IO, Level.INFO))
			Log.info(Log.FAC_IO, "Retrieving header : " + _header.getBaseName() + " in background.");
		_header.updateInBackground(false, this);
		
		if (SystemConfiguration.OLD_HEADER_NAMES) {
			_oldHeader = new HeaderObject(MetadataProfile.oldHeaderName(baseName), null, null, publisher, null, this.handle);
			if (Log.isLoggable(Log.FAC_IO, Level.INFO))
				Log.info(Log.FAC_IO, "Retrieving header under old name: " + _oldHeader.getBaseName() + " in background.");
			_oldHeader.updateInBackground(false, this);
		}
	}
	
	
	public static final byte VERSION_MARKER = (byte)0xFD;
	public static final byte FF = (byte) 0xFF;
	public static final byte O1 = (byte) 0x01;
	public static final byte OO = (byte) 0x00;
	public static final byte [] FIRST_VERSION_MARKER = new byte []{VERSION_MARKER};
	public static final byte [] LAST_VERSION_MARKER  = new byte [] {VERSION_MARKER+1, OO, OO, OO, OO, OO, OO };
	protected ContentName _baseName = null;
	public static ContentName addVersion(ContentName name, long version) {
		// Need a minimum-bytes big-endian representation of version.
		byte [] vcomp = null;
		if (0 == version) {
			vcomp = FIRST_VERSION_MARKER;
		} else {
			vcomp = DataUtils.unsignedLongToByteArray(version, VERSION_MARKER);
		}
		return new ContentName(name, vcomp);
	}
	/**
	 * Control whether versions start at 0 or 1.
	 * @return
	 */
	public static final int baseVersion() { return 0; }
	
	
	public static void main(String[] args) throws MalformedContentNameStringException, ConfigurationException, IOException{

		
		CCNHandle handle = CCNHandle.open();
		System.out.println("请输入要查询的单词");
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()){
			String word = "ccnx:/fanyi/"+sc.next();
			ContentName cn =  ContentName.fromURI(word);
			testGet testget = new testGet(cn,0,handle,0);
			testget.start();
			testget.express();
		}

		
	
		
	//	handle.expressInterest(toGet, this.contentcallback);
	}
	
	
	ContentName contentName;
	CCNHandle handle;
	long devidePipeline;
	//long startIndex;
	long startIndex;
	long starttime;
	long Contentlength;
	long pipeline;
	String output;
	public boolean start;
	boolean getlast;
	int contentnumber ;
	int receive;
	//FileOutputStream fos = null;
	// BufferedWriter bw = null;
	 //File file;
	 java.io.FileWriter fw;
     java.io.PrintWriter pw;
	ContentCallback contentcallback = new ContentCallback(this);
	public testGet(ContentName contentName,int contentnumber, CCNHandle handle, int devidePipeline) {
		//super();
		this.contentName = contentName;
		this.contentnumber = contentnumber;
		this.handle = handle;
		this.devidePipeline = devidePipeline;
		this.startIndex = 0;
		this.start = false;
		//this.startIndex = startIndex;
		//this.contentcallback = contentcallback;
	}
	
	public void start(){
		 //this.file = new File(this.output);
		 //this.fos = new FileOutputStream(file);
		 //this.bw = new BufferedWriter(new OutputStreamWriter(fos));
		System.out.println("start");
		receive = 0;
		 this.start = true;
		 this.getlast = false;
		 this.startIndex = 0;
		 //this.starttime = 
	}
	public void stop(){
		System.out.println("stop");
		
		this.start = false;
		this.receive = 0;
		this.handle.close();
	
		//this.getlast = false;
	}
	public static Exclude acceptVersions(byte [] startingVersionComponent) {
		byte [] start = null;
		// initially exclude name components just before the first version, whether that is the
		// 0th version or the version passed in
		if ((null == startingVersionComponent) || VersioningProfile.isBaseVersionComponent(startingVersionComponent)) {
			start = new byte [] { VersioningProfile.VERSION_MARKER, VersioningProfile.OO, VersioningProfile.FF, VersioningProfile.FF, VersioningProfile.FF, VersioningProfile.FF, VersioningProfile.FF };
		} else {
			start = startingVersionComponent;
		}

		ArrayList<Exclude.Element> ees = new ArrayList<Exclude.Element>();
		ees.add(new ExcludeAny());
		ees.add(new ExcludeComponent(start));
		ees.add(new ExcludeComponent(LAST_VERSION_MARKER));
		ees.add(new ExcludeAny());

		Log.fine(Log.FAC_IO, "acceptVersions:  creating excludes {0} {1}", VersioningProfile.getVersionComponentAsLong(start), VersioningProfile.getVersionComponentAsLong(LAST_VERSION_MARKER));

		return new Exclude(ees);
	}
	/**
	 * express interest
	 *@param pipeline 	the number of interest emit once
	 *@param startIndex	begin from which interest
	 */
	//@SuppressWarnings("static-access")
	public void express() throws ConfigurationException, IOException{
		//ContentName argName = ContentName.fromURI("ccnx:/login.jsp");
		//System.out.println("express, startIndex = "+this.startIndex);
		
		if(this.start == false){
			return;
		}
		//System.out.println("start is true");
		//Interest toGet = new Interest(this.contentName);
		if(this.startIndex==0){
			//System.out.println("in the startIndex");
			//ArrayList<byte[]> excludeList = new ArrayList<byte[]>();
			ContentName firstVersionName = addVersion(this.contentName, baseVersion());
			Interest constructedInterest = Interest.last(firstVersionName, null, firstVersionName.count() - 1, 3,
					3, null);
			
			
			ContentObject result = null;
			this.starttime = System.currentTimeMillis();
			//System.out.println("request "+firstVersionName.toString()+" begin, time is: "+this.starttime);
			constructedInterest.answerOriginKind(0);
			boolean noGet = true;
			while(noGet){
				
				System.out.println("constructed:"+constructedInterest.toString());
				result = handle.get(constructedInterest, 10000);
				//System.out.println("get "+firstVersionName.toString()+" first, time is: "+(System.currentTimeMillis()-this.starttime));
				
				if (null != result){
					//did it verify?
					//if it doesn't verify, we need to try harder to get a different content object (exclude this digest)
					//make this a loop?
					
					if (!handle.defaultVerifier().verify(result)) {
						System.out.println("not verify!!! I don't want to copy more= =```");
						this.stop();
						return;
					}
					else{
						//System.out.println("content "+firstVersionName.toString()+"'s number "+SegmentationProfile.getSegmentNumber(result.getContentName())+" content receive, length is "+result.contentLength()+", time is:"+(System.currentTimeMillis()-this.starttime));
						_baseName = SegmentationProfile.segmentRoot(result.name());
						
					}
					noGet = false;
					break;
				}else{
					System.out.println("get failed");
					this.stop();
					return;
				}
			}
			
			//Interest toGet = new Interest(this.contentName);
			//handle.expressInterest(toGet, this.contentcallback);
			if(result.signedInfo().getFinalBlockID()==null){
				this.Contentlength = this.contentnumber+1;
				//this.pipeline = 1;
			}else{
				try {
					this.Contentlength=unsignedByteToInt(result.signedInfo().getFinalBlockID()[3])-1;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					this.Contentlength = this.contentnumber+1;
				}
					
		
				
				if(this.Contentlength==1){
					System.out.println(this.contentName.toString()+" "+(System.currentTimeMillis()-this.starttime));
					this.stop();
					return;
				}
				if(this.devidePipeline==0){
					this.pipeline = 1;
				}else{
					this.pipeline = (this.Contentlength-1)/this.devidePipeline;
					if(this.pipeline==0){
						this.pipeline = 1;
					}
				}
			}
			
			this.startIndex++;
			this.express();

			
			return;
		}
	
		for(long i=0;i<pipeline;i++){
			
			long num = this.startIndex;
			//System.out.println("request number "+num);
			this.startIndex++;
			if(this.startIndex > this.Contentlength){
				return ;
			}
			//ContentName interestName =  SegmentationProfile.segmentName(this.contentName, startIndex+i);
			Interest interest = SegmentationProfile.segmentInterest(_baseName, num, null);
			//Interest toGet = new Interest(interestName);
			interest.answerOriginKind(0);
			handle.expressInterest(interest, this.contentcallback);
		}
		
		
	}
	@Override
	public void newVersionAvailable(CCNNetworkObject<?> newVersion,
			boolean wasSave) {
		// TODO Auto-generated method stub
		System.out.println("what's this= =");
		
	}
	
	public static int unsignedByteToInt(byte b) {  
        return (int) b & 0xFF;  
    } 
	

	
}
