package com.tools.spring_boot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@RequestMapping("/hello")
	public String hello(String name) {
		System.out.println("hello : " + name);
		return "success";
	}
}
