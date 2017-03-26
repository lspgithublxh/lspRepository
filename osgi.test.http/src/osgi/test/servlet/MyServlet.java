/**
 * 
 */
package osgi.test.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 * 2017Äê3ÔÂ24ÈÕ
 * MyServlet
 */
public class MyServlet extends HttpServlet{

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().println("Hello , world");
	}
	
	public MyServlet() {
		super();
	}
}
