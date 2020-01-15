package com.li.shao.ping.KeyListBase.datastructure.util.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.charset.Charset;

import com.esotericsoftware.minlog.Log;
import com.google.common.io.Files;
import com.li.shao.ping.KeyListBase.datastructure.util.reader.HttpStreamReaderWriter;

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
				//map:url-handler
				Handler handler = UrlHandlerMapper.instance.handlerMap.get(url);
				if(handler != null) {
					handler.handler(header, content, util, out);
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
