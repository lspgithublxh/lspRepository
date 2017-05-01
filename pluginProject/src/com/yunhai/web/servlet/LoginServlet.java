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
 * 2017��5��1������11:23:38
 * LoginServlet
 */
public class LoginServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//Web ��������һ��ʹ�� Request ����õ��õ��ͻ�����������
		 String userName = request.getParameter("userName"); 
		 //Web ���������������� Session 
		 request.getSession().setAttribute("userFromA", userName); 
		 //Web ������������ʹ�� Response ����
		 response.setCharacterEncoding("GBK"); 
		 response.setContentType("text/html"); 
		 PrintWriter out = response.getWriter(); 
		 out.println("<html>"); 
		 out.println("<body>"); 
		 out.println("<head>"); 
		 out.println("<title> ��¼����ҳ�� </title>"); 
		 out.println("</head>"); 
		 out.println("<body>"); 
		 out.println(userName + ", ���� sessionId��" + request.getSession().getId()); 
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
