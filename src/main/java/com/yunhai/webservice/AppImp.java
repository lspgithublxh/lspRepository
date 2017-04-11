/**
 * 
 */
package com.yunhai.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

/**
 * @author lishaoping
 * 2017年4月11日下午10:16:20
 * AppImp
 */

@WebService(serviceName="HelloService", targetNamespace = "http://www.baidu.com")
public class AppImp{

	@WebMethod(operationName="saysssssssss")
	public String getSayHello(String name) {
		System.out.println("server reseived: " + name);
		return name;
	}
	
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:8080/services", new AppImp());
		System.out.println("server publish service appimpl");
	}

}
