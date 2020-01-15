package com.li.shao.ping.KeyListBase.datastructure.util.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.li.shao.ping.KeyListBase.datastructure.geneutil.SimpleThreadPoolUtil;
import com.li.shao.ping.KeyListBase.datastructure.geneutil.SimpleThreadPoolUtil2;
import com.li.shao.ping.KeyListBase.datastructure.geneutil.v2.ServiceServerUtil;
import com.li.shao.ping.KeyListBase.datastructure.util.reader.HttpStreamReaderWriter;
import com.li.shao.ping.KeyListBase.datastructure.util.uid.UIDUtil;

import avro.shaded.com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

/**
 * 浏览器中可以访问的可以返回正常信息的服务器
 *
 * @author lishaoping
 * @date 2020年1月13日
 * @package  com.li.shao.ping.KeyListBase.datastructure.util.server
 */
@Slf4j
public class ServiceHttpServer {

	
	public void startServer() {
		try {
			HttpStreamReaderWriter util = new HttpStreamReaderWriter();
			ServerSocket server = new ServerSocket(11111);
			while(true) {
				Socket socket = server.accept();
				log.info("accept a socket link");
				SimpleThreadPoolUtil.pool.addTask(()->{
					try {
						InputStream in = socket.getInputStream();
						OutputStream out = socket.getOutputStream();
						String lock = UIDUtil.increNum() + "";
						SimpleThreadPoolUtil.pool.addTask(()->{
							SimpleThreadPoolUtil.pool.addTask(()->{
								try {
									System.out.println("start listen read:");
									util.formatRead(in, lock);
								} catch (IOException e) {
									e.printStackTrace();
								}
							});
							synchronized (lock.intern()) {//TODO
								try {
									//未到，等待
									String header = util.headerMap.get(in);
									if(header == null) {
										lock.intern().wait();
										header = util.headerMap.get(in);
									}
									HttpResourceDispatcher.instance.dispatcher(header, 
											util.dataMap.get(in), util, out);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new ServiceHttpServer().startServer();
	}
}
