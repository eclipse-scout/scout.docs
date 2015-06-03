// servlet browser link http://localhost:8080/tinyservlet/
// direct link to servlet: http://localhost:8080/tinyservlet/servlets/Tiny

// tomcat overview: http://localhost:8080/
// application admin: http://localhost:8080/manager/html/list

// compiling
// servlet jar has to be found in WEB-INF/lib/ (to be found in apache servlet container, as well for compiling above)
// mzi@bsim013 ~/Desktop/jee/servlet1/WEB-INF/sources
// (0) cd /cygdrive/C/Documents\ and\ Settings/mzi/Desktop/jee/tinyservlet/WEB-INF/sources
// (1) /cygdrive/C/java/jdk1.5.0_16/bin/javac -classpath "../lib/javax.servlet_2.4.0.v200806031604.jar;." TinyServlet.java
// (2) mv TinyServlet.class ../classes

// deploying
// servlet jar has to be found in WEB-INF/lib/ (to be found in apache servlet container, as well for compiling above)
// (1a) zip contents of C:\Documents and Settings\mzi\Desktop\jee\tinyservlet
// (1b) rename to tinyservlet.zip to tinyservlet.war
// (2) copy war to C:\tomcat\tomcat70\webapps
// (3) restart tomcat (or remove folder tinyservlet from webapps and restart with war file only)

import java.util.Date;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

public class TinyServlet 
  extends HttpServlet
{
  public void service(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
  {
    response.setContentType("text/html;charset=ISO-8859-1");
	response.getWriter().println(
	  "<html>"+
	  "<head><title>tiny servlet</title></head>"+
	  "<body style=\"text-align:center\">" +
	  "TinyServlet's server time: " + new Date() + 
	  "</body>" +
	  "</html>"
	);
  }
}