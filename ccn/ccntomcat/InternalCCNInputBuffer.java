package ccntomcat;

import java.io.EOFException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.coyote.InputBuffer;
import org.apache.coyote.Request;
import org.apache.tomcat.util.buf.ByteChunk;
import org.ccnx.ccn.protocol.Interest;

/**
 * Parse CCN Interest Header
 * 
 * @author Nan GuoShun
 * 
 */

public class InternalCCNInputBuffer implements InputBuffer {

	protected Interest interest = null;
	protected Request request;
	protected String uri;
	protected String originalUri;
	protected CCNConnector ccnConnector;

	public void setCCNConnector(CCNConnector ccnInternalHandler) {
		this.ccnConnector = ccnInternalHandler;
	}

	public String getOriginalUri() {
		return originalUri;
	}

	public void setOriginalUri(String originalUri) {
		this.originalUri = originalUri;
	}

	/**
	 * Pointer to the current read buffer.
	 */
	protected byte[] buf;

	public InternalCCNInputBuffer(Request request, int headerBufferSize) {

		this.request = request;
		buf = new byte[headerBufferSize];

		/*
		 * try { ccnprefix = ContentName.fromURI(Constants.DEFAULT_CCN_URI); }
		 * catch (MalformedContentNameStringException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		/*
		 * headers = request.getMimeHeaders();
		 * 
		 * buf = new byte[headerBufferSize];
		 * 
		 * inputStreamInputBuffer = new InputStreamInputBuffer();
		 * 
		 * filterLibrary = new InputFilter[0]; activeFilters = new
		 * InputFilter[0]; lastActiveFilter = -1;
		 * 
		 * parsingHeader = true; swallowInput = true;
		 */
	}

	@Override
	public int doRead(ByteChunk chunk, Request request) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setInterest(Interest interest) {
		// TODO Auto-generated method stub
		this.interest = interest;
	}
	//Tu20140914
	public void parsePara(String para) {
		byte[] paraByte ;
		paraByte = para.getBytes();


		request.query().setBytes(paraByte, 0, paraByte.length);
		
	}

	public void parseRequet() {
		// TODO Auto-generated method stub

		/**
		 * set the method to adapt with Tomcat container
		 */
		byte[] methodB = new byte[Constants.METHOD_LENGHT];
		methodB = Constants.REQUEST_METHOD.getBytes();


		request.method().setBytes(methodB, 0, methodB.length);
		

		/**
		 * parse uri
		 * 
		 */
		// int index =0;
		int length = buf.length;
		for (int i = 0; i < length; i++) {
			buf[i] = 0;
		}
		uri = ccnConnector.getUri(interest);

		// step 1 : calculate ? position.
		// step 2 : copy uri to uriMB and param to queryMB
		int questionPos = uri.indexOf("/para/");
		String realUri = null;
		if(questionPos >= 1){
			realUri = uri.substring(0, questionPos);			
		}else{
			realUri = uri;
		}

		System.out.println("realUri is : "+realUri);
		byte[] realUribuf = realUri.getBytes();
		String paramUri = null;
		if (questionPos >= 0) {
			
			
				paramUri = uri.substring(questionPos+5);
			
			
			System.out.println("before decode, paramUri is : "+paramUri);
			String result="";
			try {
				
			paramUri = java.net.URLDecoder.decode(paramUri, "UTF-8");
			//paramUri = CCNConnector.parsePara(paramUri);
			
			// /username/xiaoxiaff/password/dd
			while(true)
			{
				if(paramUri.indexOf('/')!=-1){
					String first = paramUri.substring(1);
					if(first.indexOf('/')!=-1){
						result +=first.substring(0,first.indexOf('/'));
						result +="=";
						//System.out.println(result);
						String second = first.substring(first.indexOf('/')+1);
						//System.out.println(second);
						if(second.indexOf('/')!=-1){
							result+=second.substring(0,second.indexOf('/'))+"&";
						//	System.out.println(result);
						//	System.out.println(second);
							paramUri = second.substring(second.indexOf('/'));
						//	System.out.println(para);
						}
						else
						{
							result+=second;
						//	System.out.println(result);
							//System.out.println(second);
							break;
						}
						
						
					}
					else{
						result="false";
						break;
					}
				}
			}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			
			System.out.println("after decode, paramUri is : "+result);
			
			byte[] paramUribuf = result.getBytes();
			System.arraycopy(realUribuf, 0, buf, 0, realUribuf.length);
			if(result.endsWith("...")==false){
				request.queryString().setBytes(paramUribuf, 0, paramUribuf.length);				
			}
			request.requestURI().setBytes(realUribuf, 0, realUribuf.length);

		} else {
			/**
			 * nan: security check and sometimes it is useless ?
			 */
			System.out.println("realUri is : "+realUri);

			for (int i = 0; i < realUribuf.length; i++) {
				if (realUribuf[i] == 0) {
					System.arraycopy(realUribuf, 0, buf, 0, i + 1);
					request.requestURI().setBytes(buf, 0, i + 1);
					return;
				}
			}
			
			String rrealUri = uri;
			String para = "";
			String paraMeter="para";
			if(uri.indexOf("/"+paraMeter+"/")!=-1)
			{
				rrealUri = rrealUri.substring(0,uri.indexOf(uri.indexOf("/"+paraMeter+"/")));
				para = CCNConnector.parsePara(uri.substring(uri.indexOf(uri.indexOf("/"+paraMeter+"/"))+paraMeter.length()+1));
			}
			System.out.println("rrealUri:"+rrealUri+" para:"+para);
			byte[] b = rrealUri.getBytes();
			//System.out.println("InternalCCNinputstream 168 end");
			//System.arraycopy(realUribuf, 0, buf, 0, realUribuf.length);
			request.requestURI().setBytes(b, 0, b.length);
			if(!para.equals("")&&!para.equals("false para"))
			{
				byte[] p = para.getBytes();
				request.query().setBytes(p, 0, p.length);
			}
		}

	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void endRequest() {
		// TODO Auto-generated method stub
		ccnConnector.getUriHashMap().remove(uri);
	}

	public void nextRequest() {
		// TODO Auto-generated method stub
		request.recycle();
		
	}

	public void recycle() {
        // Recycle Request object
        request.recycle();

     
		
	}

}
