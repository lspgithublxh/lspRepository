package com.explore.known.Consumer_A.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import com.explore.known.Consumer_A.filters.TokenFilter;
import com.explore.known.Consumer_A.resolver.HostAddrKeyResolver;
import com.explore.known.Consumer_A.resolver.UriKeyResolver;

import reactor.core.publisher.Mono;

/**
 * 限流(过滤器方式：需要redis配置)，熔断(hystrix)，缓存
 * 限流具体：ip,uri,用户访问频次
 *  >限流三大算法：1.基本的直接atomic来一个增加一个， 2.漏桶算法：用一个队列一方面保存/等待请求，一方面定时从队列取/解冻定量。3.令牌桶算法：用一个队列保存令牌，
 * 断言： 路径/报头/cookie/请求方法/参数名与值
 * 
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
	
//	@Bean
//	@Qualifier(value = "TokenFilter")
//	public TokenFilter tokenFilter(){
//	        return new TokenFilter();
//	}
	
	@Bean
//	@Qualifier(value = "hostAddrKeyResolver")
//	@Primary
    public HostAddrKeyResolver hostAddrKeyResolver() {
        return new HostAddrKeyResolver();
    }
	
//	@Bean
//	@Qualifier(value = "UriKeyResolver")
//    public UriKeyResolver uriKeyResolver() {
//        return new UriKeyResolver();
//    }
	
	/**@Primary
	 * 直接根据用户名去限流
	 * @return
	 */
//	@Bean
//	@Qualifier(value = "userKeyResolver")
//    KeyResolver userKeyResolver() {
//        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("user"));
//    }

}
