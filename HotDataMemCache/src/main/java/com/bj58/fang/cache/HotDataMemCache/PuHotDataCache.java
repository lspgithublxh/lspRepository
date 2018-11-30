package com.bj58.fang.cache.HotDataMemCache;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * 
 * map大小稳步增长，更安全
 * @ClassName:HotDataCache
 * @Description:
 * @Author lishaoping
 * @Date 2018年11月24日
 * @Version V1.0
 * @Package com.bj58.fang.cache
 */
public class PuHotDataCache<K, T> extends AbstractHotDataCache<K, T, CacheEntity2<T>>{

	static {
		try {
			URL url = new URL("file:///D:/scf_log2.xml");
			DOMConfigurator.configure(url); 
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	private static final Logger logger = Logger.getLogger(PuHotDataCache.class);
	
	private int maxKeyNum = 5000;
	
	private int numPerStatUnit = 1;//1/10min
	private long statUnit = 1000 * 60 * 10;//10min
	
	private IGetValByKey<K, T> source = null;
	private long avgGetTime = -1;
	private long avgCleanTime = -1;
	
	public PuHotDataCache(IGetValByKey<K, T> source) throws NoCallbackInterException {
		super();
		if(source != null) {
			this.source = source;
			cacheMap = new ConcurrentHashMap<K, CacheEntity2<T>>();
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
	
	public long getAvgCleanTime() {
		return avgCleanTime;
	}
	
	public long getAvgGetTime() {
		return avgGetTime;
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
	public T getData(K key) {
		T data = null;
		long currT = System.currentTimeMillis();
		long t1 = System.nanoTime();
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
		long t2 = System.nanoTime();
		avgGetTime = avgGetTime == -1 ? (t2 - t1) / 1000: (avgGetTime + (t2 - t1) / 1000) / 2;
		logger.info(String.format("get total time %s", avgGetTime));
		return data;
		
	}
	
	public void cleanCache() {
		long currT = System.currentTimeMillis();
		long t1 = System.nanoTime();
		Iterator<Entry<K, CacheEntity2<T>>> ite = cacheMap.entrySet().iterator();
		int count = 0;
		while(ite.hasNext()) {
			Entry<K, CacheEntity2<T>> entity = ite.next();
			CacheEntity2<T> ca = entity.getValue();
			float rate =  (ca.getVisiCount() / ((float)(currT - ca.getFirstTime()) / ((float)statUnit)));//10min种内需要有一个
//			System.out.println(rate);
			if(rate < numPerStatUnit ) {//&& cacheMap.size() >= maxKeyNum   移除要求更严，所以缓存量只能稳步增增
				ite.remove();
				count++;
//				System.out.println("remove ok:");
			}
		}
		long t2 = System.currentTimeMillis();
		long t3 = System.nanoTime();
		logger.info(String.format("clean cacheKey total:%s, take :%s ms", count, t2 - currT));
		if(count != 0) {
			avgCleanTime = avgCleanTime == -1 ? (t3 - t1) /1000 / count : (avgCleanTime + (t3 - t1) /1000 / count) / 2;
		}
	}
	
	public void autoSeletedXiaoquCache() {
		
	}

	/**
	 * 更活跃，但性能没那么好
	 */
	@Override
	public int putData(K key, T data) {
		long currT = System.currentTimeMillis();
		int ok = 0;
		if(cacheMap.containsKey(key)) {
			CacheEntity2<T> entity = cacheMap.get(key);
			entity.setVisiCount(entity.getVisiCount() + 1);
			ok = 1;
		}else {
			if(cacheMap.size() <= maxKeyNum) {
				CacheEntity2<T> one = new CacheEntity2<T>(data, currT, 1);
				cacheMap.put(key, one);
				logger.info(String.format("put key success: %s", key));
				ok = 1;
			}
		}
		return ok;
	}
}
