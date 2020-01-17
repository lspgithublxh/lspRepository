package com.li.shao.ping.KeyListBase.datastructure.util.httpserver;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.io.Files;
import com.li.shao.ping.KeyListBase.datastructure.util.httpserver.header.CommonHeader;
import com.li.shao.ping.KeyListBase.datastructure.util.reader.HttpStreamReaderWriter;

import avro.shaded.com.google.common.collect.Maps;

public class Filter {

	public static Filter instance = new Filter();

	public Pattern filterPattern = Pattern.compile(".+?(?:\\.png|jpg|js|svg|css)");
	String path = Filter.class.getClassLoader().getResource("").getPath();
	{
		try {
			path = path.substring(0, path.length() - 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean filter(String url, String header,HttpStreamReaderWriter util, 
			byte[] content, OutputStream out) {
		Matcher matcher = filterPattern.matcher(url);
		if(matcher.find()) {
			try {
				byte[] data = Files.asByteSource(new File(path + url)).read();
				Map<String, String> headerMap = Maps.newHashMap();
				String prefix = url.substring(url.lastIndexOf(".") + 1);
				headerMap.put("Server", "Apache-Coyote/1.1");
				if(url.endsWith(".png")) {
					prefix = "image/png";
				}else if(url.endsWith(".jpg")){
					prefix = "image/jpeg";
				} if(url.endsWith(".js")){
					prefix = "application/x-javascript";
				}else if(url.endsWith(".css")) {
					prefix = "text/css";
				}
				headerMap.put("Content-Type", prefix);
				String responseHeader = CommonHeader.instance.convertMapToResponseHeader(headerMap);
				util.formSend(data, responseHeader, out);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	public static void main(String[] args) {
		Matcher m = Filter.instance.filterPattern.matcher("xxxx.jpg");
		if(m.find()) {
			System.out.println(m.group());
		}
	}
}