package com.bj58.fang.cache;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
public class PuHotDataCache2 {

	Map<String, CacheEntity2> cacheMap = new ConcurrentHashMap<String, CacheEntity2>();
	private static final long maxEdle = 1000 * 60 * 60;//1h
	private static final long nonActive = 1000 * 60 * 6;//6min
	private static final long taskPerid = 1000 * 60;//1min
	private static final int iniScore = 10;//
	private static final int nAScore = 3;
	private static final int maxKeyNum = 5000;
	
	private static final int avgPerTenMin = 1;//1/10min
	private static final long tenMin = 1000 * 60 * 10;//10min
	
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
	 *代数变量T
	 * @param 
	 * @author lishaoping
	 * @Date 2018年11月24日
	 * @Package com.bj58.fang.cache
	 * @return void
	 */
	public <T> void putData(String key, IGetValByKey source) {
		long currT = System.currentTimeMillis();
		if(cacheMap.containsKey(key)) {
			CacheEntity2 entity = cacheMap.get(key);
			entity.setVisiCount(entity.getVisiCount() + 1);
		}else {
			if(cacheMap.size() < maxKeyNum) {
				T data = source.getValByKey(key);
				CacheEntity2<T> one = new CacheEntity2<T>(data, currT, 1);
				cacheMap.put(key, one);
			}else {
				//主动回收一次， 看有没有位置；；；但是性能消耗大，估计110ms，所以不进行
//				cleanCache();
			}
		}
		
		
	}
	
	/**
	 * 移除：
	 * @param 
	 * @author lishaoping
	 * @Date 2018年11月24日
	 * @Package com.bj58.fang.cache
	 * @return void
	 */
	private <T> void scheduledTaskByVisitTime(IGetValByKey source) {
		ScheduledExecutorService executor = null;
		try {
			executor = Executors.newScheduledThreadPool(2);//只启动2个线程
			executor.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					cleanCache();
				}
			}, 1, taskPerid, TimeUnit.SECONDS);
			//延迟到凌晨
			//构造明天凌晨的时间
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 2);
			long delay = (calendar.getTimeInMillis() - System.currentTimeMillis()) / 1000 / 60;
			executor.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {//周期任务--更新数据--对map里已经有的数据进行--其他数据不需要
					Iterator<Entry<String, CacheEntity2>> ite = cacheMap.entrySet().iterator();
					while(ite.hasNext()) {
						Entry<String, CacheEntity2> entity = ite.next();
						CacheEntity2 ca = entity.getValue();
						T data = source.getValByKey(entity.getKey());
						ca.setData(data);
					}
				}
			}, delay, 24 * 60, TimeUnit.MINUTES);
		}catch (Exception e) {
			if(executor != null) {
				executor.shutdownNow();
			}
		}
	} 
	
	private void cleanCache() {
		long currT = System.currentTimeMillis();
		Iterator<Entry<String, CacheEntity2>> ite = cacheMap.entrySet().iterator();
		while(ite.hasNext()) {
			Entry<String, CacheEntity2> entity = ite.next();
			CacheEntity2 ca = entity.getValue();
			float rate =  (ca.getVisiCount() / (currT - ca.getFirstTime()) / ((float)tenMin));//10min种内需要有一个
			if(rate < avgPerTenMin && cacheMap.size() > maxKeyNum) {
				ite.remove();
			}
		}
	}
	
	public void autoSeletedXiaoquCache() {
		
	}
}
