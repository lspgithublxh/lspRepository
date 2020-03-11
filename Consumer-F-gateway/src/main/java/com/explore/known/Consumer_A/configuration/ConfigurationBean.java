package com.explore.known.Consumer_A.configuration;

import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.explore.known.Consumer_A.filters.TokenFilter;

/**
 * 限流，熔断，缓存
 *
 * 断言： 路径/报头/cookie/请求方法/参数名与值
 * pre:前过滤
 * post:后过滤
 * 
 * @author lishaoping
 * @date 2020年3月11日
 * @package  com.explore.known.Consumer_A.configuration
 */
@Configuration
public class ConfigurationBean {

	@Bean
	public RestTemplate getTemplate() {
		return new RestTemplate();
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				//以下会覆盖yml里的配置--而不是merge
//				.route(p -> p.path("/get")// 原路径
//						.filters(f -> f.addRequestHeader("Hello", "World"))
//										.uri("http://httpbin.org:80"))// 转发路径
				.route(p -> p.host("*.hystrix.com")// host条件满足 才进入
						.filters(f -> f.hystrix(config -> 
												config.setName("mycmd")
													  .setFallbackUri("forward:/fallback")))// 熔断降级重定向
						.uri("http://httpbin.org:80"))
				.route(p -> p.path("/hello")
							.filters(f -> f.rewritePath("/hello(?<segment>.*)", "/api/consumer/hello${segment}"))
							.uri("http://localhost:9021/"))
				.build();
	}
	
	@Bean
	public TokenFilter tokenFilter(){
	        return new TokenFilter();
	}
}
