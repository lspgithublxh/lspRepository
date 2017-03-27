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

/**
 * @author Administrator
 * 2017年3月27日
 * Filter
 */ 
public class MyFilter implements Filter{

	private static Integer Test = 1;
	@Override
	public void destroy() {
		System.out.println("my filter destroy");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("my filter do! " + request.getRemoteAddr() + request.getLocalAddr());
		Test++;
		chain.doFilter(request, response);
//		if(Test == 2){
//			chain.doFilter(request, response);
//		}else{
//			request.getRequestDispatcher("/1.jpg").forward(request, response);
//		}
		
		
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		System.out.println("my filter init");//执行在项目启动时
	}

}
