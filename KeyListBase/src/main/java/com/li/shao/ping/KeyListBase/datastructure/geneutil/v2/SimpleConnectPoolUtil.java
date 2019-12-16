package com.li.shao.ping.KeyListBase.datastructure.geneutil.v2;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.AbstractQueue;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.li.shao.ping.KeyListBase.datastructure.entity.Rejection2Entity;
import com.li.shao.ping.KeyListBase.datastructure.entity.Rejection2Entity2;
import com.li.shao.ping.KeyListBase.datastructure.geneutil.SimpleThreadPoolUtil;
import com.li.shao.ping.KeyListBase.datastructure.inter.RejectionStrategy;
import com.li.shao.ping.KeyListBase.datastructure.inter.RejectionStrategy2;
import com.li.shao.ping.KeyListBase.datastructure.inter.RejectionStrategy22;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 每个服务的每个ip下都建立一个任务队列：由多个socket来消费
 * 发送数据之后-阻塞等待响应：请求响应模式(发送成功的确认消息也是)
 * 发送数据之后-异步消费返回。
 * --直接开发完成测试成功，甚至没怎么修改
 * -------------------------
 * 不能用同一个线程池
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
	private RejectionStrategy22<Task> reject;
	
	public SimpleConnectPoolUtil() {}
	
	private SimpleThreadPoolUtil tpool = new SimpleThreadPoolUtil(20, 200, 10, 1000,
			(task) ->{task.run();log.info("rejection thread execute");;return true;}) ;
	
	private SimpleThreadPoolUtil tpool1 = new SimpleThreadPoolUtil(20, 200, 10, 1000,
			(task) ->{task.run();log.info("rejection thread execute");;return true;}) ;
	
	
	private SimpleThreadPoolUtil tpool2 = new SimpleThreadPoolUtil(200, 200, 60, 1000,
			(task) ->{task.run();log.info("rejection thread execute");;return true;}) ;
	
	
	public SimpleConnectPoolUtil(int maxWorkerNum, int maxTaskNum, int maxIdleWorkerNum
			,long maxIdelTime,RejectionStrategy22<Task> reject) {
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
			tasks.put(target, new LinkedBlockingQueue<Task>(this.maxTaskNum));
			workers.put(target, Sets.newHashSet());
		}
	}
	
	public byte[] sendDataTest(String service, String ipPort, byte[] data) {
		String target = service + "`" + ipPort;
		String user = currCallNo();
		Set<Worker> workerSet = workers.get(target);
		if(workerSet.isEmpty()) {
			synchronized (workerSet) {
				if(workerSet.isEmpty()) {
					workerSet.add(new Worker("front-worker-" + user, service, ipPort));
				}
			}
		}
		LinkedBlockingQueue<Task> queue = tasks.get(target);
		return reject.handle(new Rejection2Entity2<Task>()
				.setService(service).setUtil(this)
				.setReceivedMap(receivedMap)
				.setIpPort(ipPort).setTask(data).setQueue(queue).setUser(user));

	}
	
	public byte[] sendDataTest2(String service, String ipPort, byte[] data) {
		String target = service + "`" + ipPort;
		String user = currCallNo();
		Set<Worker> workerSet = workers.get(target);
		if(workerSet.isEmpty()) {
			synchronized (workerSet) {
				if(workerSet.isEmpty()) {
					workerSet.add(new Worker("front-worker-" + user, service, ipPort));
				}
			}
		}
		LinkedBlockingQueue<Task> queue = tasks.get(target);
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
					return reject.handle(new Rejection2Entity2<Task>().setService(service).setUtil(this)
							.setReceivedMap(receivedMap)
							.setIpPort(ipPort).setTask(data).setQueue(queue).setUser(user));
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
			tpool1.addTask(()->{
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
								countSend.incrementAndGet();
								OutputStream out = socket.getOutputStream();
								//格式化发送 TODO
								log.info("send:" + td.syn);
								formSend(data, td.syn, out);
							} catch (IOException e) {
								e.printStackTrace();
							}
						};
//						boolean addTask = tpool2.addTask(task);
						ThreadPoolUtil.getThreadPool().execute(task);
						countPoll.incrementAndGet();
						countQueue.set(taskQueue.size());
//						log.info("get task count:" + td.getSyn() + "x" + task.hashCode() + "-" + addTask);
//						new Thread(task).start();//线程组？--马上执行就可以？
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
					log.info("received: " + user.trim());
					first = true;
					receivedMap.put(user.trim(), innerCache.toByteArray());
					synchronized (user.trim().intern().intern()) {
						user.trim().intern().notifyAll();
						innerCache.reset();
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
		otherTest();
		
	}

	AtomicInteger countSend = new AtomicInteger(0);
	AtomicInteger countPoll = new AtomicInteger(0);
	AtomicInteger countQueue = new AtomicInteger(0);
	
	private static void otherTest() {
		log.info("hello");
		SimpleConnectPoolUtil util = new SimpleConnectPoolUtil(10, 20, 10, 1000, item ->{
			LinkedBlockingQueue<Task> queue = item.getQueue();
			String user = item.getUser();
			try {
				queue.put(item.getUtil().new Task().setData(item.getTask()).setSyn(user));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			log.info("buchong task" + user.intern());
			synchronized (user.intern()) {
				try {
					user.intern().wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			log.info("after buchong task" + user.intern());
			return item.getReceivedMap().get(user);
		});
		AtomicInteger count = new AtomicInteger();
		AtomicInteger count2 = new AtomicInteger();
		AtomicInteger countCall = new AtomicInteger(0);
		for(int i = 0; i < 500; i++) {
			final int j = i;
			SimpleThreadPoolUtil.pool.addTask(()->{
				for(int k = 0; k < 10; k++) {
					String send = "hello,server, rpc call" + j;
					countCall.incrementAndGet();
					byte[] received = util.sendData("user", "localhost:12345", send.getBytes());
					if(received != null) {
						log.info("success:" + new String(received));
						count.incrementAndGet();
					}else {
						log.info("success: null");
						count2.incrementAndGet();
					}
				}
			});
		}
		try {
			Thread.sleep(15000);
			System.out.println(count.get() + "," + count2.get());
			System.out.println("call-count:" + countCall.get());
			System.out.println("send-count:" + util.countSend.get());
			System.out.println("poll-count:" + util.countPoll.get());
			System.out.println("taskQueue-count:" + util.countQueue.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void justTest() {
		LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
		for(int i = 0; i < 10; i++) {
			new Thread(()->{
				while(true) {
					try {
//						synchronized (queue) {
							Runnable s = queue.poll(1000, TimeUnit.MILLISECONDS);
							log.info("runnable:" + s + " " + queue.size());
//						}
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < 50; i++) {
			new Thread(()->{
				try {
					synchronized (SimpleThreadPoolUtil.class) {
						boolean rs = queue.offer(()->{});
						log.info("offer:" + rs + " queue:" + queue.size());
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();
		}
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		justT();
	}

	private static void justT() {
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
