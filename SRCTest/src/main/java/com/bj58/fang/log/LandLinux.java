package com.bj58.fang.log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class LandLinux {

	public static void main(String[] args) {
		
//		getClusterIps();
	}

	private static void getClusterIps() {
		Object s = new byte[] {3,4,5,6};
		System.out.println(s.getClass().getName());
		System.out.println("".getClass().getName());
		//
		String url = "http://yun.58corp.com/cluster/group/getPods";
		Map<String, String> h = new HashMap<>();
		h.put("Accept", "application/json, text/plain, */*");
		h.put("Accept-Encoding", "gzip, deflate");
		h.put("Accept-Language", "zh-CN,zh;q=0.9");
		h.put("Connection", "keep-alive");
		h.put("Content-Type", "application/x-www-form-urlencoded");
//		h.put("Content-Length", "71");
		h.put("Cookie", "SSO_SESSION_ID=ST-1078313-5gAncsj5UYyNFVdFXcFZ-tjtx-121-100");
		h.put("Host", "yun.58corp.com");
//		h.put("Origin", "http://yun.58corp.com");
		h.put("Referer", "http://yun.58corp.com/");
		h.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:61.0) Gecko/20100101 Firefox/61.0");
//		h.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
		h.put("X-Requested-With", "XMLHttpRequest");
		Map<String, Object> p = new HashMap<>();
		p.put("clusterName", "hbg_web_aroundweb");
		p.put("environment", "1");
		p.put("groupName", "hbg_web_aroundweb");
		System.out.println(post(url, h, p));
	}

	private static String post(String url, Map<String, String> headers, Map<String, Object> params) {
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
