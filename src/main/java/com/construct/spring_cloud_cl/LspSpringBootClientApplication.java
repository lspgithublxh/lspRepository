package com.construct.spring_cloud_cl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@EnableWebMvc
@Configuration
@EnableEurekaClient
@SpringBootApplication
public class LspSpringBootClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(LspSpringBootClientApplication.class, args);
	}
}
