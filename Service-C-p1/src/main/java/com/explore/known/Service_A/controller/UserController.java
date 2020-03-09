package com.explore.known.Service_A.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import brave.sampler.Sampler;

@RestController("usercontroller")
@RequestMapping("/api/zipkin")
public class UserController {

	@Autowired
	private RestTemplate template;
	
	
	@GetMapping("/start")
	public String hello() {
		return template.getForObject("http://localhost:8004/api/zipkin/info", String.class);
	}
	
	@GetMapping("/info")
	public String info() {
		return "call return";
	}
	
	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}
}
