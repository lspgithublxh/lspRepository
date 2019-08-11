package com.bj58.fang.hugopenapi;

import java.util.HashMap;

import org.apache.commons.codec.digest.DigestUtils;

import com.alibaba.fastjson.JSONObject;
import com.bj58.fang.hugopenapi.client.util.DefaultHttpPoolingManager;

public class RequestDemo {
	  
	  public static void main(String[] args) {

	    long timeSign = System.currentTimeMillis();
	    System.out.println("timeSign = " + timeSign);
	    
	    //公司申请的clientId,clientSecret
	    String clientId = "b6ab5dc63efb2ea7c7de1317bd9a9d58";//06e47c88385c8523e6bdd3a70e2b5fa2
	    String clientSecret = "180def1ba07798ba4447790830358be3";//
	    
	    String signature = DigestUtils.sha1Hex(clientSecret + "hugopenapi.58.com" + timeSign);
	    System.out.println(signature);
	    
	    //第一次获取token
	    String url = "http://hugopenapi.58.com/gateway/token/get_token?clientId=" + clientId + "&timeSign=" + timeSign + "&signature=" + signature;
	    String res = "";
	    String token = "1f9508dc7f72048afee9d9c61824c690";
//	    try {
//		      res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(url, new HashMap<String, String>(), new HashMap<String, String>());
//	      System.out.println(res);
//	      JSONObject tokenObject = JSONObject.parseObject(res);
//	      token = tokenObject.getString("access_token");
//	    } catch (Exception e) {
//	      e.printStackTrace();
//	    }
	    
	    //刷新token
//	    String url = "http://hugopenapi.58.com/gateway/token/refresh_token?clientId=" + clientId + "&timeSign=" + timeSign + "&signature=" + signature;
//	    String res = "";
//	    String token = "";
//	    try {
//	      res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(url, null, new HashMap<String, String>());
//	      System.out.println(res);
//	      JSONObject tokenObject = JSONObject.parseObject(res);
//	      token = tokenObject.getString("refresh_token");
//	    } catch (Exception e) {
//	      e.printStackTrace();
//	    }
	    

	        String tokenSign = DigestUtils.md5Hex(clientId + timeSign + token);
	        System.out.println("tokenSign = " + tokenSign);
	    
	        url = "http://hugopenapi.58.com/gateway/sale/company/posthouse?clientId=" + clientId + "&timeSign=" + timeSign + "&tokenSign=" + tokenSign + "&bianhao=xxx&xxx=xxx...";
	        res = "";
	    try {
	      res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(url, new HashMap<String, String>(), new HashMap<String, String>());
	      System.out.println(res);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  
	  }
	}