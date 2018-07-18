package com.bj58.fang.url;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.bj58.spat.wos.util.FileUtils;
import com.bj58.wf.mvc.utils.Base64;

//import com.bj58.spat.wos.ClientConfig;
//import com.bj58.spat.wos.exception.WosException;
//import com.bj58.spat.wos.http.DefaultWosHttpClient;
//import com.bj58.spat.wos.http.HttpContentType;
//import com.bj58.spat.wos.http.HttpMethod;
//import com.bj58.spat.wos.http.HttpRequest;
//import com.bj58.spat.wos.request.UploadFileRequest;
//import com.bj58.spat.wos.util.CodecUtils;
//import com.bj58.spat.wos.util.FileUtils;

public class Pure_http_wos {

	public static void main(String[] args) {
		try {
			new Pure_Wos().uploadSingleFile(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String uploadSingleFile()
	        throws Exception
	    {
		String uploadDomain = "testv1.wos.58dns.org";
		//2.下载资源的域名
		String downDomain = "testv1.wos.58dns.org";
		//3.获取token的域名
		String tokenserverDomain = "tokentest.wos.58dns.org";
		String appid = "GIhdCbAZyGwhj";
		String secretKey = "UqZ3o85DcbcQiq32ATbNGSTDKcbJzbjo";
		String secretId = "GeQ6AoBhegvSBCcmmx288OGroRgEhbQp";
		String bucket = "applandordsharepic";
		
		String filename = "old.jpg";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String fileContent = FileUtils.getFileContent("D:\\" + filename);
//	        String sign = Pure_Wos.getToken(bucket, filename, true);
			String sign = Http_client_wos.getToken(httpClient, bucket, filename, appid, secretId);
	        String url = "http://testv1.wos.58dns.org/GIhdCbAZyGwhj/applandordsharepic/" + filename;//buildUrl(config.getUploadWosEndPointDomain(), request.getBucketName(), request.getFileName());
	        Map<String, String> map = new HashMap<>();
//	        httpRequest.setUrl(url);
	        map.put("Authorization", sign);
	        map.put("url", url);
	        
	        map.put("ttl", String.valueOf("168"));
	        map.put("op", "upload");
//	        httpRequest.addParam("sha", shaDigest);
	        map.put("insertOnly", String.valueOf("1"));
	        map.put("filecontent", fileContent);
	      
//	        return cl.sendPostRequest(httpRequest);
	        return sendPostRequest(map);
	    }
	
	
//	 private String getToken(String bucketName, String filename, boolean post)
//		        throws WosException
//		    {
//		        long expire = (new Date()).getTime() / 1000L + (long)120;
//		        String url = "http://tokentest.wos.58dns.org/get_token?";//buildGetTokenUrl(config.getTokenServerDomain());
//		        String sign = (new StringBuilder()).append("Basic ").append(CodecUtils.Base64Encode(String.format("%s:%s", new Object[] {
//		            "GIhdCbAZyGwhj", "GeQ6AoBhegvSBCcmmx288OGroRgEhbQp"
//		        }).getBytes())).toString();
//		        HttpRequest httpRequest = new HttpRequest();
//		        httpRequest.setUrl(url);
//		        httpRequest.addHeader("Authorization", sign);
//		        httpRequest.addParam("bucket", bucketName);
//		        httpRequest.addParam("filename", filename);
//		        httpRequest.addParam("expire", String.valueOf(expire));
//		        if(post)
//		            httpRequest.addParam("op", "upload");
//		        else
//		            httpRequest.addParam("op", "get");
//		        httpRequest.setMethod(HttpMethod.GET);
//		        return cl.getToken(httpRequest);
//		    }
	 
	  public String sendPostRequest(Map<String, String> map) throws Exception {
		  HttpPost httpPost = new HttpPost(map.get("url"));
//		  RequestConfig requestConfig = RequestConfig.custom().
//	                setConnectTimeout(1000).setConnectionRequestTimeout(1000)
//	                .setSocketTimeout(1000).setRedirectsEnabled(true).build();
//		  httpPost.setConfig(requestConfig);
		  setHeaders(httpPost, map);
		  CloseableHttpClient httpClient = HttpClients.createDefault();
		  map.remove("Authorization");
		  setMultiPartEntity(httpPost, map);
		  
		  HttpResponse httpResponse = httpClient.execute(httpPost);
          int http_statuscode = httpResponse.getStatusLine().getStatusCode();
          if(http_statuscode > 500 && http_statuscode <= 599)
          {
            String errMsg = String.format("http status code is %d, response body: %s", new Object[] {
                Integer.valueOf(http_statuscode), getResponseString(httpResponse)
            });
            
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
	  
	  private void setHeaders(HttpMessage message, Map map)
	    {
	        message.setHeader("Connection", "Keep-Alive");
	        message.setHeader("User-Agent", "Java SDK/1.0.8");
	        message.setHeader("Authorization", (String)(map.get("Authorization")));
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
}
