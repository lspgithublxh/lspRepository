package com.bj58.fang.url;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.bj58.wf.mvc.utils.Base64;

public class HttpClient_5 {

	public static void main(String[] args){
		try {
//			test3("http://upload.58cdn.com.cn/json", null);
			test2("http://upload.58cdn.com.cn/json");//不是方法的问题，而是合成图本身不能放大
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		};
	}

	private static Object test3(String url, byte[] data) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
        //配置超时时间
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(1000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000).setRedirectsEnabled(true).build();
        HttpPost httpPost = new HttpPost(url);
        		 //设置超时时间
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-Type","application/json");  //
  
        String value = Base64.encode(data);
        String jsonParams = String.format("{\"Pic-Size\":\"0*0\",\"Pic-Encoding\":\"base64\",\"Pic-Path\":\"/p1/big/\",\"Pic-Data\":\"%s\"}", value);
		httpPost.setEntity(new StringEntity(jsonParams,ContentType.create("application/json", "utf-8")));
		HttpResponse httpResponse = httpClient.execute(httpPost);
		String strResult = "";
        if(httpResponse != null){ 
            System.out.println(httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                strResult = EntityUtils.toString(httpResponse.getEntity());
            } else if (httpResponse.getStatusLine().getStatusCode() == 400) {
                strResult = "Error Response: " + httpResponse.getStatusLine().toString();
            } else if (httpResponse.getStatusLine().getStatusCode() == 500) {
                strResult = "Error Response: " + httpResponse.getStatusLine().toString();
            } else {
                strResult = "Error Response: " + httpResponse.getStatusLine().toString();
            } 
        }
        System.out.println(strResult);
		return null;
	}

	private static char[] test2(String url) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
        //配置超时时间
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(1000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000).setRedirectsEnabled(true).build();
        HttpPost httpPost = new HttpPost(url);
        		 //设置超时时间
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-Type","application/json");  //
        httpPost.setHeader("Accept", "image/webp,image/apng,image/*,*/*;q=0.8");
        FileInputStream in = new FileInputStream("D:\\compu.jpg");//合成图会当作是png  compu.jpg 
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int l = 0;
        while((l = in.read(b)) > 0) {
        	out.write(b, 0, l);
        }
        System.out.println(out.toByteArray());
        String value = Base64.encode(out.toByteArray());
        String jsonParams = String.format("{\"Pic-Size\":\"0*0\",\"Pic-Encoding\":\"base64\",\"Pic-Path\":\"/p1/big/\",\"Pic-Data\":\"%s\"}", value);
		httpPost.setEntity(new StringEntity(jsonParams,ContentType.create("application/json", "utf-8")));
		HttpResponse httpResponse = httpClient.execute(httpPost);
		String strResult = "";
        if(httpResponse != null){ 
            System.out.println(httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                strResult = EntityUtils.toString(httpResponse.getEntity());
            } else if (httpResponse.getStatusLine().getStatusCode() == 400) {
                strResult = "Error Response: " + httpResponse.getStatusLine().toString();
            } else if (httpResponse.getStatusLine().getStatusCode() == 500) {
                strResult = "Error Response: " + httpResponse.getStatusLine().toString();
            } else {
                strResult = "Error Response: " + httpResponse.getStatusLine().toString();
            } 
        }
        System.out.println(strResult);
		return null;
	}

	private static String test(String url) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
        //配置超时时间
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(1000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000).setRedirectsEnabled(true).build();
        HttpPost httpPost = new HttpPost(url);//"http://consentprt.dtac.co.th/webaoc/123SubscriberProcess"
        //设置超时时间
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-Type","application/json");  //
      //装配post请求参数
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>(); 
        list.add(new BasicNameValuePair("Pic-Size", "0*0"));  //请求参数
        list.add(new BasicNameValuePair("Pic-Encoding", "base64")); //请求参数
        list.add(new BasicNameValuePair("Pic-Path", "/p1/big/")); //请求参数
        
        FileInputStream in = new FileInputStream("D:\\compu.jpg");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int l = 0;
        while((l = in.read(b)) > 0) {
        	out.write(b, 0, l);
        }
        System.out.println(out.toByteArray());
        String value = Base64.encode(out.toByteArray());
        list.add(new BasicNameValuePair("Pic-Data", value)); //请求参数
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"UTF-8"); 
        //设置post求情参数
        httpPost.setEntity(entity);
        HttpResponse httpResponse = httpClient.execute(httpPost);
        String strResult = "";
        if(httpResponse != null){ 
            System.out.println(httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                strResult = EntityUtils.toString(httpResponse.getEntity());
            } else if (httpResponse.getStatusLine().getStatusCode() == 400) {
                strResult = "Error Response: " + httpResponse.getStatusLine().toString();
            } else if (httpResponse.getStatusLine().getStatusCode() == 500) {
                strResult = "Error Response: " + httpResponse.getStatusLine().toString();
            } else {
                strResult = "Error Response: " + httpResponse.getStatusLine().toString();
            } 
        }
        return strResult;
	}
}
