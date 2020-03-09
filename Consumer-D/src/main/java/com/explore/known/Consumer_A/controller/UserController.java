package com.explore.known.Consumer_A.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController("UserController")
@RequestMapping("/api/consumer")
public class UserController {

	@Autowired
	private RestTemplate template;
//    public static  final String URL_PREFIX = "http://localhost:8001";
	 public static  final String URL_PREFIX = "http://Service-B";//可以去掉端口，根据服务名访问
	
	@GetMapping("/start/{user_id}")
	@HystrixCommand(fallbackMethod = "hiError")
	public String hello(@PathVariable("user_id") Long id) {
		System.out.println("duan");
		return template.getForObject(URL_PREFIX + "/api/serviceB/start/" + id, String.class);
	}
	
	public String hiError(Long id) {
        return "hi,"+id+",sorry,error!";
    }
}
