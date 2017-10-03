package com.tools.webservice;

import javax.xml.ws.Endpoint;

public class WebServiceTest {

	public static void main(String[] args) {
		String url = "http://localhost:8888/toolT/services/ABService";
		Endpoint.publish(url, new ABService());
	}
}
