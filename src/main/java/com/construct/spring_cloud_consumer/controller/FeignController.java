package com.construct.spring_cloud_consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.construct.spring_cloud_consumer.service.IFeignService;

@RestController
public class FeignController {

	@Autowired
	IFeignService service;
	
	@RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String sayHi(@RequestParam String name){
		System.out.println("feigin mehtod: ");
        return service.hi(name);
    } 
}
