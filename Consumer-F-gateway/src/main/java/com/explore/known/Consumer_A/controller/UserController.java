package com.explore.known.Consumer_A.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import reactor.core.publisher.Mono;

@RestController("UserController")
@RequestMapping("/api/consumer")
public class UserController {

	@Autowired
	private RestTemplate template;
    public static  final String URL_PREFIX = "http://localhost:8001";

	
	@GetMapping("/start/{user_id}")
	public String hello(@PathVariable("user_id") Long id) {
		return template.getForObject(URL_PREFIX + "/api/service/start/" + id, String.class);
	}
	
	@GetMapping("/hello")
	public String h(@RequestParam("name") String name) {
		return "hello" + name;
	}
	
	@RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }

}
