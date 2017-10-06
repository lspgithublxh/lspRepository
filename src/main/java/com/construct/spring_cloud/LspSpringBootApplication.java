package com.construct.spring_cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 启动一个注册中心，给其他应用对话
 *
 *@author lishaoping
 *lsp_spring_boot
 *2017年10月6日
 */
@EnableEurekaServer
@SpringBootApplication
public class LspSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(LspSpringBootApplication.class, args);
	}
}
