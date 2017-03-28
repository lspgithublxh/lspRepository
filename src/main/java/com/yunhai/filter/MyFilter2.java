/**
 * 
 */
package com.yunhai.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 * 2017年3月27日
 * MyFilter2
 */
public class MyFilter2 implements Filter{

	@Override
	public void destroy() {
		System.out.println("my filter2 destroy");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String path = ((HttpServletRequest)request).getServletPath();
		System.out.println("my filter2 do! " + path);
		
		chain.doFilter(request, response);
//		request.getRequestDispatcher("/user/page2").forward(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		System.out.println("my filter2 init");//执行在项目启动时
	}

}
