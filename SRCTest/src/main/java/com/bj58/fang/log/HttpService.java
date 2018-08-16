package com.bj58.fang.log;

import java.io.IOException;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpService {

	public static String get(String url) {
		CloseableHttpClient client = HttpClients.createDefault();
		 //配置超时时间
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(1000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000).setRedirectsEnabled(true).build();
        HttpGet get = new HttpGet(url);
        get.setConfig(requestConfig);
        try {
        	CloseableHttpResponse chr = client.execute(get);
        	int http_statuscode = chr.getStatusLine().getStatusCode();
            if(http_statuscode > 500 && http_statuscode <= 599)
            {
              String errMsg = String.format("http status code is %d, response body: %s", new Object[] {
                  Integer.valueOf(http_statuscode), getResponseString(chr)
              });
              System.out.println(errMsg);
              
            }
  	      String responseStr = getResponseString(chr);
  	      return responseStr;
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
	}
	
	public static String post(String url, Map<String, String> headers, Map<String, Object> params) {
		CloseableHttpClient client = HttpClients.createDefault();
		 //配置超时时间
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(1000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000).setRedirectsEnabled(true).build();
        HttpPost post = new HttpPost(url);
        post.setConfig(requestConfig);
        for(String k : headers.keySet()) {
        	post.addHeader(k, headers.get(k));
        }
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        ContentType utf8TextPlain = ContentType.create("text/plain", Consts.UTF_8);
        for(String p : params.keySet()) {
        	Object v = params.get(p);
        	if(v.getClass().getName().contains("String")) {
        		entityBuilder.addTextBody(p, (String)v, utf8TextPlain);
        	}else if(v.getClass().getName().equals("[B")) {
        		entityBuilder.addBinaryBody(p, (byte[])v, ContentType.DEFAULT_BINARY, "1.jpg");
        	}
        }
        post.setEntity(entityBuilder.build());
        try {
        	CloseableHttpResponse chr = client.execute(post);
        	int http_statuscode = chr.getStatusLine().getStatusCode();
            if(http_statuscode > 500 && http_statuscode <= 599)
            {
              String errMsg = String.format("http status code is %d, response body: %s", new Object[] {
                  Integer.valueOf(http_statuscode), getResponseString(chr)
              });
              System.out.println(errMsg);
              
            }
  	      String responseStr = getResponseString(chr);
  	      return responseStr;
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
	}
	
	 private static String getResponseString(HttpResponse httpResponse)
	            throws ParseException, IOException
    {
        String httpResponseStr = null;
        HttpEntity httpEntity = httpResponse.getEntity();
        if(httpEntity != null)
            httpResponseStr = EntityUtils.toString(httpEntity, "UTF-8");
        if(httpResponseStr == null)
            return "";
        else
            return httpResponseStr;
    }
}
