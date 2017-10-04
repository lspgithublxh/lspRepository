package com.tools.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Dubbo_Client {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext con = new ClassPathXmlApplicationContext(
				new String[] {"spring-client.xml"});
		con.start();
		IMessageService inter = (IMessageService) con.getBean("msgService");
		
		System.out.println(inter.sayHello("lishaoping"));;
	}
}
