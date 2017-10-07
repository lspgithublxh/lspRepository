package com.construct.spring_cloud_consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.construct.spring_cloud_consumer.service.AService;

@RestController
public class AController {

	@Autowired
	AService aservice;
	
	@RequestMapping("/hi")
	public String hi(@RequestParam String name) {
		return aservice.hiService(name);
	}
}
