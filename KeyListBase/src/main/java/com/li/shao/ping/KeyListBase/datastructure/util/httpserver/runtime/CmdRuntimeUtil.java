package com.li.shao.ping.KeyListBase.datastructure.util.httpserver.runtime;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CmdRuntimeUtil {

	public static CmdRuntimeUtil instance = new CmdRuntimeUtil();
	Runtime runtime = Runtime.getRuntime();
	{
		runtime.addShutdownHook(new Thread(()->{
			log.info("shutdown of runtime!");
		}) );
	}
	
	public byte[] exec(String cmd) {
		try {
			Process process = runtime.exec(cmd);
			process.waitFor(1000,TimeUnit.MILLISECONDS);
			InputStream inputStream = process.getInputStream();
			while(true) {
				if(inputStream.available() == 0) {
					log.info("no found data");
					Thread.sleep(1000);
					continue;
				}
				break;
			}
			byte[] data = read2(inputStream);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private byte[] read2(InputStream inputStream) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			log.info("total to read:" + inputStream.available());
			byte[] buf = new byte[1024];
			int len = 0;
			while((len = inputStream.read(buf)) > 0) {//超时退出
				out.write(buf, 0, len);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}
	
	private byte[] readAll(InputStream inputStream) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		byte[] data = new byte[1024];
		try {
			out.write(inputStream);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}
}
