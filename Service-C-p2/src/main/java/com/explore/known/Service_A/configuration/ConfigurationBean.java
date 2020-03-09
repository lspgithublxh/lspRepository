package com.explore.known.Service_A.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigurationBean {

	@Bean
	public RestTemplate getTemplate() {
		return new RestTemplate();
	}
}
