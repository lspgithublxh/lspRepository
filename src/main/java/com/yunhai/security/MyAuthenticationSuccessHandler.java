/**
 * 
 */
package com.yunhai.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author Administrator
 * 2017年3月26日
 * AuthenticationSuccessHandler
 */
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	private static final Logger LOGGER = LoggerFactory.getLogger(MyAuthenticationSuccessHandler.class);
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		LOGGER.info("success login");
		//转发静态资源jpg,jsp,html，重定向动态请求/login /page2
		request.getRequestDispatcher("/index.jsp")
		.forward(request, response);
//		response.sendRedirect("/webHibernate/page2");//重定向没问题。只是转发不行！！/webHibernate/page2
	}

}
