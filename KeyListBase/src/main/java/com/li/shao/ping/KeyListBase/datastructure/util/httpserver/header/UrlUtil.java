package com.li.shao.ping.KeyListBase.datastructure.util.httpserver.header;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import avro.shaded.com.google.common.collect.Lists;
import avro.shaded.com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UrlUtil {

	public static UrlUtil instance = new UrlUtil();

	public String getUrl(String header) {
		if(header == null || header.isEmpty() || !header.contains("\r\n")) {
			return "/default";
		}
		String url = header.substring(header.indexOf(" ") + 1, header.lastIndexOf("\r\n"));
		url = url.substring(0, url.indexOf(" "));
		return url;
	}
	
	public Map<String, List<String>> getUrlData(String header) {
		BufferedReader reader = new BufferedReader(new StringReader(header));
		String request;
		Map<String, List<String>> dataMap = Maps.newHashMap();
		try {
			request = reader.readLine();
			if(request == null) {
				log.info("dispatcher bad");
			}
			String[] arr = request.trim().split("\\s+");
			String url = arr[1];
			if(!url.contains("?")) {
				return dataMap;
			}
			String data = url.substring(url.indexOf("?") + 1);
			String[] kv = data.split("&");
			for(String item : kv) {
				String[] kvv = item.split("=");
				List<String> orElseGet = Optional.ofNullable(dataMap.get(kvv[0])).orElseGet(()->{
					List<String> dlist = Lists.newArrayList();
					dataMap.put(kvv[0], dlist);
					return dlist;
				});
				orElseGet.add(kvv[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataMap;
		
	}
}
