package com.bj58.fang.cache;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * @ClassName:HotDataCache
 * @Description:
 * @Author lishaoping
 * @Date 2018年11月24日
 * @Version V1.0
 * @Package com.bj58.fang.cache
 */
public class PuHotDataCache2<T> {

	private static final Logger logger = Logger.getLogger(PuHotDataCache2.class);
	
	private Map<String, CacheEntity2<T>> cacheMap = new ConcurrentHashMap<String, CacheEntity2<T>>();
	private long taskPerid = 1000 * 60;//1min
	private int maxKeyNum = 5000;
	
	private int numPerStatUnit = 1;//1/10min
	private long statUnit = 1000 * 60 * 10;//10min
	
	private long updateDelay = 0;
	private long updatePerid = 24 * 60 * 60 * 1000;
	
	private IGetValByKey<T> source = null;
	
	public PuHotDataCache2(IGetValByKey<T> source) throws NoCallbackInterException {
		super();
		if(source != null) {
			this.source = source;
		}else {
			throw new NoCallbackInterException("no data fund");
		}
	}
	
	public void configAndStartClean(CacheConfig config) {
		taskPerid = config.getTaskPerid();
		maxKeyNum = config.getMaxKeyNum();
		numPerStatUnit = config.getNumPerStatUnit();
		statUnit = config.getStatUnit();
		updateDelay = config.getUpdateDelay();
		updatePerid = config.getTaskPerid();
		scheduledTaskByVisitTime(source);
	}
	
	public int getMapSize() {
		return cacheMap.size();
	}

	public static void main(String[] args) {
//		Map<String, String> m = new TreeMap<String, String>();
//		String tx = "";
//		for(int i = 0; i < 5000; i++) {
//			tx = System.currentTimeMillis()+i+"";
//			m.put(tx, System.currentTimeMillis()+"");
//		}
//		long t1 = System.currentTimeMillis();
//		Iterator<Entry<String, String>> i = m.entrySet().iterator();
//		while(i.hasNext()) {
//			Entry<String, String> v = i.next();
//			System.out.println(v);
//		}
//		long t2 = System.currentTimeMillis();
//		System.out.println( t2 - t1);//遍历耗时分析, 136ms是可能的
		
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 2);
		System.out.println(calendar.getTime());
		System.out.println(calendar.getTimeInMillis() - System.currentTimeMillis());
//		SimpleDateFormat format = new SimpleDateFormat("");
	}
	
	/**
	 *《！--只对单条数据启动一次扣分和remove判读，
	 *
	 * @param 
	 * @author lishaoping
	 * @Date 2018年11月24日
	 * @Package com.bj58.fang.cache
	 * @return void
	 */
	public T getData(String key) {
		T data = null;
		long currT = System.currentTimeMillis();
		if(cacheMap.containsKey(key)) {
			CacheEntity2<T> entity = cacheMap.get(key);
			entity.setVisiCount(entity.getVisiCount() + 1);
			data = entity.getData();
		}else {
			if(cacheMap.size() < maxKeyNum) {
				data = source.getValByKey(key);
				CacheEntity2<T> one = new CacheEntity2<T>(data, currT, 1);
				cacheMap.put(key, one);
				logger.info(String.format("put key success: %s", key));
			}else {
				//主动回收一次， 看有没有位置；；；但是性能消耗大，估计110ms，所以不进行
//				cleanCache();
			}
		}
		return data;
		
	}
	
	/**
	 * 移除：
	 * @param 
	 * @author lishaoping
	 * @Date 2018年11月24日
	 * @Package com.bj58.fang.cache
	 * @return void
	 */
	private void scheduledTaskByVisitTime(IGetValByKey<T> source) {
		ScheduledExecutorService executor = null;
		try {
			executor = Executors.newScheduledThreadPool(2);//只启动2个线程
			executor.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					try {
						cleanCache();
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
				}
			}, 1, taskPerid, TimeUnit.MILLISECONDS);
			//延迟到凌晨
			//构造明天凌晨的时间
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 2);
			if(updateDelay == 0) {
				updateDelay = (calendar.getTimeInMillis() - System.currentTimeMillis()) / 1000 / 60;
			}
			executor.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {//周期任务--更新数据--对map里已经有的数据进行--其他数据不需要
					long t1 = System.currentTimeMillis();
					Iterator<Entry<String, CacheEntity2<T>>> ite = cacheMap.entrySet().iterator();
					while(ite.hasNext()) {
						Entry<String, CacheEntity2<T>> entity = ite.next();
						CacheEntity2<T> ca = entity.getValue();
						T data = source.getValByKey(entity.getKey());
						ca.setData(data);
					}
					long t2 = System.currentTimeMillis();
					logger.info(String.format("update cache ok, total:%s, take %s ms", cacheMap.size(), t2 - t1));
				}
			}, updateDelay, updatePerid, TimeUnit.MILLISECONDS);
		}catch (Exception e) {
			if(executor != null) {
				executor.shutdownNow();
			}
			logger.error("task run error!" + e.getMessage());
		}
	} 
	
	private void cleanCache() {
		long currT = System.currentTimeMillis();
		Iterator<Entry<String, CacheEntity2<T>>> ite = cacheMap.entrySet().iterator();
		int count = 0;
//		List<String> keyList = new ArrayList<>();
		while(ite.hasNext()) {
			Entry<String, CacheEntity2<T>> entity = ite.next();
			CacheEntity2<T> ca = entity.getValue();
			float rate =  (ca.getVisiCount() / ((float)(currT - ca.getFirstTime()) / ((float)statUnit)));//10min种内需要有一个
			System.out.println(rate);
			if(rate < numPerStatUnit ) {
//				keyList.add(entity.getKey());
				ite.remove();
				count++;
//				try {
//					Thread.sleep(30);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}//等待新的请求填充
				System.out.println("remove ok:");
			}
		}
		long t2 = System.currentTimeMillis();
		logger.info(String.format("clean cacheKey total:%s, take :%s ms", count, t2 - currT));
	}
	
	public void autoSeletedXiaoquCache() {
		
	}
}
