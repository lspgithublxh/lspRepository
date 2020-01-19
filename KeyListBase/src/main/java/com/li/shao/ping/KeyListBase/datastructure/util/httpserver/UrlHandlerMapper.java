package com.li.shao.ping.KeyListBase.datastructure.util.httpserver;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.TreeMap;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.li.shao.ping.KeyListBase.datastructure.util.httpserver.header.CommonHeader;
import com.li.shao.ping.KeyListBase.datastructure.util.monitor.MemoryVisitUtil;
import com.li.shao.ping.KeyListBase.datastructure.util.monitor.MemoryVisitUtil.AllMonitorEntity;
import com.li.shao.ping.KeyListBase.datastructure.util.monitor.MemoryVisitUtil.ThreadEntity;

import avro.shaded.com.google.common.collect.Maps;

public class UrlHandlerMapper {

	public static UrlHandlerMapper instance = new UrlHandlerMapper();
	
	public Map<String, Handler> handlerMap = Maps.newHashMap();
	{
		//加载
		handlerMap.put("/abc", (header, data, util, out)->{
			//到了，返回数据
			String responseHeader = "HTTP/1.1 200 OK\r\nServer: Apache-Coyote/1.1\r\nContent-Type:text/html\r\n\r\n";
			//获取页面数据
//			String responseHeader = null;
//			String page = "callback data";
			String path = ServiceHttpServer.class.getClassLoader().getResource("").getPath();

			try {
				String page = Files.asCharSource(new File(path + "f.html"), Charset.defaultCharset()).read();
				util.formSend(page.getBytes(), responseHeader, out);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		handlerMap.put("/default", (header, data, util, out)->{
			//到了，返回数据
			String responseHeader = "HTTP/1.1 200 OK\r\nServer: Apache-Coyote/1.1\r\nContent-Type:text/html\r\n\r\n";
			//获取页面数据
			String path = ServiceHttpServer.class.getClassLoader().getResource("").getPath();
			try {
				String page = Files.asCharSource(new File(path + "f.html"), Charset.defaultCharset()).read();
				util.formSend(page.getBytes(), responseHeader, out);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		handlerMap.put("/perf", (header, data, util, out)->{
			//到了，返回数据
			String responseHeader = CommonHeader.instance.convertMapToResponseHeader(null);
//			String responseHeader = "HTTP/1.1 200 OK\r\nServer: Apache-Coyote/1.1\r\nContent-Type:text/html\r\n\r\n";
			//获取页面数据
			String path = ServiceHttpServer.class.getClassLoader().getResource("").getPath();
			try {
				AllMonitorEntity allInfo = MemoryVisitUtil.util.getAllInfo();
				if(allInfo.getJstackMap() == null ) {
					handlerMap.get("/default").handler(header, data, util, out);
					return;
				}
				allInfo.getJstackMap().entrySet().stream().forEach(item ->{
					ThreadEntity entity = item.getValue();
					int pos = entity.getStack().indexOf("\r\n");
					if(pos > 0) {
						entity.setStack(entity.getStack().substring(0, pos));
					}
				});;
				//信息展示：
				String page = Files.asCharSource(new File(path + "performce.html"), Charset.defaultCharset()).read();
				Map<String, Object> resource = Maps.newHashMap();
				resource.put("jmap",allInfo.getJmapData().descendingMap().entrySet());
				resource.put("jstack", allInfo.getJstackMap().entrySet());
				resource.put("jstat", allInfo.getJstatMap().entrySet());
				resource.put("jinfo", allInfo.getJvmStartParam());
				resource.put("resource", allInfo.getBase());
				
				page = ResourceMapper.instance.matchAndReplace(page, resource);
				util.formSend(page.getBytes(), responseHeader, out);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
