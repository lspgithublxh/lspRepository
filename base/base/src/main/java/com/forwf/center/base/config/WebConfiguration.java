package com.forwf.center.base.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注册过滤器
 * @ClassName:WebConfiguration
 * @Description:
 * @Author lishaoping
 * @Date 2018年9月14日
 * @Version V1.0
 * @Package com.forwf.center.base.config
 */
@Configuration 
public class WebConfiguration {

	@Bean
	public RemoteIpFilter remoteIPFilter() {
		return new RemoteIpFilter();
	}
	
	@Bean
	public FilterRegistrationBean testFilterRegistraction() {
		FilterRegistrationBean bean = new FilterRegistrationBean<>();
		bean.setFilter(new FirstFilter());
		bean.addUrlPatterns("/");
		bean.addInitParameter("name", "lsp");
		bean.setName("firstFilter");
		bean.setOrder(1);
		return bean;
	}
	
	public class FirstFilter implements Filter{

		@Override
		public void destroy() {
			
		}

		@Override
		public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
				throws IOException, ServletException {
			System.out.println("go to filter");
			arg2.doFilter(arg0, arg1);//激活下一个请求
			
		}

		@Override
		public void init(FilterConfig arg0) throws ServletException {
			
		}
		
	}
}
