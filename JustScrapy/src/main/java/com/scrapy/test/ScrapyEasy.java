package com.scrapy.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class ScrapyEasy {

	static Pattern pattern = Pattern.compile("^.+\\s+init\\(\\{\n");//Pattern.MULTILINE
	static Pattern pattern2 = Pattern.compile("^\\s+(.+)\\:");
	public static void main(String[] args) {
		try {
			start();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	static RequestConfig requestConfig = RequestConfig.custom().
            setConnectTimeout(1000).setConnectionRequestTimeout(1000)
            .setSocketTimeout(1000).setRedirectsEnabled(true).build();
	
	private static void start() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String url = "https://hf.ke.com/ershoufang/103102363699.html";
		HttpGet get = getHttpGet(url);
		try {
			CloseableHttpResponse response = httpClient.execute(get);
			String rs = getResponseString(response);
			int index = rs.indexOf("init({");
			int end = rs.indexOf("<script>require(['common/suggestion']");
			String sub = rs.substring(index, end);
			sub = sub.substring(5, sub.length() - 26)+"}";
			sub = sub.replace("'", "\"");
//			System.out.println(sub.substring(5, sub.length() - 19));//.replace("\n", "")
//			sub = sub.substring(5, sub.length() - 19).replace("'", "\"");//.replace(",\n      ", ",\n\"");//.replace(",\n      ", ",\n\"")
//			sub = sub.replaceAll("\\:\\s*\"", "\":\"");//[!\"]
//			System.out.println(sub);
			BufferedReader reader = new BufferedReader(new StringReader(sub));
			String line = null;
			StringBuilder builder = new StringBuilder();
			while((line = reader.readLine()) != null) {
				builder.append(line.replaceFirst("\\:\\s*", "\":").replaceFirst("^\\s+", "\""));//.replaceFirst("^\\s+", "\"")
				
//				System.out.println(line.replaceFirst("\\:\\s*\"", "\":\"").replaceFirst("\\:\\s*\\[", "\":[").replaceFirst("", replacement));
			}
			JSONObject data = JSONObject.parseObject(builder.toString());
			System.out.println(data.get("resblockName"));

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String getResponseString(CloseableHttpResponse httpResponse) throws ParseException, IOException {
		String httpResponseStr = null;
		HttpEntity httpEntity = httpResponse.getEntity();
		if (httpEntity != null)
			httpResponseStr = EntityUtils.toString(httpEntity, "UTF-8");
		EntityUtils.consumeQuietly(httpEntity);
		httpResponse.close();
		return httpResponseStr;
	}
	
	private static HttpGet getHttpGet(String url) {
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(requestConfig);
		return httpGet;
	}
}
