package com.bj58.fang.url;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.bj58.spat.wos.util.CodecUtils;
import com.bj58.spat.wos.util.FileUtils;
import com.bj58.wf.mvc.utils.Base64;

public class Http_client_wos {

	public static void main(String[] args) {
//		test_offline2("http://testv1.wos.58dns.org/GIhdCbAZyGwhj/applandordsharepic/", System.currentTimeMillis() +"16.png");
		test_offline("http://testv1.wos.58dns.org/GIhdCbAZyGwhj/applandordsharepic/", "16.png");

//		test("http://prod1.wos.58dns.org/dEbAZyXwdhK/applandordsharepic/", System.currentTimeMillis() +"16.png");
	}

	private static void test_offline2(String url, String filename) {
		String appid = "GIhdCbAZyGwhj";
		String secretKey = "UqZ3o85DcbcQiq32ATbNGSTDKcbJzbjo";
		String secretId = "GeQ6AoBhegvSBCcmmx288OGroRgEhbQp";
		String bucket = "applandordsharepic";
		DefaultHttpClient client = new DefaultHttpClient();  
		HttpPost httpPost = getHttpPost(url + filename);
        client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION,  
                HttpVersion.HTTP_1_1);  
        client.getParams().setParameter(  
                CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");
        MultipartEntity entity = new MultipartEntity();         
        // 字符参数部分 
        
        String au = getToken(client, bucket, filename, appid, secretId);
		FileBody bin = new FileBody(new File("D:\\a.png"));  
		StringBody comment = new StringBody("upload", ContentType.TEXT_PLAIN);
		
		
        entity.addPart("filecontent", bin);
        entity.addPart("op", comment);
        
        
        httpPost.setEntity(entity);  
        httpPost.addHeader("Host", "testv1.wos.58dns.org");//UzB4VVk2UlgwUEFlQ25lM2dNSVZsbzJjY0JzPTplPTE0ODkwNDg5Mzg=
        httpPost.addHeader("Authorization", au);
        httpPost.addHeader("Content-Type", "multipart/form-data");
        HttpResponse response;
		try {
			response = client.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == 200) { // 成功  
	            //获取服务器返回值  
	            HttpEntity responseEntity = response.getEntity();  
	            InputStream input = responseEntity.getContent();  
	            StringBuilder sb = new StringBuilder();  
	            int s;  
	            while ((s = input.read()) != -1) {  
	                sb.append((char) s);  
	            }  
	            String result = sb.toString();  
	            System.out.println(result);
	        } 
		} catch (IOException e) {
			e.printStackTrace();
		}  
        
	}
	
	private static void test_offline(String url, String filename) {
		String appid = "GIhdCbAZyGwhj";
		String secretKey = "UqZ3o85DcbcQiq32ATbNGSTDKcbJzbjo";
		String secretId = "GeQ6AoBhegvSBCcmmx288OGroRgEhbQp";
		String bucket = "applandordsharepic";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = getHttpPost(url + filename);
		String au = getToken(httpClient, bucket, filename, appid, secretId);
		FileBody bin = new FileBody(new File("D:\\" + filename));  
		StringBody comment = new StringBody("upload", ContentType.TEXT_PLAIN); 
		byte[] fileContent = null;
		String shaDigest = null;
		try {
			fileContent = FileUtils.getFileContent("D:\\" + filename).getBytes(Charset.forName("ISO-8859-1"));
			shaDigest = CodecUtils.getBufferSha1(fileContent);
		} catch (Exception e3) {
			e3.printStackTrace();
		}

		try {
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		FileInputStream in;
		HttpEntity reqEntity = null;
		try {
//			in = new FileInputStream("D:\\" + filename);
//			 ByteArrayOutputStream out = new ByteArrayOutputStream();
//		        byte[] b = new byte[1024];
//		        int l = 0;
//		        while((l = in.read(b)) > 0) {
//		        	out.write(b, 0, l);
//		        }
		        ContentType utf8TextPlain = ContentType.create("text/plain", Consts.UTF_8);
		        reqEntity = MultipartEntityBuilder.create()
		        		.addBinaryBody("filecontent", fileContent, ContentType.DEFAULT_BINARY, "1.jpg")
		        		.addTextBody("op", "upload", utf8TextPlain)
		        		.addTextBody("sha", shaDigest)
		        		.addTextBody("ttl", "168")
		        		.addTextBody("insertOnly", "1")
//						.addPart("filecontent", bin)
//						.addPart("op", comment)
//						.addPart("sha", new StringBody(HttpClient_5_wos.getSha1(out.toByteArray()), ContentType.TEXT_PLAIN))
//						.addPart("insertOnly", new StringBody("1", ContentType.TEXT_PLAIN))
//						.addPart("ttl", new StringBody("168", ContentType.TEXT_PLAIN))
						.build();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
       
//      entity;
//      entity.addPart("insertOnly", new StringBody("1"));
//      entity.addPart("ttl", new StringBody("168"));
		httpPost.setEntity(reqEntity);
//		httpPost.addHeader("Host", "testv1.wos.58dns.org");//UzB4VVk2UlgwUEFlQ25lM2dNSVZsbzJjY0JzPTplPTE0ODkwNDg5Mzg=
        httpPost.addHeader("Authorization", au);
//        httpPost.addHeader("Content-Type", "multipart/form-data; boundary=---------------------------249631983811356");
//        httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:60.0) Gecko/20100101 Firefox/60.0");
//        httpPost.addHeader("Origin", "null");
        httpPost.addHeader("Connection", "keep-alive");
        httpPost.addHeader("User-Agent", "Java SDK/1.0.8");
        
        
        HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpPost);
			String strResult = getRsFromResponse(httpResponse);
		    System.out.println(strResult);
		} catch (IOException e) {
			e.printStackTrace();
		}
       
	}
	
	private static void test(String url, String filename) {
		String appid = "dEbAZyXwdhK";
		String secretKey = "iicZuBkVkGzHYUGhKWbPTG9W18lEMwWg";
		String secretId = "mb6jTMoxEzEHYN2yhLCnw1U7W0LTKDHz";
		String bucket = "applandordsharepic";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = getHttpPost(url + filename);
		String au = getToken(httpClient, bucket, filename, appid, secretId);
		FileBody bin = new FileBody(new File("D:\\a.png"));  
		StringBody comment = new StringBody("upload", ContentType.TEXT_PLAIN);  
		HttpEntity reqEntity = MultipartEntityBuilder.create()
				.addPart("filecontent", bin)
				.addPart("op", comment).build();
		httpPost.setEntity(reqEntity);
		httpPost.addHeader("Host", "prod1.wos.58dns.org");//UzB4VVk2UlgwUEFlQ25lM2dNSVZsbzJjY0JzPTplPTE0ODkwNDg5Mzg=
        httpPost.addHeader("Authorization", au);
        httpPost.addHeader("Content-Type", "multipart/form-data");
        
        HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpPost);
			String strResult = getRsFromResponse(httpResponse);
		    System.out.println(strResult);
		} catch (IOException e) {
			e.printStackTrace();
		}
       
	}
	
	private static HttpPost getHttpPost(String url) {
		RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(1000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000).setRedirectsEnabled(true).build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
		return httpPost;
	}
	
	public static String getToken_online(CloseableHttpClient httpClient, String bucket, String filename, String appid, String secret_id) {
		String url = String.format("http://token.wos.58dns.org/get_token?bucket=%s&filename=%s", bucket, filename);
//		HttpPost httpPost = getHttpPost(url);
		url = "http://token.wos.58dns.org/dEbAZyXwdhK/mb6jTMoxEzEHYN2yhLCnw1U7W0LTKDHz/applandordsharepic/"+filename+"/1531828181";
		HttpGet httpGet = getHttpGet(url);
		try {
			httpGet.addHeader("Authorization", String.format("Basic %s", Base64.encode(String.format("%s:%s", appid, secret_id).getBytes("UTF-8"))));
			HttpResponse httpResponse = httpClient.execute(httpGet);
			String strResult = getRsFromResponse(httpResponse);
	        System.out.println(strResult);
	        JSONObject rs = JSONObject.parseObject(strResult);
	        if(rs.getInteger("code") == 0) {
	        	return rs.getString("data");
	        }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public static String getToken(CloseableHttpClient httpClient, String bucket, String filename, String appid, String secret_id) {
		String url = String.format("http://tokentest.wos.58dns.org/get_token?bucket=%s&filename=%s", bucket, filename);
//		HttpPost httpPost = getHttpPost(url);
		url = "http://127.0.0.1:9494/GIhdCbAZyGwhj/GeQ6AoBhegvSBCcmmx288OGroRgEhbQp/applandordsharepic/"+filename+"/1531828181";
		HttpGet httpGet = getHttpGet(url);
		try {
			httpGet.addHeader("Authorization", String.format("Basic %s", Base64.encode(String.format("%s:%s", appid, secret_id).getBytes("UTF-8"))));
			HttpResponse httpResponse = httpClient.execute(httpGet);
			String strResult = getRsFromResponse(httpResponse);
	        System.out.println(strResult);
	        JSONObject rs = JSONObject.parseObject(strResult);
	        if(rs.getInteger("code") == 0) {
	        	return rs.getString("data");
	        }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	private static HttpGet getHttpGet(String url) {
		RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(1000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000).setRedirectsEnabled(true).build();
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(requestConfig);
		return httpGet;
	}
	
	private static String getRsFromResponse(HttpResponse httpResponse) throws IOException {
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
