package fanyi;

import java.io.IOException;

import org.ccnx.ccn.CCNContentHandler;
import org.ccnx.ccn.config.ConfigurationException;
import org.ccnx.ccn.impl.support.DataUtils;
import org.ccnx.ccn.profiles.SegmentationProfile;
import org.ccnx.ccn.protocol.ContentName;
import org.ccnx.ccn.protocol.ContentObject;
import org.ccnx.ccn.protocol.Interest;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class ContentCallback implements CCNContentHandler{
	testGet testget;
	long calcu;
	//long pipeline;
	
	
	public Interest handleContent(ContentObject data, Interest interest) {
		// TODO Auto-generated method stub
		
		//System.out.println("call back"+data.toString());
		long number = SegmentationProfile.getSegmentNumber(data.getContentName());
		//testget.pw.println(System.currentTimeMillis()+"\t"+test.ThrNum+"\t"+testget.contentnumber+"\t"+(System.currentTimeMillis()-testget.starttime)+"\t"+testget.devidePipeline+"\t"+(testget.receive+1)+"\n");
		
		//System.out.println("content "+data.name().toString()+"'s number "+number+" content receive, length is "+data.contentLength()+", time is:"+(System.currentTimeMillis()-testget.starttime));
		testget.receive++;
		//System.out.println("receive:"+testget.receive);
		try {
			if(data.contentLength()==4096){
				calcu++;
				//System.out.println("calcu:"+calcu+" pipeline="+testget.pipeline);
				if(calcu==testget.pipeline||testget.receive==testget.Contentlength-1){
					if(testget.receive==testget.Contentlength-1){
						//System.out.println("testget.Contentlength and receive"+(testget.Contentlength-1)+testget.receive);
						System.out.println(testget.contentName.toString()+" "+(System.currentTimeMillis()-testget.starttime));
						testget.stop();
						calcu = 0;
					}
					else{
						testget.express();
						calcu = 0;
					}
				}
				
			}
			else{
				calcu++;
				//System.out.println("else calcu:"+calcu);
				testget.getlast = true;
				if(testget.receive==testget.Contentlength-1){
					//System.out.println("testget.Contentlength and receive"+(testget.Contentlength-1)+testget.receive);
					System.out.println(testget.contentName.toString()+" "+(System.currentTimeMillis()-testget.starttime));
					testget.stop();
					calcu = 0;
				}
			}
		} catch (ConfigurationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public ContentCallback(testGet testget) {
		super();
		this.testget = testget;
		this.calcu = 0;
		//this.pipeline = testget.pipeline;
	}



}
