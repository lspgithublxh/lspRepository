package com.tools.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * 默认名称空间是：targetNamespace="http://webservice.tools.com/"   name="ABServiceService"
 *
 *@author lishaoping
 *ToolsTest
 *2017年10月3日
 */
@WebService
public class ABService implements IABService {

	@Override
	public String sayHello(String ap) {
		System.out.println("hello ,world" + ap);
		return "success";
	}

	@WebMethod(exclude=true)
	@Override
	public void hello2() {
		System.out.println("0--------------0");
	}

	
}
