package com.bj58.fang.url;

import org.apache.commons.httpclient.HttpsURL;
import org.apache.commons.httpclient.URIException;

import com.bj58.biz.utility.http.HttpClient;

public class HttpClientTest {

	public static void main(String[] args) {
		test();
	}

	private static void test() {
		HttpClient client = new HttpClient();
		try {
			HttpsURL url = new HttpsURL("https://miniappfang.58.com/api/token");
			org.apache.commons.httpclient.HttpClient client2 = new org.apache.commons.httpclient.HttpClient();
			
		} catch (URIException e1) {
			e1.printStackTrace();
		}
		try {
			String rs = client.getContent("https://miniappfang.58.com/api/token");
			System.out.println(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
