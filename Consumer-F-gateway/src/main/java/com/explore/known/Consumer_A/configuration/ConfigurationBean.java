package com.explore.known.Consumer_A.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigurationBean {

	@Bean
	public RestTemplate getTemplate() {
		return new RestTemplate();
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p.path("/get")// 原路径
						.filters(f -> f.addRequestHeader("Hello", "World"))
										.uri("http://httpbin.org:80"))// 转发路径
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
}
