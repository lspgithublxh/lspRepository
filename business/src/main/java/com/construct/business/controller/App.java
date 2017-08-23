package com.construct.business.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Hello world!
 *
 */
@Controller
@RequestMapping("/test")
public class App 
{
		
	@RequestMapping("/test")
	public void test() {
		
	}

	public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
