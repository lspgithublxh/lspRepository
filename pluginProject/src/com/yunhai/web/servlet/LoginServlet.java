/**
 * 
 */
package com.yunhai.web.servlet;
import java.io.IOException; 
import java.io.PrintWriter; 

import javax.servlet.ServletException; 
import javax.servlet.http.HttpServlet; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse; 
/**
 * @author lishaoping
 * 2017年5月1日上午11:23:38
 * LoginServlet
 */
public class LoginServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//Web 开发场景一：使用 Request 对象得到得到客户端请求数据
		 String userName = request.getParameter("userName"); 
		 //Web 开发场景二：操作 Session 
		 request.getSession().setAttribute("userFromA", userName); 
		 //Web 开发场景三：使用 Response 返回
		 response.setCharacterEncoding("GBK"); 
		 response.setContentType("text/html"); 
		 PrintWriter out = response.getWriter(); 
		 out.println("<html>"); 
		 out.println("<body>"); 
		 out.println("<head>"); 
		 out.println("<title> 登录返回页面 </title>"); 
		 out.println("</head>"); 
		 out.println("<body>"); 
		 out.println(userName + ", 您的 sessionId：" + request.getSession().getId()); 
		 out.println("</body>"); 
		 out.println("</html>"); 
		 out.flush(); 
		 out.close(); 
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
