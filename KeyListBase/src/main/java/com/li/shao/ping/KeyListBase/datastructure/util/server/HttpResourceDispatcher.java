package com.li.shao.ping.KeyListBase.datastructure.util.server;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import com.google.common.io.Files;
import com.li.shao.ping.KeyListBase.datastructure.util.reader.HttpStreamReaderWriter;

public class HttpResourceDispatcher {

	public static HttpResourceDispatcher instance = new HttpResourceDispatcher();
	
	public void dispatcher(String header, byte[] content, HttpStreamReaderWriter util,
			OutputStream out) {
		//到了，返回数据
		String responseHeader = "HTTP/1.1 200 OK\r\nServer: Apache-Coyote/1.1\r\nContent-Type:text/html\r\n\r\n";
		//获取页面数据
//		String page = "callback data";
		String path = ServiceHttpServer.class.getClassLoader().getResource("").getPath();
		try {
			String page = Files.asCharSource(new File(path + "f.html"), Charset.defaultCharset()).read();
			util.formSend(page.getBytes(), responseHeader, out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
