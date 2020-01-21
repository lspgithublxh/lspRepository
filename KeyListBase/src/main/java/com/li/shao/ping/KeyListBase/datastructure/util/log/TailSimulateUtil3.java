package com.li.shao.ping.KeyListBase.datastructure.util.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.lang.invoke.VolatileCallSite;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.junit.Test;

import com.li.shao.ping.KeyListBase.datastructure.geneutil.SimpleThreadPoolUtil2;
import com.li.shao.ping.KeyListBase.datastructure.util.file.ReadFileUtil;
import com.li.shao.ping.KeyListBase.datastructure.util.uid.UIDUtil;

import avro.shaded.com.google.common.collect.Lists;
import avro.shaded.com.google.common.collect.Maps;
import avro.shaded.com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;

/**
 * 日志监控
 *
 * @author lishaoping
 * @date 2020年1月20日
 * @package  com.li.shao.ping.KeyListBase.datastructure.util.log
 */
@Slf4j
public class TailSimulateUtil3 {

	public static TailSimulateUtil3 instance = new TailSimulateUtil3();
	
	private Map<String, byte[]> logMap = Maps.newConcurrentMap();
	
	private Set<String> logFiles = Sets.newHashSet();
	
	public void logWrite(File file) {
		try {
			FileWriter fw = new FileWriter(file);
			Thread thread = new Thread(()->{
				while(true) {
					try {
						fw.write("hello,Exception new info" + UIDUtil.increNum() + "\r\n");
						fw.flush();
						Thread.sleep(1000);
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
	
	public List<String> startPattern(List<Pattern> pList, File file) {
		List<String> lines = Lists.newArrayList();
		try {
			String path = file.getAbsolutePath();
			String lock = path.intern();
			byte[] data = null;
			synchronized (lock) {//监控线程必定要先已经在等待中
				log.info("pattern log thread wake up supervisory thread");
				lock.notifyAll();//开始采集;甚至可以唤醒其他的先执行到这里的采集线程
				if(!logMap.containsKey(lock)) {
					lock.wait();
				}
				log.info("pattern log thread wake up");
				data = logMap.get(lock);
				logMap.remove(lock);
			}
			//利用data处理过滤
			//单行匹配
			String content = new String(data);
			BufferedReader reader = new BufferedReader(new StringReader(content));
			String line = null;
			while((line = reader.readLine()) != null) {
				for(Pattern p : pList) {
					Matcher m = p.matcher(line);
					if(m.find()) {
						lines.add(m.group());
					}
				}
			}
			//多行匹配
//			for(Pattern p : pList) {
//				Matcher m = p.matcher(content);
//				if(m.find()) {
//					lines.add(m.group());
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	public void logRead(File file) {
		try {
			synchronized (TailSimulateUtil3.class) {
				if(logFiles.contains(file.getAbsolutePath())) {
					return;
				}
				logFiles.add(file.getAbsolutePath());
			}
			RandomAccessFile rf = new RandomAccessFile(file, "r");
			AtomicLong pos = new AtomicLong(0);
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			//用CountDownLatch实现
			//用synchronized+AtomicInteger实现:被等待线程好知道到底是否需要等待
			AtomicInteger count = new AtomicInteger(1);
			
			SimpleThreadPoolUtil2.pool.addTask(()->{
				while(true) {
					try {
						synchronized (count) {
							count.decrementAndGet();
							count.notifyAll();
						}
						String path = file.getAbsolutePath();
						String lock = path.intern();
						synchronized (lock) {
							lock.wait();
						}
						log.info("start collect new log from " + pos.get());//out.toByteArray()
						long nowPos = rf.getFilePointer();
						rf.seek(pos.get());
						FileChannel channel = rf.getChannel();
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						ReadFileUtil.instance.readFromChannel(buffer, channel, out);
						synchronized (lock) {
							logMap.put(lock, out.toByteArray());
							lock.notifyAll();
						}
						if(pos.get() == nowPos) {
							continue;
						}
						nowPos = rf.getFilePointer();
						pos.set(nowPos);
						
						out.close();
						buffer.clear();
						log.info("put logMap data ok");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
			synchronized (count) {
				if(count.get() == 1) {
					count.wait();
				}
			}
			log.info("end open supervisory log");
		} catch (Exception e) {
			e.printStackTrace();
			synchronized (TailSimulateUtil3.class) {
				if(!logFiles.contains(file.getAbsolutePath())) {
					return;
				}
				logFiles.remove(file.getAbsolutePath());
			}
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
	
	public static void main(String[] args) {
		try {
			File f = new File("D:\\test\\a.txt");
			log.info(f.getAbsolutePath());
			TailSimulateUtil3 t2 = new TailSimulateUtil3();
//			t2.logRead(f);
			t2.logWrite(f);
			Thread.sleep(1000 * 3600);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
