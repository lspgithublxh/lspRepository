package com.li.shao.ping.KeyListBase.datastructure.util.httpserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.charset.Charset;

import com.esotericsoftware.minlog.Log;
import com.google.common.io.Files;
import com.li.shao.ping.KeyListBase.datastructure.util.reader.HttpStreamReaderWriter;

import avro.shaded.com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpResourceDispatcher {

	public static HttpResourceDispatcher instance = new HttpResourceDispatcher();
	
	public void dispatcher(String header, byte[] content, HttpStreamReaderWriter util,
			OutputStream out) {
		String url = "";
		if(header != null) {
			try {
				BufferedReader reader = new BufferedReader(new StringReader(header));
				String request = reader.readLine();
				String[] arr = request.trim().split("\\s+");
				url = arr[1];
				log.info(url);
				//过滤器直接过滤：比如图片直接返回
				if(FilterChain.getInstance().filter(Lists.newArrayList(Filter.instance), 
						url,util, header, content, out)) {
					return;
				}
				//map:url-handler
				Handler handler = UrlHandlerMapper.instance.handlerMap.get(url);
				if(handler != null) {
					handler.handler(header, content, util, out);
					return;
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		//map:url-handler
		Handler handler = UrlHandlerMapper.instance.handlerMap.get("/default");
		if(handler != null) {
			handler.handler(header, content, util, out);
		}
		
	}
}
