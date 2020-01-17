package com.li.shao.ping.KeyListBase.datastructure.util.server.header;

import java.util.Map;

import avro.shaded.com.google.common.collect.Maps;

public class CommonHeader {

	public static CommonHeader instance = new CommonHeader();
	
	private Map<String, String> map = Maps.newHashMap();
	{
		map.put("Server", "Apache-Coyote/1.1");
		map.put("Content-Type", "text/html");
	}
	
	public String convertMapToResponseHeader(Map<String, String> map) {
		StringBuffer buffer = new StringBuffer("HTTP/1.1 :code :status\r\n");
		String rs = "HTTP/1.1 500 Failed \\r\\n";
		try {
			if(map == null || map.isEmpty()) {
				map = this.map;
			}
			map.forEach((key, val) ->{
				buffer.append(key).append(": ").append(val).append("\r\n");
			}); 
			buffer.append("\r\n");
			rs = buffer.toString().replace(":code", "200").replace(":status", "OK");
		} catch (Exception e) {
			e.printStackTrace();
			rs = buffer.toString().replace(":code", "400").replace(":status", "Failed");
		}
		return rs;
	}
}
