package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import cn.nnw.dao.impl.UserDaoImpl;
import cn.nnw.domain.User;
import java.util.*;

public final class seg0test2_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String username=null;
String password=null;
/* Cookie myCookie[]=request.getCookies();//创建一个Cookie对象数组 
System.out.println(">>>>>>>>>>>>>>----");
for(int i=0;myCookie!=null&&i<myCookie.length;i++){
	 //设立一个循环，来访问Cookie对象数组的每一个元素 
	Cookie newCookie= myCookie[i]; 
	
	if(newCookie.getName().equals("username")){
		username = newCookie.getValue();
		System.out.println(">>>>>>>>>>>>>>"+username);
	}
	if(newCookie.getName().equals("password")){
		password = newCookie.getValue();
		System.out.println(">>>>"+password);
	}
}
if(username!=null&&password!=null){
	cn.nnw.dao.UserDao dao = new UserDaoImpl();
	User use = dao.find(username,password);
	if(use!=null){
		request.getSession().setAttribute("user", use);
		//让用户登陆成功后，跳转首页
		
		response.sendRedirect(request.getContextPath() + "/index.jsp");
		return; 
		
	}
} */
		

      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE HTML>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n");
      out.write("<title>未来互联网首页</title>\r\n");
      out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\r\n");
      out.write("<meta name=\"description\" content=\"\" />\r\n");
      out.write("<meta name=\"author\" content=\"\" />\r\n");
      out.write("\r\n");
      out.write("<!-- Le styles -->\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\"\r\n");
      out.write("\thref=\" ccnx://bupt/css/bootstrap.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\"\r\n");
      out.write("\thref=\" ccnx://bupt/css/inettuts.css \" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\"\r\n");
      out.write("\thref=\" ccnx://bupt/css/inettuts.js.css \" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\"\r\n");
      out.write("\thref=\" ccnx://bupt/css/next-network.css \" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\"\r\n");
      out.write("\thref=\" ccnx://bupt/css/bootstrap-responsive.css \" />\r\n");
      out.write("<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->\r\n");
      out.write("<!--[if lt IE 9]>\r\n");
      out.write("    <script src=\"http://html5shim.googlecode.com/svn/trunk/html5.js\"></script>\r\n");
      out.write("    <![endif]-->\r\n");
      out.write("\r\n");
      out.write("<!-- Le fav and touch icons -->\r\n");
      out.write("<link rel=\"shortcut icon\"\r\n");
      out.write("\thref=\" ccnx://bupt/ico/favicon.ico \" />\r\n");
      out.write("<link rel=\"apple-touch-icon-precomposed\" sizes=\"144x144\"\r\n");
      out.write("\thref=\" ccnx://bupt/ico/apple-touch-icon-144-precomposed.png \" />\r\n");
      out.write("<link rel=\"apple-touch-icon-precomposed\" sizes=\"114x114\"\r\n");
      out.write("\thref=\" ccnx://bupt/ico/apple-touch-icon-114-precomposed.png \" />\r\n");
      out.write("<link rel=\"apple-touch-icon-precomposed\" sizes=\"72x72\"\r\n");
      out.write("\thref=\" ccnx://bupt/ico/apple-touch-icon-72-precomposed.png \" />\r\n");
      out.write("<link rel=\"apple-touch-icon-precomposed\"\r\n");
      out.write("\thref=\" ccnx://bupt/ico/apple-touch-icon-57-precomposed.png \" />\r\n");
      out.write("\r\n");
      out.write("<style>\r\n");
      out.write("body {\r\n");
      out.write("\tpadding-top: 60px;\r\n");
      out.write("\t/* 60px to make the container go all the way to the bottom of the topbar */\r\n");
      out.write("\tbackground: white;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("#logo {\r\n");
      out.write("\tdisplay: table-cell;\r\n");
      out.write("\tvertical-align: middle;\r\n");
      out.write("\ttext-align: center;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("/* add by me */\r\n");
      out.write("#columns #column1 {\r\n");
      out.write("\tbackground-image: none;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("#columns #column3 {\r\n");
      out.write("\tbackground-image: none;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("#columns .widget .widget-head {\r\n");
      out.write("\theight: 35px;\r\n");
      out.write("}\r\n");
      out.write(".uneditable-input {\r\n");
      out.write("  height: 30px;\r\n");
      out.write("}\r\n");
      out.write("</style>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("\t\r\n");
      out.write("\r\n");
      out.write("\t\r\n");
      out.write("\r\n");
      out.write("\t<script type=\"text/javascript\"\r\n");
      out.write("\t\tsrc=\" ccnx://bupt/js/jquery-1.7.2.js \"></script>\r\n");
      out.write("\t<script type=\"text/javascript\"\r\n");
      out.write("\t\tsrc=\" ccnx://bupt/js/bootstrap.min.js \"></script>\r\n");
      out.write("\t<script type=\"text/javascript\"\r\n");
      out.write("\t\tsrc=\" ccnx://bupt/js/jquery-ui-1.10.3.custom.js \"></script>\r\n");
      out.write("\t<script type=\"text/javascript\"\r\n");
      out.write("\t\tsrc=\" ccnx://bupt/js/xmlccnxrequest.js\"></script>\t\r\n");
      out.write("\t<!-- <script type=\"text/javascript\" srcsrc=\" ccnx://bupt/ ccnx://bupt//jquery-ui-personalized-1.6rc2.min.js \"></script> -->\r\n");
      out.write("\t<script type=\"text/javascript\"\r\n");
      out.write("\t\tsrc=\" ccnx://bupt/js/inettuts.js \"></script>\r\n");
      out.write("\t<script type=\"text/javascript\"\r\n");
      out.write("\t\tsrc=\" ccnx://bupt/js/index.js \"></script>\r\n");
      out.write("\t\t");
      out.write("\r\n");
      out.write("\t<!-- <script type=\"text/javascript\" src=\" ccnx://bupt/ js/calendar.js \"></script> -->\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else log(t.getMessage(), t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
