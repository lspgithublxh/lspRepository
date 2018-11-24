package com.bj58.fang.cache;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.collections.map.HashedMap;

import javafx.application.Application;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Stage;

/**
 * --回调通知方式：略---订阅方式
 * gz:1.一周一次更新；
 *  2.加载初始文件数据到内存里；看每条数据版本而从mem\源数据回调接口 更新最新版本数据；并回写到初始文件
 *  3.
 * 等待做：
 *  1.WMB主动发消息更新
 *  2.wconfig通知更新
 *  3.zookeeper更新
 *  4.更新发送邮件---错误也发送邮件：：格式不对发送邮件/短信 wmonitor可以
 * 
 * ---------------------
 * 1.最简单的取数逻辑：从mem取，没有则从center取--格式对则推mem且更新file--center也没有或者格式不对--从file里取, 有则用；；mem存1周,脏读没问题
 * 2.最简单的更新逻辑：1周一次，定时任务查询来更新---格式对mem和file都更新
 * 
 * ------------清空规定
 * 1.过期清空
 * 2.一段时间内没有访问查询
 * @ClassName:BFcache
 * @Description:
 * @Author lishaoping
 * @Date 2018年11月22日
 * @Version V1.0
 * @Package com.bj58.fang.cache
 */
@sun.misc.Contended
public class BFcache {

	@sun.misc.Contended("id")
	private int id;
	
	public static void main(String[] args) {
//		testFileRead();
		testMapGet();
//		test2();
//		testReadLock();
//		testMapUseMem();
//		memOvertimeMap();
//		autoSeletedXiaoquCache();
	}

	
	/**
	 * treemap比hashmap没有多多少内存使用----5000个多7kB
	 * @param 
	 * @author lishaoping
	 * @Date 2018年11月23日
	 * @Package com.bj58.fang.cache
	 * @return void
	 */
	private static void testMapUseMem() {
		System.gc();
		long m1 = Runtime.getRuntime().freeMemory();
		Map<String, String> m = new TreeMap<String, String>();
		String tx = "";
		for(int i = 0; i < 5000; i++) {//227KB
			tx = System.currentTimeMillis()+i+"";
			m.put(tx, System.currentTimeMillis()+"");
		}
		System.gc();
		long m2 = Runtime.getRuntime().freeMemory();
		System.out.println(m1 - m2);//B
		System.out.println((m1 - m2) / 1024);//K
		System.out.println(System.currentTimeMillis());
	}

	/**
	 * 自动缓存，根据活跃度
	 * 固定5000条
	 * 值有一个long时间，超时则重新请求mem或者接口--结果一样则不更新到map，不一样则更新-且热度值不变
	 * 值有一个热度：每天从0开始，访问一次自增1-原子自增， 每晚移除10以内的；全部设置为0--重新开始
	 * 
	 * concurrentM
	 * ---------内存占用：字符串数组比 同样多元素的对象更多；；；因为数组长度会占用
	 * -------日志离线计算：每天凌晨读取日志---排序出小区的访问量高低：取前5000个放到Hashmap里---此时都不用过期时间--上次访问时间等信息
	 * -----每隔1min统计一次，如果已经满了，则移除访问量小于2的
	 * ------每隔1min统计一次，如果当前时间和上次访问的时间差超过1min，那么分值降低--降低1分，否则不变，初始化10分，当分值为0且已经满了则移除它--最多500个（非常简单的动态优化）
	 * 
	 * @param 
	 * @author lishaoping
	 * @Date 2018年11月23日
	 * @Package com.bj58.fang.cache
	 * @return void
	 */
	public static void autoSeletedXiaoquCache() {//固定存5000条//固定访问次数保留：10次以内的去掉
//		TreeMap<String, CacheEntity> map = new TreeMap<String, CacheEntity>(); 
		
		final Map<String, CacheEntity> map = new ConcurrentHashMap<String, CacheEntity>(5000);
		for(int i = 0; i < 3000;i++) {
			map.put(i+"", new CacheEntity(i + "", System.currentTimeMillis(), 10));
			System.out.println("put data:" + i);
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				getOne();
				int index = 0;
				while(true) {
					int d = (int) (Math.random() * 5000);
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
//					System.out.println("reqeust:" + d);
					if(map.size() < 5000) {
						System.out.println(map.size());
						map.put("" + d, new CacheEntity(d + "", System.currentTimeMillis(), 10));
					}else {//准备回收
						System.out.println(map.size());
						if(map.containsKey(d+"")) {
							map.get(d+"").setHotPot(map.get(""+d).getHotPot() + 1);
						}else {
							long curr = System.currentTimeMillis();
							long min1 = 1000 * 60 * 1;
							Iterator<Entry<String, CacheEntity>> ite = map.entrySet().iterator();
							while(ite.hasNext()) {
								Entry<String, CacheEntity> entity = ite.next();
								CacheEntity ca = entity.getValue();
								if(curr - ca.getLastMod() > 30) {
									//或者移除出map
									if(ca.getHotPot() - 1 == 0) {
										map.remove(entity.getKey());
										ca = null;
										System.out.println("no visit:" + entity.getKey() + "---new add:" + d);
										map.put("" + d, new CacheEntity(d + "", System.currentTimeMillis(), 10));
										Series<Number, Number> sex = new XYChart.Series<>();
										sex.getData().add(new Data<Number, Number>(++index, d));
										chart.getData().add(sex);
									}else {
										//不活跃，减1分 
										ca.setHotPot(ca.getHotPot()-1);
										System.out.println("bu huo yue:" + entity.getKey());
									}
									
								}
							}
						}
						
					}
				}
			}
		}).start();
		//定时任务
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);//只启动一个线程
		executor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
//				System.out.println("----" + Thread.currentThread().getName());
				long curr = System.currentTimeMillis();
				long min1 = 1000 * 60 * 1;
				Iterator<Entry<String, CacheEntity>> ite = map.entrySet().iterator();
				while(ite.hasNext()) {
					Entry<String, CacheEntity> entity = ite.next();
					CacheEntity ca = entity.getValue();
					if(curr - ca.getLastMod() > min1) {
						//或者移除出map
						if(ca.getHotPot() - 1 == 0) {
							map.remove(entity.getKey());
							System.out.println("no visit:" + entity.getKey());
							ca = null;
						}else {
							//不活跃，减1分 
							ca.setHotPot(ca.getHotPot()-1);
							System.out.println("bu huo yue:" + entity.getKey());
						}
					}
				}
			}
		}, 1, 3, TimeUnit.SECONDS);
//		executor.shutdownNow();//任务线程会关闭
	}
	
	static LineChart<Number, Number> chart = null;

	private static LineChart<Number, Number> getOne() {
		Object lock = new Object();
		boolean[] ok = {false};
		Application one = new Application() {
			@Override
			public void start(Stage primaryStage) throws Exception {
				chart = ShowLine.justChart2();
				synchronized (lock) {
					lock.notify();
					ok[0] = true;
				}
			}
		};
		one.launch(new String[] {});
		synchronized (lock) {
			if(!ok[0]) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return chart;
	}
	
	/**
	 * 加了读锁，比直接读慢5倍，从0.02ms达到了0.1ms
	 * @param 
	 * @author lishaoping
	 * @Date 2018年11月23日
	 * @Package com.bj58.fang.cache
	 * @return void
	 */
	private static void testReadLock() {
	  ReadWriteLock rwl = new ReentrantReadWriteLock();
	  Map<String, String> m = new HashedMap();
		String tx = "";
		for(int i = 0; i < 50000; i++) {
			tx = System.currentTimeMillis()+i+"";
			m.put(tx, System.currentTimeMillis()+"");
		}
		long t1 = System.nanoTime();
	  try {
		rwl.readLock().lock();
		String xx = m.get(tx);
		
	  }finally {
			rwl.readLock().unlock();
	  }
	  long t2 = System.nanoTime();
	  System.out.println(((double)(t2-t1) / 1000) / 1000);
	}

	private static void memOvertimeMap() {
		Map<String, String[]> data = new HashMap<String, String[]>();
		
	}

	private static void testMapGet() {
		long t1 = System.currentTimeMillis();
		Map<String, String> m = new TreeMap<String, String>();
		String tx = "";
		for(int i = 0; i < 50000; i++) {
			tx = System.currentTimeMillis()+i+"";
			m.put(tx, System.currentTimeMillis()+"");
		}//耗时94ms
		long t2 = System.currentTimeMillis();
		long t5 = System.nanoTime();
		String rx = m.get(tx);//耗时0ms--0.02ms 非常快；；每天刷一遍
		long t6 = System.nanoTime();
		long t3 = System.currentTimeMillis();
		System.out.println(((double)(t6-t5) / 1000) / 1000);
		System.out.println(t3 - t2);
		System.out.println(t2 - t1);
	}

	private static void test2() {
		File f = new File("D:\\id2.txt");
		//定位行查询
	}

	
	
	/**
	 * 一行存所有查寻快些，和n行每行少量，全部访问完耗时多一点，查找速度和行数有关系：8ms/5万 。
	 * @param 
	 * @author lishaoping
	 * @Date 2018年11月23日
	 * @Package com.bj58.fang.cache
	 * @return void
	 */
	private static void testFileRead() {
		long t1 = System.currentTimeMillis();
		File f = new File("D:\\id.txt");
		try {
			FileReader reader = new FileReader(f);
			BufferedReader re = new BufferedReader(reader);
			String line = "";//reader.mark(3);
			while((line = re.readLine()) != null) {
				
			}
			long t2 = System.currentTimeMillis();//35ms遍历完49704行的文件
			System.out.println(t2 - t1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
