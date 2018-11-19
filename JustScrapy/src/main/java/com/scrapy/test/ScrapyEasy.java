package com.scrapy.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import com.alibaba.fastjson.JSONObject;

public class ScrapyEasy {

	static Pattern pattern = Pattern.compile("^.+\\s+init\\(\\{\n");//Pattern.MULTILINE
	static Pattern pattern2 = Pattern.compile("^\\s+(.+)\\:");
	static Logger logger = null;
	static {
		try {
			System.out.println(new File("").getAbsolutePath());
			System.out.println(System.getProperty("user.dir"));
			System.out.println(ScrapyEasy.class.getResource("/").getPath());
//			System.out.println(Thread.currentThread().getContextClassLoader().getResource("/").getPath());
			System.out.println(ScrapyEasy.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			System.out.println(ScrapyEasy.class.getName());
			File file = new File(ScrapyEasy.class.getClassLoader().getResource("log4j.xml").getPath());//加载当前目录文件
			System.out.println(file.getAbsolutePath());
			ConfigurationSource source = new ConfigurationSource(new FileInputStream(file), file);
			Configurator.initialize(null, source);
			logger = (Logger) LogManager.getLogger(DetailPage.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
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
		//经纪人评价：https://bj.ke.com/ershoufang/showcomment?isContent=1&page=1&order=0&id=101103599958&_=1542175969383
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
			BufferedReader reader = new BufferedReader(new StringReader(sub));
			String line = null;
			StringBuilder builder = new StringBuilder();
			while((line = reader.readLine()) != null) {
				builder.append(line.replaceFirst("\\:\\s*", "\":").replaceFirst("^\\s+", "\""));//.replaceFirst("^\\s+", "\"")
			}
			logger.info(builder.toString());
//			JSONObject data = JSONObject.parseObject(builder.toString());
//			System.out.println(data.get("resblockName"));
//			System.out.println(data.toJSONString());
//			logger.error("soso");
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
