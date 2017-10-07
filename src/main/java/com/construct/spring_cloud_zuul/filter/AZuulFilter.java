package com.construct.spring_cloud_zuul.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class AZuulFilter extends ZuulFilter{

	@Override
	public Object run() {
		RequestContext context = RequestContext.getCurrentContext();
		HttpServletRequest request = context.getRequest();
		Object accessToken = request.getParameter("token");
		if(accessToken == null) {
			context.setSendZuulResponse(false);
			context.setResponseStatusCode(401);
			try {
				context.getResponse().getWriter().write("token is empty");
			}catch(Exception e) {
				
			}
		}
		return null;
	}

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "pre";
	}

	
}
