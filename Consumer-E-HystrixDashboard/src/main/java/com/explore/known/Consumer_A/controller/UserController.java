package com.explore.known.Consumer_A.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * url-mapping 不会再生效
 *
 * @author lishaoping
 * @date 2020年3月9日
 * @package  com.explore.known.Consumer_A.controller
 */
@RestController("UserController")
@RequestMapping("/api/consumer")
@EnableHystrix
@EnableCircuitBreaker
@EnableHystrixDashboard
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
