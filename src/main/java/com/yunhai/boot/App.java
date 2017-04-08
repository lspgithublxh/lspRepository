package com.yunhai.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author lishaoping
 * 2017年4月8日上午11:26:34
 * App
 */
@SpringBootApplication
@RestController
@RequestMapping(value="/boot")
public class App 
{
	@RequestMapping(value="/")
	public String hello(){
		return "Hello World!";
	}
	
	@RequestMapping(value="/hello")
	public String hello2(){
		return "Hello World ----------!";
	}
	
    public static void main( String[] args )
    {
    	SpringApplication.run(App.class, args);
        System.out.println( "Hello World!" );
    }
}
