package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import cn.nnw.dao.impl.UserDaoImpl;
import cn.nnw.domain.User;
import java.util.*;

public final class seg1test2_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
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
      out.write("\t<!-- 下拉应用列表框 -->\r\n");
      out.write("\t\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t\r\n");
      out.write("\r\n");
      out.write("\t<div id=\"columns\">\r\n");
      out.write("\r\n");
      out.write("\t\t<ul id=\"column1\" class=\"column\">\r\n");
      out.write("\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t<!-- <input type=\"hidden\" id=\"orderlist1\" /> -->\r\n");
      out.write("\r\n");
      out.write("\t\t\t<!-- <li class=\"widget color-green\" id=\"intro\" >\r\n");
      out.write("            <div class=\"widget-head\">\r\n");
      out.write("                <h3>我的导航</h3>\r\n");
      out.write("            </div>\r\n");
      out.write("            \r\n");
      out.write("        </li> -->\r\n");
      out.write("        ");
      if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t");
      out.write("\r\n");
      out.write("\t\t</ul>\r\n");
      out.write("\r\n");
      out.write("\t\t<ul id=\"column2\" class=\"column\">\r\n");
      out.write("\t\t\t<!--<li class=\"widget color-white ui-sortable\" id=\"appid-6\">\r\n");
      out.write("\t\t\t<div class=\"widget-head\" style=\"cursor: move;\"><a href=\"#\" class=\"collapse\">COLLAPSE</a>\r\n");
      out.write("\t\t\t<h3>热点视频</h3>\r\n");
      out.write("\t\t\t<a href=\"#\" class=\"remove\">CLOSE</a><a href=\"#\" class=\"edit\">EDIT</a></div>\r\n");
      out.write("\t\t\t<div class=\"edit-box\" style=\"display:none;\"><ul><li class=\"item\">\r\n");
      out.write("\t\t\t<label>Change the title?</label><input value=\"热点视频\"></li></ul><li class=\"item\">\r\n");
      out.write("\t\t\t<label>Available colors:</label><ul class=\"colors\"><li class=\"color-yellow\"></li><li class=\"color-red\">\r\n");
      out.write("\t\t\t</li><li class=\"color-blue\"></li><li class=\"color-white\"></li>\r\n");
      out.write("\t\t\t<li class=\"color-orange\"></li><li class=\"color-green\"></li></ul></li></div>\r\n");
      out.write("\t\t\t<div class=\"widget-content\"><a href=\"http://127.0.0.1:8080/nextNetwork/servlet/DownLoadServlet\" shape=\"rect\" style=\"float: right;color:black;cursor:pointer;\">\r\n");
      out.write("\t\t\t<i class=\"icon-download-alt\"></i>http下载</a><h4>机器人舞</h4>\r\n");
      out.write("\t\t\t<video width=\"414\" height=\"310\" controls=\"controls\">\r\n");
      out.write("\t\t\t<source src=\"video/chaoniu1.mp4\" type=\"video/mp4\">Your browser does not support the video tag.</video>\r\n");
      out.write("\t\t\t</div></li>-->\r\n");
      out.write("\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t<!--      <input type=\"hidden\" id=\"orderlist2\" /> -->\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\t<!-- <li class=\"widget color-yellow\">    \r\n");
      out.write("    <div class=\"widget-head\">\r\n");
      out.write("        <h3>视频</h3>\r\n");
      out.write("    </div>\r\n");
      out.write("    <div class=\"widget-content\">\r\n");
      out.write("        <p>视频:日本右翼抵达钓附近海域 10艘海保船护航</p>\r\n");
      out.write("        <embed src=\" ccnx://bupt/http://you.video.sina.com.cn/api/sinawebApi/outplay.php/P060S3A4B27K+l1lHz2stssM5aINt8vji2m3vFatJBEZDFjhZoPdK51SjyvJRpYWnm1NRpo3ffYn1gJOMfQJomp0MmYSnRSGN7cMObXR5KGZOwEd8XFHrnimAN1ioaFYgSpOExLVx7QC4gOTpDHUcWCy7gU1CoWDU0N+wlQ2rMdGrBCU3Ope5hLT27zKFcYpr3VcZSaKrKcuxA.swf\" quality=\"high\" allowfullscreen=\"true\" flashvars=\"playMovie=true&auto=1\" pluginspage=\"http://get.adobe.com/cn/flashplayer/\" style=\"visibility: visible;\" allowscriptaccess=\"never\"\r\n");
      out.write("                        width=\"393\" height=\"300\" \r\n");
      out.write("                        type=\"application/x-shockwave-flash\"></embed>\r\n");
      out.write("   \r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t<!-- <input name=txt><input type=button value=setday onclick=\"setday(this,document.all.txt)\"> -->\r\n");
      out.write("\t<!-- <input onfocus=\"setday(this,'hehe')\" type=\"text\"> -->\r\n");
      out.write("\r\n");
      out.write("\t<div id=\"username\" title=\"\"></div>\r\n");
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

  private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f0 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f0.setParent(null);
    // /seg1test2.jsp(131,8) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f0.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${user==null}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
    if (_jspx_eval_c_005fif_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t<li class=\"widget color-orange\" id=\"appid-8\">\r\n");
        out.write("\t\t\t\t<div class=\"widget-head\">\r\n");
        out.write("\t\t\t\t\t<h3>最常访问</h3>\r\n");
        out.write("\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t<div class=\"widget-content\">\r\n");
        out.write("\t\t\t\t\t<ul>\r\n");
        out.write("\t\t\t\t\t\t<li><a href=\"http://www.baidu.com\" style=\"color:black\">www.hoopchina.com</a>\r\n");
        out.write("\t\t\t\t\t\t</li>\r\n");
        out.write("\t\t\t\t\t\t<li><a href=\"http://www.baidu.com\" style=\"color:black\">forum.byr.edu.cn</a>\r\n");
        out.write("\t\t\t\t\t\t</li>\r\n");
        out.write("\t\t\t\t\t</ul>\r\n");
        out.write("\t\t\t\t</div></li>\r\n");
        out.write("\t\t\t<!-- <li class=\"widget color-white\" id=\"appid-4\">\r\n");
        out.write("            <div class=\"widget-head\">\r\n");
        out.write("                <h3>实时热点</h3>\r\n");
        out.write("            </div>\r\n");
        out.write("            <div class=\"widget-content\">\r\n");
        out.write("                <ul>\r\n");
        out.write("                    <li>\r\n");
        out.write("                         <a href=\"http://www.baidu.com\" style=\"color:black\">www.hoopchina.com</a>\r\n");
        out.write("                    </li>\r\n");
        out.write("                </ul>\r\n");
        out.write("               \r\n");
        out.write("                \r\n");
        out.write("            </div>\r\n");
        out.write("        </li> -->\r\n");
        out.write("\t\t\t<li id=\"appid-5\" class=\"widget color-white\">\r\n");
        out.write("\t\t\t\t<div class=\"widget-head\">\r\n");
        out.write("\t\t\t\t\t<h3>个性推荐</h3>\r\n");
        out.write("\t\t\t\t</div>\r\n");
        out.write("\t\t\t\t<div class=\"widget-content\">\r\n");
        out.write("\t\t\t\t\t<ul>\r\n");
        out.write("\t\t\t\t\t\t<li><a href=\"http://www.baidu.com\" style=\"color:black\">www.hoopchina.com</a>\r\n");
        out.write("\t\t\t\t\t\t</li>\r\n");
        out.write("\t\t\t\t\t</ul>\r\n");
        out.write("\r\n");
        out.write("\t\t\t\t</div></li>\r\n");
        out.write("\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
    return false;
  }
}
