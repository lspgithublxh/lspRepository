package com.bj58.fang.url;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;

public class HttpTest {

	public static void main(String[] args) throws Exception {
//		String url = "http://localhost:80/fangyuanapi";
		String url = "http://localhost:80/xiaoqu/bj/esfhotlist";
		Map<String, String> map = new HashMap<String, String>();
//		map.put("userId", "48073625865748");
//		map.put("userId", "32014609672372");
//		map.put("userId", "4966360");
//		map.put("dispcateid", "12");
		System.out.println(uploadFile2(url, map));
		//whtcy_au8
	}
	
	public static String uploadFile2(String url, Map<String, String> params) throws Exception {
    	
    	DefaultHttpClient client = new DefaultHttpClient();  
        HttpPost httpPost = new HttpPost(url);
        client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION,  
                HttpVersion.HTTP_1_1);  
        client.getParams().setParameter(  
                CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");
        MultipartEntity entity = new MultipartEntity();         
        // 字符参数部分  
        Set<String> set = params.keySet();  
        for (String key : set) {  
            entity.addPart(key, new StringBody(params.get(key)));  
        }  
        httpPost.setEntity(entity);  
        HttpResponse response = client.execute(httpPost);  
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
            return result;
        } 
    	return null;
    }
}
