package com.li.shao.ping.KeyListBase.datastructure.util.reader;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import avro.shaded.com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

/**
 * GET /abc HTTP/1.1
Host: localhost:11111
Connection: keep-alive
Cache-Control: max-age=0
 *
 *HTTP/1.1 200 OK
 *Server: Apache-Coyote/1.1
 *Content-Type:application/json
 *
 *
 *text/html
 * @author lishaoping
 * @date 2020年1月15日
 * @package  com.li.shao.ping.KeyListBase.datastructure.util.reader
 */
@Slf4j
public class HttpStreamReaderWriter {

	public Map<InputStream, byte[]> dataMap = Maps.newHashMap();
	public Map<InputStream, String> headerMap = Maps.newHashMap();

	public void formatRead(InputStream in, String lock) throws IOException {
		DataInputStream input = new DataInputStream(in);
		byte[] cache = new byte[1024];//第一个，前4个是块的个数
		ByteArrayOutputStream innerCache = new ByteArrayOutputStream();
		boolean startContent = false;
		int num = 0;
		String header = "";
		int available = input.available();
		while(true) {
			int len = input.read(cache);
			if(len > 0) {
				innerCache.write(cache, 0, len);
			}
			if(len >= 1024) {//报头未结束
				continue;
			}
			if(!startContent) {
				startContent = true;
				header = new String(innerCache.toByteArray());
				innerCache.reset();
				log.info(header);
				if(available <= len) {//不再需要等待
					log.info("only header");
					synchronized (lock.intern()) {
						headerMap.put(in, header);
						lock.intern().notify();
					}
					break;
				}
				continue;
			}
			//得到内容--触发开始写
			byte[] content = innerCache.toByteArray();
			innerCache.reset();
			innerCache.close();
			log.info("header+content");
			dataMap.put(in, content);
			synchronized (lock.intern()) {
				headerMap.put(in, header);
				lock.intern().notify();
			}
			break;
		}
	}

	public void formSend(byte[] data, String head, OutputStream out) {
		//发送头数据
		try {
			out.write(head.getBytes());
			out.write(data);
			out.flush();//发送一个结束符？
//			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}	
}
