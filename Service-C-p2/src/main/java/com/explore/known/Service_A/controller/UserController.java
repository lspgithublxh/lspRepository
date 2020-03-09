package com.explore.known.Service_A.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import brave.sampler.Sampler;

/**
 * https://blog.csdn.net/forezp/article/details/81041078
 * 下载zipkin server并启动
 *
 * @author lishaoping
 * @date 2020年3月9日
 * @package  com.explore.known.Service_A.controller
 */
@RestController("usercontroller")
@RequestMapping("/api/zipkin")
public class UserController {

	@Autowired
	private RestTemplate template;
	
	
	@GetMapping("/info")
	public String hello() {
		return template.getForObject("http://localhost:8005/api/zipkin/info", String.class);
	}
	
	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}
}
