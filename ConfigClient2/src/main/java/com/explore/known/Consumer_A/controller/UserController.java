package com.explore.known.Consumer_A.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController("UserController")
@RequestMapping("/api/consumer")
public class UserController {

	/**
	 * 从配置中心获取的配置
	 */
	@Value("${foo}")
	String foo;
	@Autowired
	private RestTemplate template;
    public static  final String URL_PREFIX = "http://localhost:8001";

	
	@GetMapping("/start/{user_id}")
	public String hello(@PathVariable("user_id") Long id) {
		return template.getForObject(URL_PREFIX + "/api/service/start/" + id, String.class);
	}
	
	@GetMapping("/hi/{user_id}")
	public String hi(@PathVariable("user_id") Long id) {
		return "foo:" + foo;
	}
}
