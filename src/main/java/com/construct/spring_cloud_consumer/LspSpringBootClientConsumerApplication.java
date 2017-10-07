package com.construct.spring_cloud_consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
public class LspSpringBootClientConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LspSpringBootClientConsumerApplication.class, args);
	}
	
	/**
	 * @LoadBalanced代理类，转换服务名为ip用
	 *@author lishaoping
	 *lsp_spring_boot_client_consumer
	 *2017年10月7日
	 * @return
	 */
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
