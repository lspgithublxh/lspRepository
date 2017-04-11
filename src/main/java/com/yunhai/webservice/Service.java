/**
 * 
 */
package com.yunhai.webservice;

/**
 * @author lishaoping
 * 2017年4月11日下午8:54:07
 * Service
 */
public class Service {

	public static void main(String[] args) {
		HelloService service = new HelloService();
		AppImp app = service.getAppImpPort();
		app.saysssssssss("dfsfsdfd");
	}
}
