package com.li.shao.ping.KeyListBase.datastructure.util.log;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.junit.Test;

import com.li.shao.ping.KeyListBase.datastructure.util.uid.UIDUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 日志监控
 *
 * @author lishaoping
 * @date 2020年1月20日
 * @package  com.li.shao.ping.KeyListBase.datastructure.util.log
 */
@Slf4j
public class TailSimulateUtil2 {

	public void logWrite(File file) {
		try {
			FileWriter fw = new FileWriter(file);
			Thread thread = new Thread(()->{
				while(true) {
					try {
						fw.write("hello, new info" + UIDUtil.increNum());
						fw.flush();
						Thread.sleep(100);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void logRead(File file) {
		try {
			RandomAccessFile rf = new RandomAccessFile(file, "r");
			AtomicLong pos = new AtomicLong(0);
//			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			Thread thread = new Thread(()->{
				while(true) {
					try {
						long nowPos = rf.getFilePointer();
						rf.seek(pos.get());
						FileChannel channel = rf.getChannel();
						int len = 0;
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						byte[] data = new byte[1024];
						while((len = channel.read(buffer)) > 0) {
							buffer.flip();
							buffer.get(data, 0, len);
							buffer.compact();
							out.write(data, 0, len);
						}
						if(pos.get() == nowPos) {
							continue;
						}
						nowPos = rf.getFilePointer();
						pos.set(nowPos);
						log.info(new String(out.toByteArray()));
						Thread.sleep(1000);
						buffer.clear();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test() {
		try {
			File f = new File("D:\\test\\a.txt");
			logRead(f);
			logWrite(f);
			Thread.sleep(1000 * 3600);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
