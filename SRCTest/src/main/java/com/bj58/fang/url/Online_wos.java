package com.bj58.fang.url;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.bj58.spat.wos.ClientConfig;
import com.bj58.spat.wos.exception.WosException;
import com.bj58.spat.wos.http.DefaultWosHttpClient;
import com.bj58.spat.wos.http.HttpContentType;
import com.bj58.spat.wos.http.HttpMethod;
import com.bj58.spat.wos.http.HttpRequest;
import com.bj58.spat.wos.request.UploadFileRequest;
import com.bj58.spat.wos.util.CodecUtils;
import com.bj58.spat.wos.util.FileUtils;

public class Online_wos {

	public static void main(String[] args) {
		try {
			System.out.println(new Online_wos().uploadSingleFile(null));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static DefaultWosHttpClient cl = null;
	CloseableHttpClient httpClient = HttpClients.createDefault();
	/**
	 * 可以成功
	 * @param 
	 * @author lishaoping
	 * @Date 2018年7月18日
	 * @Package com.bj58.fang.url
	 * @return String
	 */
	public String uploadSingleFile(UploadFileRequest request)
	        throws Exception
    {
//		String uploadDomain = "testv1.wos.58dns.org";
//		//2.下载资源的域名
//		String downDomain = "testv1.wos.58dns.org";
//		//3.获取token的域名
//		String tokenserverDomain = "tokentest.wos.58dns.org";
//		String appid = "GIhdCbAZyGwhj";
//		String secretKey = "UqZ3o85DcbcQiq32ATbNGSTDKcbJzbjo";
//		String secretId = "GeQ6AoBhegvSBCcmmx288OGroRgEhbQp";
//		String bucket = "applandordsharepic";
		//wos
		String uploadDomain = "prod1.wos.58dns.org";
		//2.下载资源的域名
		String downDomain = "wos.58.com";
		//3.获取token的域名
		String tokenserverDomain = "token.wos.58dns.org";
		String appid = "dEbAZyXwdhK";
		String secretKey = "iicZuBkVkGzHYUGhKWbPTG9W18lEMwWg";
		String secretId = "mb6jTMoxEzEHYN2yhLCnw1U7W0LTKDHz";
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.setUploadWosEndPointDomain(uploadDomain);
		clientConfig.setDownWosEndPointDomain(downDomain);
		clientConfig.setTokenServerDomain(tokenserverDomain);
		cl = new DefaultWosHttpClient(clientConfig);
		
		String bucket = "applandordsharepic";
		String filename = "file7.jpg";
		String fileContent = FileUtils.getFileContent("D:\\" + filename);
//		String sign = getToken_online(bucket, filename, true);
		String sign = getToken_online_self(bucket, filename, true);
		System.out.println(sign);
//		String sign = Http_client_wos.getToken_online(HttpClients.createDefault(), bucket, filename, appid, secretId);
		String url = "http://"+uploadDomain+"/"+appid+"/"+bucket+"/" + filename;//buildUrl(config.getUploadWosEndPointDomain(), request.getBucketName(), request.getFileName());
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader("Authorization", sign);
        
        httpRequest.addParam("ttl", String.valueOf("168"));
        httpRequest.addParam("op", "upload");
        httpRequest.addParam("insertOnly", String.valueOf("1"));
        httpRequest.addParam("filecontent", fileContent);
        httpRequest.setMethod(HttpMethod.POST);
        httpRequest.setContentType(HttpContentType.MULTIPART_FORM_DATA);
        
//        return cl.sendPostRequest(httpRequest);
        return sendPostRequest(httpRequest);
    }
	
	 public String getToken_online_self(String bucketName, String filename, boolean post)
		        throws WosException
	{
		  long expire = (new Date()).getTime() / 1000L + (long)120;
		  String url = "http://token.wos.58dns.org/get_token?";//buildGetTokenUrl(config.getTokenServerDomain());
		  String sign = (new StringBuilder()).append("Basic ").append(CodecUtils.Base64Encode(String.format("%s:%s", new Object[] {
		      "dEbAZyXwdhK", "mb6jTMoxEzEHYN2yhLCnw1U7W0LTKDHz"
		  }).getBytes())).toString();
		  HttpRequest httpRequest = new HttpRequest();
		  httpRequest.setUrl(url);
		  httpRequest.addHeader("Authorization", sign);
		  httpRequest.addParam("bucket", bucketName);
		  httpRequest.addParam("filename", filename);
		  httpRequest.addParam("expire", String.valueOf(expire));
		  if(post)
		      httpRequest.addParam("op", "upload");
		  else
		      httpRequest.addParam("op", "get");
		  httpRequest.setMethod(HttpMethod.GET);
		  return getToken_(httpRequest);
//		  return cl.getToken(httpRequest);
	}
	
	public String getToken_(HttpRequest httpRequest) {
		try {
			HttpGet get = getHttpGet(httpRequest, httpRequest.getUrl());
			setHeaders(get, httpRequest.getHeaders());
			HttpResponse httpResponse = httpClient.execute(get);
			
			String responseStr = getResponseString(httpResponse);
			String rs = JSONObject.parseObject(responseStr).getString("data");
//			get.completed();
//			get.releaseConnection();
//			get.abort();
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	 
	 public static String getToken_online(String bucketName, String filename, boolean post)
		        throws WosException
 {
     long expire = (new Date()).getTime() / 1000L + (long)120;
     String url = "http://token.wos.58dns.org/get_token?";//buildGetTokenUrl(config.getTokenServerDomain());
     String sign = (new StringBuilder()).append("Basic ").append(CodecUtils.Base64Encode(String.format("%s:%s", new Object[] {
         "dEbAZyXwdhK", "mb6jTMoxEzEHYN2yhLCnw1U7W0LTKDHz"
     }).getBytes())).toString();
     HttpRequest httpRequest = new HttpRequest();
     httpRequest.setUrl(url);
     httpRequest.addHeader("Authorization", sign);
     httpRequest.addParam("bucket", bucketName);
     httpRequest.addParam("filename", filename);
     httpRequest.addParam("expire", String.valueOf(expire));
     if(post)
         httpRequest.addParam("op", "upload");
     else
         httpRequest.addParam("op", "get");
     httpRequest.setMethod(HttpMethod.GET);
     return cl.getToken(httpRequest);
 }
	
	 public static String getToken(String bucketName, String filename, boolean post)
		        throws WosException
    {
        long expire = (new Date()).getTime() / 1000L + (long)120;
        String url = "http://tokentest.wos.58dns.org/get_token?";//buildGetTokenUrl(config.getTokenServerDomain());
        String sign = (new StringBuilder()).append("Basic ").append(CodecUtils.Base64Encode(String.format("%s:%s", new Object[] {
            "GIhdCbAZyGwhj", "GeQ6AoBhegvSBCcmmx288OGroRgEhbQp"
        }).getBytes())).toString();
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(url);
        httpRequest.addHeader("Authorization", sign);
        httpRequest.addParam("bucket", bucketName);
        httpRequest.addParam("filename", filename);
        httpRequest.addParam("expire", String.valueOf(expire));
        if(post)
            httpRequest.addParam("op", "upload");
        else
            httpRequest.addParam("op", "get");
        httpRequest.setMethod(HttpMethod.GET);
        return cl.getToken(httpRequest);
    }
	
	 
	 public String sendPostRequest(HttpRequest httpRequest) throws Exception {
		  HttpPost httpPost = new HttpPost(httpRequest.getUrl());
		  RequestConfig requestConfig = RequestConfig.custom().
	                setConnectTimeout(1000).setConnectionRequestTimeout(1000)
	                .setSocketTimeout(1000).setRedirectsEnabled(true).build();
		  httpPost.setConfig(requestConfig);
		  setHeaders(httpPost, httpRequest.getHeaders());
		  
		  setMultiPartEntity(httpPost, httpRequest.getParams());
		  
		  HttpResponse httpResponse = httpClient.execute(httpPost);
         int http_statuscode = httpResponse.getStatusLine().getStatusCode();
         if(http_statuscode > 500 && http_statuscode <= 599)
         {
           String errMsg = String.format("http status code is %d, response body: %s", new Object[] {
               Integer.valueOf(http_statuscode), getResponseString(httpResponse)
           });
           throw new WosException(-3, errMsg);
         }
	      String responseStr = getResponseString(httpResponse);
	      return responseStr;
	  }
	 
	 private String getResponseString(HttpResponse httpResponse)
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
	  
	  private static void setHeaders(HttpMessage message, Map headers)
	    {
	        message.setHeader("Connection", "Keep-Alive");
	        message.setHeader("User-Agent", "Java SDK/1.0.8");
	        if(headers != null)
	        {
	            String headerKey;
	            for(Iterator i$ = headers.keySet().iterator(); i$.hasNext(); message.setHeader(headerKey, (String)headers.get(headerKey)))
	                headerKey = (String)i$.next();

	        }
	    }
	  
	  private void setMultiPartEntity(HttpPost httpPost, Map params)
		        throws Exception
		    {
		        ContentType utf8TextPlain = ContentType.create("text/plain", Consts.UTF_8);
		        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
		        for(Iterator i$ = params.keySet().iterator(); i$.hasNext();)
		        {
		            String sendPostRequest = (String)i$.next();
		            if(sendPostRequest.equals("filecontent"))
		                entityBuilder.addBinaryBody("filecontent", ((String)params.get("filecontent")).getBytes(Charset.forName("ISO-8859-1")), ContentType.DEFAULT_BINARY, "1.jpg");
		            else
		                entityBuilder.addTextBody(sendPostRequest, (String)params.get(sendPostRequest), utf8TextPlain);
		        }

		        httpPost.setEntity(entityBuilder.build());
		    }
	  
	  private static HttpGet getHttpGet(HttpRequest httpRequest, String url)
		        throws Exception
		    {
		  URIBuilder urlBuilder = new URIBuilder(url);
          String paramKey;
          for(Iterator i$ = httpRequest.getParams().keySet().iterator(); i$.hasNext(); urlBuilder.addParameter(paramKey, (String)httpRequest.getParams().get(paramKey)))
              paramKey = (String)i$.next();

          HttpGet httpget = new HttpGet(urlBuilder.build());
//          RequestConfig requestConfig = RequestConfig.custom().
//                  setConnectTimeout(1000).setConnectionRequestTimeout(1000)
//                  .setSocketTimeout(1000).setRedirectsEnabled(true).build();
//          httpget.setConfig(requestConfig);
          return httpget;
		 }
}
