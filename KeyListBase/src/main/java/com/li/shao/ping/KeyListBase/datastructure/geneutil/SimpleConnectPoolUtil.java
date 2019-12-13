package com.li.shao.ping.KeyListBase.datastructure.geneutil;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.li.shao.ping.KeyListBase.datastructure.inter.RejectionStrategy;
import com.li.shao.ping.KeyListBase.datastructure.inter.RejectionStrategy2;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 每个服务的每个ip下都建立一个任务队列：由多个socket来消费
 * 发送数据之后-阻塞等待响应：请求响应模式(发送成功的确认消息也是)
 * 发送数据之后-异步消费返回。
 * --直接开发完成测试成功，甚至没怎么修改
 * @author lishaoping
 * @date 2019年12月12日
 * @package  com.li.shao.ping.KeyListBase.datastructure.geneutil
 */
@Slf4j
@SpringBootApplication
public class SimpleConnectPoolUtil {

	private Map<String, LinkedBlockingQueue<Task>> tasks;
	private Map<String, Set<Worker>> workers;
	private Map<String, byte[]> receivedMap;
	private long maxIdelTime;
	private int maxWorkerNum;
	private int maxIdleWorkerNum;
	private int maxTaskNum;
	private RejectionStrategy2 reject;
	
	public SimpleConnectPoolUtil() {}
	
	private SimpleThreadPoolUtil tpool = new SimpleThreadPoolUtil(100, 200, 10, 1000,
			(task) ->{task.run();return true;}) ;
	
	public SimpleConnectPoolUtil(int maxWorkerNum, int maxTaskNum, int maxIdleWorkerNum
			,long maxIdelTime,RejectionStrategy2 reject) {
		this.maxWorkerNum = maxWorkerNum;
		this.maxIdelTime = maxIdelTime;
		this.maxTaskNum = maxTaskNum;
		this.maxIdleWorkerNum = maxIdleWorkerNum;
		this.reject = reject;
		initMap();
	}
	
	private void initMap() {
		//从注册中心获取 所有的调用服务及其ip:port列表
		String service = "user";
		String[] ipArr = new String[] {"localhost:12345", "localhost:13456"};
		tasks = Maps.newHashMap();
		receivedMap = Maps.newHashMap();
		workers = Maps.newHashMap();
		int len = ipArr.length;
		for(int i = 0; i < len; i++) {
			String target = service + "`" + ipArr[i];
			tasks.put(target, new LinkedBlockingQueue<Task>());
			workers.put(target, Sets.newHashSet());
		}
	}
	
	public byte[] sendData(String service, String ipPort, byte[] data) {//结果数据，用户自己反序列化
		String target = service + "`" + ipPort;
		String user = currCallNo();
		LinkedBlockingQueue<Task> queue = tasks.get(target);
		Set<Worker> workerSet = workers.get(target);
		if(queue.size() >= maxTaskNum) {
			synchronized (queue) {
				if(queue.size() >= maxTaskNum) {//先考虑加worker
					if(workerSet.size() >= maxWorkerNum) {
						workerSet.add(new Worker("new-worker-" + user, service, ipPort));
					}
					return reject.handle(service, ipPort, data);
				}
			}
		}
		if(workerSet.isEmpty() || (queue.size() > workerSet.size() * 3 && workerSet.size() < maxWorkerNum)) {
			synchronized (workerSet) {
				if(workerSet.isEmpty() || (queue.size() > workerSet.size() * 3 && workerSet.size() < maxWorkerNum)) {
					workerSet.add(new Worker("front-worker-" + user, service, ipPort));
				}
			}
		}
		boolean rs = queue.offer(new Task().setData(data).setSyn(user));
		if(!rs) {
			log.error("[add-data-to-task] error!");
			return null;
		}
		//worker是否足够
		//等待结果并返回
		synchronized (user.intern()) {
			try {
				user.intern().wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return receivedMap.get(user);
	}
	
	private String currCallNo() {
		long now = System.currentTimeMillis();
		int num = increNum();
		return now + "a" + num;
	}
	
	private AtomicInteger no = new AtomicInteger(0);
	
	private int increNum() {
		int incre = no.incrementAndGet();
		no.compareAndSet(100000, 0);
		return incre;
	}
	
	@Data
	@Accessors(chain = true)
	class Task{
		private byte[] data;
		private String syn;
	}
	
	/**
	 * 更好的是给：每个1024数据包都带上一个归属头
	 *
	 * @author lishaoping
	 * @date 2019年12月13日
	 * @package  com.li.shao.ping.KeyListBase.datastructure.geneutil
	 */
	@Data
	@Accessors(chain = true)
	class Worker{
		
		private String service;
		private String ip;
		private int port;
		private Socket socket;
		private String targetIpPort;
		private String name;
		
		public Worker(String name, String service, String targetIpPort){
			this.name = name;
			this.service = service;
			this.targetIpPort = targetIpPort;
			String[] arr = targetIpPort.split(":");
			this.ip = arr[0];
			this.port = Integer.valueOf(arr[1]);
			try {
				socket = new Socket(ip, port);
				start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void start() {//只是执行一次，没有并发的问题
			String target = service + "`" + targetIpPort;
			LinkedBlockingQueue<Task> taskQueue = tasks.get(target);
			tpool.addTask(()->{
				try {
					InputStream in = socket.getInputStream();
					formatRead(in);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			tpool.addTask(()->{
				while(true) {
					try {//发送数据
						Task td = taskQueue.poll(maxIdelTime, TimeUnit.MILLISECONDS);
						if(td == null) {//超时了，删除worker
							synchronized (tasks) {
								if(workers.size() > maxIdleWorkerNum) {
									workers.get(target).remove(this);
									break;
								}
								continue;
							}
						}
						byte[] data = td.getData();//需要连带 syn一起发送
						Runnable task = ()->{
							try {
								log.info("send before:" + td.syn);
								OutputStream out = socket.getOutputStream();
								//格式化发送 TODO
								log.info("send:" + td.syn);
								formSend(data, td.syn, out);
							} catch (IOException e) {
								e.printStackTrace();
							}
						};
						boolean addTask = tpool.addTask(task);
						//TODO 等待读取完毕，才开始下一个任务的拉取
						synchronized (td.syn.trim().intern()) {
							td.syn.trim().intern().wait();
						}
						//等一会儿再发送看
//						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				log.info("[worker-exit-]" + name);
			});
		}

		private void formatRead(InputStream in) throws IOException {
			DataInputStream input = new DataInputStream(in);
			byte[] cache = new byte[1024];//第一个，前4个是块的个数
			ByteArrayOutputStream innerCache = new ByteArrayOutputStream();
			boolean first = true;
			int num = 0;
			String user = "";
			while(true) {
				int len = input.read(cache);
				if(first) {//计算块个数：
					num = ((cache[0] & 0xff) << 24) + ((cache[1] & 0xff) << 16) + ((cache[2] & 0xff) << 8) + cache[3];
					if(num > 0) {
						first = false;
					}
					//计算响应谁的64个字节
					user = new String(cache, 4, 68);
					continue;
				}
				innerCache.write(cache, 0, len);
				if(--num == 0) {//读取完毕，放到用户区域
					log.info("received:");
					first = true;
					receivedMap.put(user.trim(), cache);
					synchronized (user.trim().intern().intern()) {
						user.trim().intern().notifyAll();
					}
				}
			}
		}
		
		public void formSend(byte[] data, String user, OutputStream out) {//以后byte[]可以换成list<byte[]> 或者byte[][]
			byte[] syn = user.getBytes();
			int blockLen = data.length / 1024 + (data.length % 1024 > 0 ? 1:0);
			byte[] header = new byte[1024];
			header[0] = (byte)(blockLen >> 24);
			header[1] = (byte)(blockLen >> 16);
			header[2] = (byte)(blockLen >> 8);
			header[3] = (byte)(blockLen >> 0);
			//增加64字节user
			int index = 4;
			for(byte s : syn) {
				header[index++] = s;
				if(index > 67) {//截取
					break;
				}
			}
			
			try {
				//发送头数据
				out.write(header);
				out.flush();//发送一个结束符？
				//发送有效数据
				int startPos = 0;
				int endPos = 1024 > data.length ? data.length : 1024;
				byte[] buffer = new byte[1024];
				int j = startPos;
				for(int i = 0; i < 1024; j++,i++) {
					if(j >= endPos) {
						//临时数据也开始刷
						out.write(buffer, 0, i);
						out.flush();
						break;
					}
					buffer[i] = data[j]; 
					if(i == 1023) {
						i = -1;
						//开始刷数据,完整
						out.write(buffer);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

		
		public void read() {
			
		}
	}
	
	
	public static void main(String[] args) {
//		SpringApplication.run(SimpleConnectPoolUtil.class, args);
//		justTest();
		//
		log.info("hello");
		SimpleConnectPoolUtil util = new SimpleConnectPoolUtil(100, 200, 10, 1000, (servie, ipPort, data)->{
			return null;//或者直接亲自new 一个worker发送；但是麻烦
		});
		for(int i = 0; i < 20; i++) {
			final int j = i;
			SimpleThreadPoolUtil.pool.addTask(()->{
				for(int k = 0; k < 1; k++) {
					String send = "hello,server, rpc call" + j;
					byte[] received = util.sendData("user", "localhost:12345", send.getBytes());
					if(received != null) {
						log.info("success:" + new String(received));
					}else {
						log.info("success: null");
					}
				}
			});
		}
		
	}

	private static void justTest() {
		Integer a = 111;
		System.out.println(a & 0x00FF);
		System.out.println(a & 0xFF00);
		System.out.println(a & 0xFF0000);
		System.out.println(a & 0xFF000000);
		System.out.println("-----------");
		System.out.println((byte)(a >> 24));
		System.out.println((byte)(a >> 16));
		System.out.println((byte)(a >> 8));
		System.out.println((byte)(a >> 0));
		System.out.println("-----------");
		byte[] s = new byte[] {0,0,1,1};
		System.out.println((s[0] & 0xff) << 24);
		System.out.println(((s[0] & 0xff) << 24) + ((s[1] & 0xff) << 16) + ((s[2] & 0xff) << 8) + s[3]);
	}
}
