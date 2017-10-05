package com.tools.spring_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tools.spring_boot.controller.AController;

/**
 * 绗竴涓猻pring閰嶇疆
 * Add a provider like Hibernate Validator (RI) to your classpath.
 *@author lishaoping
 *ToolsTest
 *2017骞�10鏈�5鏃�
 */


@SpringBootApplication
@RestController
@RequestMapping(value="/boot")
public class SpringBoot_AController {
	
	@RequestMapping(value="/")
    public String greeting() {
		System.out.println("------hello-------");
        return "Hello World!";
    }

	@RequestMapping(value="/hello")
    public String index() {
		System.out.println("------hello2-------");
        return "Index Page";
    }
	
	public static void main(String[] args) {
		//1.只需要运行注解了@SpringBootApplication的那个类就可以了。其他controller只要在同级包下或者子包下都可以被访问到
		SpringApplication.run(SpringBoot_AController.class, args);
		
	}
}
