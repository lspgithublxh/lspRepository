package com.tools.dubbo;

public class MessageService implements IMessageService{

	@Override
	public String sayHello(String param) {
		System.out.println("sayHello");
		return "success";
	}

}
