package com.explore.known.Service_A.controller;

import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController("usercontroller")
@RequestMapping("/api/serviceB")
@EnableHystrix
@EnableHystrixDashboard
public class UserController {

	@GetMapping("/start/{user_id}")
	public String hello(@PathVariable("user_id")Long id) {
		return "this is a json data" + id;
	}
	
	@RequestMapping("/hi")
	@HystrixCommand(fallbackMethod = "hiError")
	public String home() {
		return "hi i am from port:";
	}

	public String hiError() {
		return "hi,"+",sorry,error!";
	}

}
