package com.tools.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class ABService implements IABService {

	@Override
	public String sayHello() {
		System.out.println("hello ,world");
		return "success";
	}

	@WebMethod(exclude=true)
	@Override
	public void hello2() {
		System.out.println("0--------------0");
	}

	
}
