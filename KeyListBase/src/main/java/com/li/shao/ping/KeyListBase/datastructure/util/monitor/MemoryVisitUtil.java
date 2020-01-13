package com.li.shao.ping.KeyListBase.datastructure.util.monitor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.junit.Test;

import com.google.gson.Gson;
import com.li.shao.ping.KeyListBase.datastructure.inter.CmdReturnHandler;
import com.li.shao.ping.KeyListBase.datastructure.util.uid.UIDUtil;

import avro.shaded.com.google.common.collect.Lists;
import avro.shaded.com.google.common.collect.Maps;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemoryVisitUtil {
	
	private Runtime runtime = Runtime.getRuntime();
	private boolean isWin = false;
	private Pattern jstackPattern = Pattern.compile("\"([\\S\\s]+)\".+?nid\\=0x(\\S+)");
	{
		runtime.addShutdownHook(new Thread(()->{
			log.info("shutdown of runtime!");
		}) );
		String name = System.getProperty("os.name");
		if(name.startsWith("Win")) {
			isWin = true;
		}
	}
	
	/**
	 * 实时输出
	 */
	public void memCpuNetworkDiskMonitor() {
		
	}
	
	public void runCmd() {
		try {
			List<String> mainClassList = Lists.newArrayList("ProsserApplication");
			Map<Integer, String> threadIdName = parseJps("jps", this::parseJps);
			threadIdName.entrySet().forEach(item ->{
				if(mainClassList.contains(item.getValue())) {
					//开启循环持续输出
					Integer pid = item.getKey();
					String jmapSort = isWin ? "" : " | sort -n -r -k 3";
					for(;;) {
						TreeMap<Integer, Entity> jmapData = parseJmap("jmap -histo:live " + pid + jmapSort);//-n -r -k
						log.info(new Gson().toJson(jmapData.descendingMap()));
						TreeMap<Integer, ThreadEntity> stackMap = parseJstack("jstack -l " + pid);
						log.info(new Gson().toJson(stackMap.descendingMap()));
						TreeMap<String, Double> jstatMap = parseJstat("jstat -gcutil " + pid);
						log.info(new Gson().toJson(jstatMap.descendingMap()));

						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private TreeMap<String, Double> parseJstat(String cmd) {
		TreeMap<String, Double> memMap = Maps.newTreeMap();
		try {
			BufferedReader reader = getContent(cmd);
			String metaData = reader.readLine();
			String val = reader.readLine();
			String[] arr = metaData.trim().split("\\s+");
			String[] valArr = val.trim().split("\\s+");
			for(int i = 0; i < arr.length; i++) {
				memMap.put(arr[i], Double.valueOf(valArr[i]));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memMap;
	}

	private TreeMap<Integer, ThreadEntity> parseJstack(String cmd) {
		TreeMap<Integer, ThreadEntity> threadMap = Maps.newTreeMap();
		try {
			BufferedReader reader = getContent(cmd);
			String line = null;
			String firstLine = reader.readLine();
			String metaData = reader.readLine();
			String separatorLine = reader.readLine();
			int count = 0;
			boolean threadStatusLine = false;
			StringBuffer buffer = new StringBuffer();
			String threadStatus = "";
			ThreadEntity currThread = null;
			while((line = reader.readLine()) != null) {
				Matcher matcher = jstackPattern.matcher(line);
				if(matcher.find()) {
					if(currThread != null) {
						currThread.setStack(buffer.toString());
						buffer.setLength(0);
					}
					String threadName = matcher.group(1);
					String threadId = matcher.group(2);
					threadStatusLine = true;
					currThread = new ThreadEntity().setName(threadName);
					threadMap.put(Integer.valueOf(threadId, 16), currThread);
					continue;
				}
				if(threadStatusLine) {//开启新工作
					//查线程状态，后是线程的虚拟机栈
					String[] status = line.trim().split("\\s+");
					if(status.length <= 1) {
						continue;
					}
					threadStatusLine = false;
					threadStatus = status[1];
					currThread.setStatus(threadStatus);
					continue;
				}
				buffer.append(line).append("\r\n");
			}
			if(currThread != null) {
				currThread.setStack(buffer.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return threadMap;
	}

	private TreeMap<Integer, Entity> parseJmap(String cmd) {
		TreeMap<Integer, Entity> data = Maps.newTreeMap();
		try {
			BufferedReader reader = getContent(cmd);
			String line = null;
			String firstLine = reader.readLine();
			String metaData = reader.readLine();
			String separatorLine = reader.readLine();
			int count = 0;
			while((line = reader.readLine()) != null) {
				String[] arr = line.trim().split("\\s+");
				data.put(Integer.valueOf(arr[2]), new Entity().setCount(Integer.valueOf(arr[1])).setName(arr[3]));
				if(count++ >= 100) {
					return data;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	private Map<Integer, String> parseJps(String cmd, CmdReturnHandler handler){
		Map<Integer, String> threadIdName = Maps.newHashMap();
		BufferedReader reader;
		try {
			reader = getContent(cmd);
			String line = null;
			while((line = reader.readLine()) != null) {
				handler.handle(line, threadIdName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return threadIdName;
	}

	private BufferedReader getContent(String cmd) throws IOException, InterruptedException {
		log.info(cmd);
		Process process = runtime.exec(cmd);
		process.waitFor(2000,TimeUnit.MILLISECONDS);
		InputStream inputStream = process.getInputStream();
//		byte[] data = new byte[inputStream.available()];//不准
//		int len = inputStream.read(data);
		byte[] data = readAll(inputStream);
		String content = new String(data);
		BufferedReader reader = new BufferedReader(new StringReader(content));
		inputStream.close();
		process.destroyForcibly();
		return reader;
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
	
	public void parseJps(String line, Map<Integer, String> threadIdName) {
		String[] arr = line.split("\\s+");
		if(arr.length > 1) {
			threadIdName.put(Integer.valueOf(arr[0]), arr[1]);
		}
	}
	
	@Data
	@Accessors(chain = true)
	class Entity{
		private String name;
		private int count;
	}
	@Data
	@Accessors(chain = true)
	class ThreadEntity{
		private String stack;
		private String status;
		private String name;
	}
	
	@Test
	public void test() {
		runCmd();
	}
}
