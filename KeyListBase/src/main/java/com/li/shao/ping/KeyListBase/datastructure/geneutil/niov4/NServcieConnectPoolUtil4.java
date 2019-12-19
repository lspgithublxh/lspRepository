package com.li.shao.ping.KeyListBase.datastructure.geneutil.niov4;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.li.shao.ping.KeyListBase.datastructure.entity.Rejection2Entity;
import com.li.shao.ping.KeyListBase.datastructure.entity.Rejection2Entity2;
import com.li.shao.ping.KeyListBase.datastructure.entity.Rejection2Entity24;
import com.li.shao.ping.KeyListBase.datastructure.entity.Rejection2Entity2I;
import com.li.shao.ping.KeyListBase.datastructure.geneutil.SimpleThreadPoolUtil;
import com.li.shao.ping.KeyListBase.datastructure.geneutil.v2.SimpleConnectPoolUtil;
import com.li.shao.ping.KeyListBase.datastructure.inter.RejectionStrategy22;
import com.li.shao.ping.KeyListBase.datastructure.inter.RejectionStrategy24;
import com.li.shao.ping.KeyListBase.datastructure.inter.RejectionStrategy2I;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * 遗漏消息的问题
 *
 * @author lishaoping
 * @date 2019年12月17日
 * @package  com.li.shao.ping.KeyListBase.datastructure.geneutil.niov3
 */
@Slf4j
public class NServcieConnectPoolUtil4 {

	private Map<String, LinkedBlockingQueue<Task>> tasks;
	public Map<SocketChannel, Worker> socketWorkerMap;
	private Map<String, Set<Worker>> workers;
	private Map<String, byte[]> receivedMap;
	private ClientSelector4 selector;
	private long maxIdelTime;
	private int maxWorkerNum;
	private int maxIdleWorkerNum;
	private int maxTaskNum;
	private RejectionStrategy24<Task> reject;
	
	private SimpleThreadPoolUtil tpool = new SimpleThreadPoolUtil(20, 200, 10, 1000,
			(task) ->{task.run();log.info("rejection thread execute");;return true;}) ;
	private SimpleThreadPoolUtil tpool2 = new SimpleThreadPoolUtil(200, 200, 60, 1000,
			(task) ->{task.run();log.info("rejection thread execute");;return true;}) ;
	private SimpleThreadPoolUtil tpool1 = new SimpleThreadPoolUtil(200, 200, 60, 1000,
			(task) ->{task.run();log.info("rejection thread execute");;return true;}) ;
	
	
	public NServcieConnectPoolUtil4(int maxWorkerNum, int maxTaskNum, int maxIdleWorkerNum
			,long maxIdelTime,RejectionStrategy24<Task> reject) {
		this.selector = new ClientSelector4(this);
		this.maxWorkerNum = maxWorkerNum;
		this.maxIdelTime = maxIdelTime;
		this.maxTaskNum = maxTaskNum;
		this.maxIdleWorkerNum = maxIdleWorkerNum;
		this.reject = reject;
		initMap();
	}
	
	
	
	private void initMap() {
		//获取全部服务
		String service = "user";
		String[] ipArr = new String[] {"localhost:12345", "localhost:13456"};
		tasks = Maps.newConcurrentMap();
		workers = Maps.newConcurrentMap();
		socketWorkerMap = Maps.newConcurrentMap();
		receivedMap = Maps.newConcurrentMap();
		int len = ipArr.length;
		for(int i = 0; i < len; i++) {
			String target = service + "`" + ipArr[i];
			tasks.put(target, new LinkedBlockingQueue<Task>(this.maxTaskNum));
			workers.put(target, Sets.newHashSet());
		}
		new Thread(()->{
			selector.selector();
		}).start();
	}

	/**
	 * 和之前的接口几乎一摸一样---且可以一摸一样：：抽象化一点
	 * @param service
	 * @param ipPort
	 * @param data
	 * @return
	 */
	public byte[] sendData(String service, String ipPort, byte[] data) {//结果数据，用户自己反序列化
		String target = service + "`" + ipPort;
		String user = currCallNo();
		LinkedBlockingQueue<Task> queue = tasks.get(target);
		Set<Worker> workerSet = workers.get(target);
		if(workerSet.isEmpty() || (queue.size() > workerSet.size() * 3 && workerSet.size() < maxWorkerNum)) {
			synchronized (Rejection2Entity2.class) {
				if(workerSet.isEmpty() || (queue.size() > workerSet.size() * 3 && workerSet.size() < maxWorkerNum)) {
					workerSet.add(new Worker("front-worker-" + user, service, ipPort));
				}
			}
		}
		if(queue.size() >= maxTaskNum) {
			synchronized (Rejection2Entity.class) {
				if(queue.size() >= maxTaskNum) {//先考虑加worker
					if(workerSet.size() >= maxWorkerNum) {
						workerSet.add(new Worker("new-worker-" + user, service, ipPort));
					}
					return reject.handle(new Rejection2Entity24<Task>().setService(service).setUtil(this)
							.setReceivedMap(receivedMap)
							.setIpPort(ipPort).setTask(data).setQueue(queue).setUser(user));
				}
			}
		}
		
		//不能用queue进行，因为queue是一个局部变量;不同的线程存放地点是不一样的,hashcode也不一样的
		synchronized (this) {//这个方法不能并发的offer,必须枷锁---否则会漏掉很多；；同理不能并发的pull
			if(queue.size() >= maxTaskNum) {
				if(workerSet.size() >= maxWorkerNum) {
					workerSet.add(new Worker("new-worker-" + user, service, ipPort));
				}
				return reject.handle(new Rejection2Entity24<Task>().setService(service).setUtil(this)
						.setReceivedMap(receivedMap)
						.setIpPort(ipPort).setTask(data).setQueue(queue).setUser(user));

			}
			boolean rs = queue.offer(new Task().setData(data).setSyn(user));
			if(!rs) {
				log.error("[add-data-to-task] error!");
				countOfferFail.incrementAndGet();
				return null;
			}else {//失败的原因？
				
			}
			countOffer.incrementAndGet();
		}
		//worker是否足够
		//等待结果并返回
		synchronized (user.trim().intern()) {
			try {
				user.trim().intern().wait();
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
	
	AtomicInteger countOffer = new AtomicInteger(0);
	AtomicInteger countOfferFail = new AtomicInteger(0);
	
	@Data
	@Accessors(chain = true)
	class Task{
		private byte[] data;
		private String syn;
	}
	
	@Data
	@Accessors(chain = true)
	class Worker{
		
		private String service;
		private String ip;
		private int port;
		private String targetIpPort;
		private String name;
		private SocketChannel channel;
		public Worker(String name, String service, String targetIpPort) {
			this.name = name;
			this.service = service;
			this.targetIpPort = targetIpPort;
			String[] arr = targetIpPort.split(":");
			this.ip = arr[0];
			this.port = Integer.valueOf(arr[1]);
			try {
				this.channel = SocketChannel.open();
				this.channel.configureBlocking(false);
				selector.register(channel);
				socketWorkerMap.put(channel, this);
				this.channel.connect(new InetSocketAddress("127.0.0.1", 10023));
				tpool.addTask(()->{
					startWork();//主动方,开始写
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void startWork() {
			try {
				//等待连接好--连接建立
				synchronized (this.name.intern()) {
					if(!selector.linkMap.containsKey(channel)) {
						this.name.intern().wait();
					}
				}
				//开始读准备
				tpool2.addTask(()->{
					readData();
				});
//				while(true) {
//					if(selector.linkMap.containsKey(this.channel)) {
//						break;
//					}else {
//						Thread.sleep(30);//或者用激发模式：先阻塞
//					}
//				}
				//只监听写即可
				String target = service + "`" + targetIpPort;
				LinkedBlockingQueue<Task> taskQueue = tasks.get(target);
				tpool.addTask(() -> {
					try {
						while (true) {
							Task td;
							synchronized (workers) {//必须线程同步拉，但又不能喝offer同步//但是仍然可能会少拉一个
								td = taskQueue.poll(maxIdelTime, TimeUnit.MILLISECONDS);
							}
							if (td == null) {//超时了，删除worker
								synchronized (tasks) {
									if (workers.size() > maxIdleWorkerNum) {
										workers.get(target).remove(this);
										break;
									}
									continue;
								}
							}
							byte[] data = td.getData();//需要连带 syn一起发送
							Runnable task = ()->{
								try {
									//格式化发送 TODO
									log.info("send:" + td.syn);
									log.info("send data:" + new String(data));
									countSend.incrementAndGet();
									writeData(data, td.syn);
								} catch (Exception e) {
									e.printStackTrace();
								}
							};
							boolean addTask = tpool2.addTask(task);
							countPoll.incrementAndGet();
							countQueue.set(taskQueue.size());
							
							synchronized (td.syn.trim().intern()) {
								td.syn.trim().intern().wait();
							}
						}
					}catch (Exception e) {
						e.printStackTrace();
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void readData() {//被调用者需要异步
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			try {
				synchronized (this.name.intern()) {//等待selector激活
					if(!selector.readMap.containsKey(channel)) {
						this.name.intern().wait();
					}
					selector.readMap.remove(channel);//用true/false效率更高
				}
				byte[] cache = new byte[1024];
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				boolean first = true;
				int num = 0;
				String user = "";
				while(true) {
					int count = 1;
					while(count > 0) {
						count = channel.read(buffer);
						if(count == 0) {
							break;
						}
						buffer.flip();
						buffer.get(cache, 0, count);
						if(first) {//第一次
							num = ((cache[0] & 0xff) << 24) + ((cache[1] & 0xff) << 16) + ((cache[2] & 0xff) << 8) + cache[3];
							if(num > 0) {
								first = false;
							}
							//计算响应谁的64个字节
							user = new String(cache, 4, 68);
							buffer.clear();
							continue;
						}
						out.write(cache, 0, count);
						if(--num == 0) {//读取完毕，放到用户区域
							log.info("received: " + user.trim());
							first = true;
							synchronized (user.trim().intern()) {//激发调用方返回
								receivedMap.put(user.trim(), out.toByteArray());
								user.trim().intern().notifyAll();
								out.reset();
							}
							buffer.clear();
							break;
						}
					}
					synchronized (this.name.intern()) {//等待selector激活
						if(!selector.readMap.containsKey(channel)) {
							this.name.intern().wait();
						}
						selector.readMap.remove(channel);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void writeData(byte[] data, String user) {
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
			justSend(header, 0, header.length);
			//发送正式数据
			//发送有效数据
			int startPos = 0;
			int endPos = 1024 > data.length ? data.length : 1024;
			byte[] buffer = new byte[1024];
			int j = startPos;
			for(int i = 0; i < 1024; j++,i++) {
				if(j >= endPos) {
					//临时数据也开始刷
					justSend(buffer, 0, i);
					break;
				}
				buffer[i] = data[j]; 
				if(i == 1023) {
					i = -1;
					//开始刷数据,完整
					justSend(buffer, 0, buffer.length);
				}
			}
		}

		private void justSend(byte[] header, int offset, int len) {
			ByteBuffer buf = ByteBuffer.wrap(header, offset, len);//"hello,server".getBytes()
			try {
				if(channel.isConnectionPending()) {
					log.info("[pending]");
					channel.finishConnect();
				}
				while (buf.hasRemaining()) {
					channel.write(buf);
				}
			} catch (Exception e) {
				e.printStackTrace();
//				try {
//					countSendFail.incrementAndGet();
//					if(channel.isConnectionPending()) {
//						System.out.println("[pending]");
//						channel.finishConnect();
//					}
//					while (buf.hasRemaining()) {
//						channel.write(buf);
//					} 
//				} catch (Exception e2) {
//					e.printStackTrace();
//				}
			}
		}
		
	}
	
	AtomicInteger countSend = new AtomicInteger(0);
	AtomicInteger countSendFail = new AtomicInteger(0);
	AtomicInteger countPoll = new AtomicInteger(0);
	AtomicInteger countQueue = new AtomicInteger(0);
	
	public static void main(String[] args) {
		otherTest();
	}
	
	private static void otherTest() {
		log.info("hello");
		AtomicInteger countLabor = new AtomicInteger(0);
		NServcieConnectPoolUtil4 util = new NServcieConnectPoolUtil4(10, 20, 10, 1000, item ->{
			LinkedBlockingQueue<Task> queue = item.getQueue();
			String user = item.getUser();
			try {
				countLabor.incrementAndGet();
				synchronized (item.getUtil()) {
					queue.put(item.getUtil().new Task().setData(item.getTask()).setSyn(user));
				}
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
		AtomicInteger countFirst = new AtomicInteger(0);
		AtomicLong endTime = new AtomicLong(0);
		long t1 = System.currentTimeMillis();
		for(int i = 0; i < 3000; i++) {
			final int j = i;
			newThread(util, count, count2, countCall, countFirst, endTime, j);
		}
		 
		try {
			Thread.sleep(40000);
			System.out.println(count.get() + "," + count2.get());
			System.out.println("turn-count:" + countFirst.get());
			System.out.println("call-count:" + countCall.get());
			System.out.println("send-count:" + util.countSend.get());
			System.out.println("sendfailed-count:" + util.countSendFail.get());
			System.out.println("poll-count:" + util.countPoll.get());
			System.out.println("offer-count:" + util.countOffer.get());
			System.out.println("offerfaield-count:" + util.countOfferFail.get());
			System.out.println("offer-labor:" + countLabor.get());
			System.out.println("taskQueue-count:" + util.countQueue.get());
			System.out.println("total-time:" + (endTime.get() - t1));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}



	private static void newThread(NServcieConnectPoolUtil4 util, AtomicInteger count, AtomicInteger count2,
			AtomicInteger countCall, AtomicInteger countFirst, AtomicLong endTime, final int j) {
		new Thread(()->{
			try {
				countFirst.incrementAndGet();
				for(int k = 0; k < 30; k++) {
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
					endTime.set(System.currentTimeMillis());
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}
}
