package com.construct.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.construct.psersistence.dao.IHUserDao;

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
