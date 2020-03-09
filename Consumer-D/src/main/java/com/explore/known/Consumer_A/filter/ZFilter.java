package com.explore.known.Consumer_A.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import lombok.extern.slf4j.Slf4j;

/**
 * 直接bean化即可
 *
 * @author lishaoping
 * @date 2020年3月9日
 * @package  com.explore.known.Consumer_A.filter
 */
@Slf4j
@Component
public class ZFilter extends ZuulFilter{

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		//权限查询
		RequestContext ctx = RequestContext.getCurrentContext();
	    HttpServletRequest request = ctx.getRequest();
	    log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
	    Object accessToken = request.getParameter("token");
        if(accessToken == null) {
            log.warn("token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token is empty");
            }catch (Exception e){}
            return null;
        }
	    return null;
	}

	@Override
	public String filterType() {
		return "pre";//路由之间过滤
	}

	@Override
	public int filterOrder() {
		return 0;
	}

}
