package com.tools.spring_boot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tools.spring_boot.service.IAService;

/**
 * 全部以json格式返回
 *
 *@author lishaoping
 *ToolsTest
 *2017年10月5日
 */
//@Configuration
//@ServletComponentScan
//@EnableAutoConfiguration 
@RestController
public class AController {

	protected static Logger logger = LoggerFactory.getLogger(AController.class); 
	
	@Autowired
    private IAService userService;
	
	@RequestMapping("/hello")
	public String hello(String name) {
		System.out.println("hello : " + name);
		userService.testData();
		return "success";
	}
}
