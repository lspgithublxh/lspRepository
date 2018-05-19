package com.bj58.fang.url;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import com.alibaba.fastjson.JSONObject;

public class HttpsClientTest {

	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException {
		getWXToken();
	}

	private static void getWXToken() throws NoSuchAlgorithmException, KeyManagementException {
		HttpsClient client = new HttpsClient();
		HttpGet get = new HttpGet("https://miniappfang.58.com/api/token");
		try {
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			System.out.println(response.getStatusLine().getStatusCode());
			InputStream in = entity.getContent();
			BufferedInputStream bin = new BufferedInputStream(in);
			byte[] ca = new byte[1024];
			int len = 0;
			ByteArrayOutputStream din = new ByteArrayOutputStream(1024);			
			while((len = bin.read(ca)) > 0) {
				din.write(ca, 0, len);
			}
			System.out.println(din.toString("UTF-8"));
			JSONObject json = JSONObject.parseObject(din.toString("UTF-8"));
			if(json.getInteger("code") == 0) {
				System.out.println(json.getString("token"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
