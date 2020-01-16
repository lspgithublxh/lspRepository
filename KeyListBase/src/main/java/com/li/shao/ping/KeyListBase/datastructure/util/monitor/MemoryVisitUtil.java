package com.li.shao.ping.KeyListBase.datastructure.util.monitor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
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
	
	public static MemoryVisitUtil util = new MemoryVisitUtil();
	
	private Runtime runtime = Runtime.getRuntime();
	private boolean isWin = false;
	private Pattern jstackPattern = Pattern.compile("\"([\\S\\s]+)\".+?nid\\=0x(\\S+)");
	private Pattern topPattern = Pattern.compile("top.+?,\\s+(\\d+)\\s+users,\\s+load average\\:([\\s\\S]+?\\r\\n)");
	private Pattern threadPattern = Pattern.compile("Threads:(.+?)\\r\\n");
	private Pattern cpuPattern = Pattern.compile("Cpu\\(s\\):\\s+(.+)?\\r\\n");
	private Pattern generalPattern = Pattern.compile(":(.+)?\\r\\n");
	{
		runtime.addShutdownHook(new Thread(()->{
			log.info("shutdown of runtime!");
		}) );
		String name = System.getProperty("os.name");
		if(name.startsWith("Win")) {
			isWin = true;
		}
	}
	
	public void networkPortListen() {
		//网络端口监听
		AllMonitorEntity allEntity = new AllMonitorEntity();
		
	}
	
	/**
	 * 实时输出
	 * cat /proc/stat
	 * cat /proc/meminfo
	 * iostat
	 * iotop
	 * 查看接收队列的大小 netstat -ano
	 * cat /proc/net/dev
	 * @return 
	 */
	public BaseInfoEntity memCpuNetworkDiskMonitor() {
		BaseInfoEntity base = new BaseInfoEntity();
		try {
			OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
			MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
			base.setCpuLoadAvg("" + operatingSystemMXBean.getSystemLoadAverage());
			MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
			MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
			double heapMemUse = heapMemoryUsage.getUsed() / heapMemoryUsage.getCommitted();
			double nonHeapMemUse = nonHeapMemoryUsage.getUsed() / nonHeapMemoryUsage.getCommitted();
			base.setMemUseTotalPers(heapMemUse + "," + nonHeapMemUse);
			BufferedReader content = getContent("cat /proc/sys/kernel/pid_max");
			String totalPidNum = content.readLine();
			BufferedReader content2 = getContent("pstree -p | wc -l");
			String currPidNum = content2.readLine();
			base.setThreadTotalPers(Integer.valueOf(currPidNum) + "/" + Integer.valueOf(totalPidNum));
			BufferedReader content3 = getContent("top");
			content3.readLine();
			content3.readLine();
			String cpuU = content3.readLine();
			String[] infos = cpuU.substring(cpuU.indexOf(":") + 1).trim().split(",");
			base.setCpuUseTotalPers("用户空间：" + infos[0] + " 内核空间：" + infos[1] + " 空闲cpu百分比：" + infos[3] + " 等待输入输出的cpu时间百分比：" + infos[4]);
			//磁盘利用率方面:
			BufferedReader content4 = getContent("iostat -d -k");
			content4.readLine();
			content4.readLine();
			content4.readLine();	
			String distIO = content4.readLine();
			String[] diskArr = distIO.trim().split(",\\s+");
			base.setDiskUseIn(diskArr[0] + ":" + Double.valueOf(diskArr[2]) + " kB/s");
			base.setDiskUseOut(diskArr[0] + ":" + Double.valueOf(diskArr[3]) + " kB/s");//每秒io数忽略
			base.setDiskTPS(diskArr[0] + ":" + diskArr[1] + " tps/s");
			//网络利用率方面：
			BufferedReader content5 = getContent("sar -n DEV 1 1");
			boolean start = false;
			base.setNetIoInfo(Lists.newArrayList());
			while(true) {
				String line = content5.readLine();
				if(!start) {
					if(line.contains("rxpck/s")) {
						start = true;
					}
					continue;
				}
				if(line.isEmpty()) {
					break;
				}
				String[] netInfo = line.trim().split("\\s+");
				base.getNetIoInfo().add(new NetIOinfoEntity().setName(netInfo[2])
						.setNetworkIn(netInfo[3] + "pck/s " + netInfo[5] + "kB/s")
						.setNetworkOut(netInfo[4] + "pck/s " + netInfo[6] + "kB/s"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return base;
	}
	
	public AllMonitorEntity getAllInfo() {
		AllMonitorEntity all = new AllMonitorEntity();
		try {
			List<String> mainClassList = Lists.newArrayList("ProsserApplication");
			Map<Integer, String> threadIdName = parseJps("jps", this::parseJps);
			threadIdName.entrySet().forEach(item ->{
				if(mainClassList.contains(item.getValue())) {
					//开启循环持续输出
					Integer pid = item.getKey();
					String jmapSort = isWin ? "" : " | sort -n -r -k 3";
					TreeMap<Integer, Entity> jmapData = parseJmap("jmap -histo:live " + pid + jmapSort);//-n -r -k
					TreeMap<Integer, ThreadEntity> stackMap = parseJstack("jstack -l " + pid);
					TreeMap<String, Double> jstatMap = parseJstat("jstat -gcutil " + pid);
					all.setJmapData(jmapData);
					all.setJstackMap(stackMap);
					all.setJstatMap(jstatMap);
					if(!isWin) {
						TopEntity entity = parseTop("top -Hp " + pid);
						//确定耗时最多的线程stack/cpu/mem占用最多的thread
						BaseInfoEntity base = memCpuNetworkDiskMonitor();
						all.setBase(base);
						all.setTopEntity(entity);
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return all;
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
						if(!isWin) {
							TopEntity entity = parseTop("top -Hp " + pid);
							log.info(new Gson().toJson(entity));
							//确定耗时最多的线程stack/cpu/mem占用最多的thread
							parsePerf("sudo perf record -F 99 -p " + pid + " -g -- sleep 30", pid);//持续30s.-g记录调用栈;然后产生一个庞大文件
							parsePerf2("./profiler.sh -d 60 -o collapsed -f /tmp/test_01.txt " + pid);//持续60s
							BaseInfoEntity base = memCpuNetworkDiskMonitor();
							log.info(new Gson().toJson(base));
						}
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

	/**
	 * https://www.cnblogs.com/hama1993/p/10580581.html
	 * 先安装git clone https://github.com/jvm-profiling-tools/async-profiler
		  git clone https://github.com/brendangregg/FlameGraph
		  cd async-profiler
			make
	 * @param string
	 */
	private void parsePerf2(String cmd) {
		try {
			getContent(cmd);
			String cmd2 = "perl flamegraph.pl --colors=java /tmp/test_01.txt > test_01.svg";
			getContent(cmd2);
			//上传图片到图片服务器
			//返回图片地址
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	/**
	 * cpu中正在执行的函数名+函数调用栈；99次/s采样：得出各个函数1s内出现次数；如果采样30s,16核;; 产生一个庞大文件
	 * 
	 * 提示安装：sudo apt install linux-tools-common
			sudo apt install linux-tools-4.15.0-46-generic
		采用脚本或许好点
	 */
	private void parsePerf(String cmd, Integer pid) {
		String cmd2 = "java -cp attach-main.jar:$JAVA_HOME/lib/tools.jar net.virtualvoid.perf.AttachOnce " + pid;
		try {
			getContent(cmd2);
			String cmd3 = "sudo chown root /tmp/perf-*.map";
			getContent(cmd3);
			String cmd4 = "sudo perf script | stackcollapse-perf.pl | " + 
					"  flamegraph.pl --color=java --hash > flamegraph.svg";
			getContent(cmd4);//生成svg图片；
			//上传图片到图片服务器
			//可以查看;返回图片地址；
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private TopEntity parseTop(String cmd) {
		TopEntity entity = new TopEntity();
		try {
			String line = null;
//			String data = Files.asCharSource(new File("D:\\var\\rs"), Charsets.UTF_8).read();
//			BufferedReader reader = new BufferedReader(new StringReader(data));
			BufferedReader reader = getContent(cmd);
			setBaseInfo(entity, reader);
			reader.readLine();
			String metaData = reader.readLine();
			String[] meta = metaData.trim().split("\\s+");
			int pidPos = 0;
			int cpuPos = 7;
			int memPos = 8;
			int timePos = 9;
			int commendPos = 10;
			for(int i = 0; i < meta.length; i++){
				switch (meta[i]) {
				case "PID":
					pidPos = i;
					break;
				case "%CPU":
					cpuPos = i;
					break;
				case "%MEM":
					memPos = i;
					break;
				case "TIME+":
					timePos = i;
					break;
				case "COMMAND":
					commendPos = i;
					break;
				default:
					break;
				}
			}
			entity.setThreadMap(Maps.newHashMap());
			int count = 0;
			while((line = reader.readLine()) != null) {
				if(count++ >= 30) {
					break;
				}
				String[] arr = line.trim().split("\\s+");
				Integer threadId = Integer.valueOf(arr[pidPos]);
				entity.getThreadMap().put(threadId, new SortEntity()
						.setCpu(arr[cpuPos])
						.setMem(arr[memPos])
						.setTime(arr[timePos])
						.setName(arr[commendPos])
						.setPid(threadId));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}

	private void setBaseInfo(TopEntity entity, BufferedReader reader) throws IOException {
		String top = reader.readLine();
		String thread = reader.readLine();
		String cpu = reader.readLine();
		String mem = reader.readLine();
		String swap = reader.readLine();
		Matcher topMatch = topPattern.matcher(top);
		if(topMatch.find()) {
			String userNum = topMatch.group(1);
			String loadAvg = topMatch.group(2);
			entity.setUserCount(Integer.valueOf(userNum));
			entity.setLoadAvg(loadAvg);
		}
		Matcher tMatcher = threadPattern.matcher(thread);
		if(tMatcher.find()) {
			String threadInfo = tMatcher.group(1);
			entity.setThreads(threadInfo);
		}
		Matcher cpuM = cpuPattern.matcher(cpu);
		if(cpuM.find()) {
			String cpuUse = cpuM.group(1);
			entity.setCpuUse(cpuUse);
		}
		Matcher memM = generalPattern.matcher(mem);
		if(memM.find()) {
			String memUse = memM.group(1);
			entity.setMem(memUse);
		}
		Matcher swapM = generalPattern.matcher(swap);
		if(swapM.find()) {
			String swapInfo = swapM.group(1);
			entity.setSwap(swapInfo);
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

	/**
	 * 可以周期执行，得出重复的threadid,来统计哪个线程出现次数最多，并且出现在哪个方法上;1s内串行执行几十次。
	 * @param cmd
	 * @return
	 */
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
	public static class Entity{
		private String name;
		private int count;
	}
	@Data
	@Accessors(chain = true)
	public static class ThreadEntity{
		private String stack;
		private String status;
		private String name;
	}
	@Data
	@Accessors(chain = true)
	public static class TopEntity{
		private Integer userCount;
		private String loadAvg;
		private String threads;//
		private String cpuUse;
		private String mem;
		private String swap;
		Map<Integer, SortEntity> threadMap;
	}
	@Data
	@Accessors(chain = true)
	public static class SortEntity{
		private String cpu;
		private String mem;//是进程的；所以值都是一样
		private String time;//线程运行时间, 占用cpu时间，各个的线程的运行时间不同
		private String name;
		private Integer pid;

	}
	@Data
	@Accessors(chain = true)
	public static class BaseInfoEntity{
		private String cpuUseTotalPers;
		private String memUseTotalPers;
		private String threadTotalPers;//
		private String cpuLoadAvg;//cpu负载总数比
		private String diskUseIn;
		private String diskUseOut;
		private String diskTPS;
		private List<NetIOinfoEntity> netIoInfo;
	}
	
	@Data
	@Accessors(chain = true)
	public static class  NetIOinfoEntity{
		private String networkIn;
		private String networkOut;
		private String name;
	}
	
	@Data
	@Accessors(chain = true)
	public static class AllMonitorEntity {
		private TreeMap<Integer, Entity> jmapData;
		private TreeMap<Integer, ThreadEntity> jstackMap;
		private TreeMap<String, Double> jstatMap;
		private TopEntity topEntity;
		private BaseInfoEntity base;
	}
	
	@Test
	public void test() {
		runCmd();
	}
}
