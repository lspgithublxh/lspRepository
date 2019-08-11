package com.bj58.fang.hugopenapi.client.service;

import java.util.HashMap;
import java.util.Map;

public class InitService {

	private static InitService instance = new InitService();
	private String clientId = null;
	private String clientSecret = null;
	private Long saveTime = null;
	private Long timeSign = 0l;
	private boolean distribute = false;
	private Map<String, Integer> ipPortMap = new HashMap<String, Integer>();
	private static boolean test = false;
	private static String token = null;//测试环境时的token
	
	public static InitService getInstance() {
		return instance;
	}
	
	public boolean isDistribute() {
		return distribute;
	}



	public void setDistribute(boolean distribute) {
		this.distribute = distribute;
	}



	/**
	 * token保存时间：saveTime, 
	 * @param 
	 * @author lishaoping
	 * @Package com.bj58.fang.hugopenapi.client.annoService
	 * @return void
	 */
	public static void init(String clientId, String clientSecret, Long saveTime) {
		if(instance.clientId == null || instance.clientSecret == null || instance.saveTime == null) {
			synchronized (InitService.class) {
				if(instance.clientId == null || instance.clientSecret == null || instance.saveTime == null) {
					instance.clientId = clientId;
					instance.clientSecret = clientSecret;
					instance.saveTime = saveTime;
				}
			}
		}
	}
	
	public static void test(boolean set, String tok) {
		test = set;
		token = tok;
	}
	
	public static void configDistributeCondition(boolean distribute, Map<String, Integer> ipPortMap) {
		instance.distribute = distribute;
		instance.ipPortMap = ipPortMap;
	}

	public String getClientId() {
		return clientId;
	}
	
	public long getTime() {
		return System.currentTimeMillis();
	}

	public String getClientSecret() {
		return clientSecret;
	}
	
	public Map<String, Integer> getIpPortMap() {
		return ipPortMap;
	}

	public void setIpPortMap(Map<String, Integer> ipPortMap) {
		this.ipPortMap = ipPortMap;
	}

	public long getTimeSign() {
		return timeSign;
	}

	public void setTimeSign(long timeSign) {
		this.timeSign = timeSign;
	}

	public Long getSaveTime() {
		return saveTime;
	}

	public static boolean isTest() {
		return test;
	}

	public static String getToken() {
		return token;
	}
	
}
