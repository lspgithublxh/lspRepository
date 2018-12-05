package com.bj58.fang.cache.HotDataMemCache;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * 类似jvm堆内存3代划分，先缓存--再转移--转移算法是更高的访问速率、等基本策略
 * @ClassName:ErJIHotDataCache
 * @Description:
 * @Author lishaoping
 * @Date 2018年11月28日
 * @Version V1.0
 * @Package com.bj58.fang.cache.HotDataMemCache
 */
public class ErJIHotDataCache<K, T> extends AbstractHotDataCache<K, T, AbstractCacheEntity<T>>{// 
	
	static {
		try {
			URL url = new URL("file:///D:/scf_log2.xml");
			DOMConfigurator.configure(url); 
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	private static final Logger logger = Logger.getLogger(ErJIHotDataCache.class);

	private AbstractHotDataCache<K, T, ? extends AbstractCacheEntity<T>> cache = null;
	
	private Map<K, CacheEntity3<T>> fromCache = new ConcurrentHashMap<K, CacheEntity3<T>>();
	private long maxKeyNum;
	private IGetValByKey<K, T> source = null;
	private int numPerStatUnit = 1;//1/10min
	private long statUnit = 1000 * 60 * 5;//5min
	private int rateOkCount = 3;
	
	public ErJIHotDataCache(AbstractHotDataCache<K, T, ? extends AbstractCacheEntity<T>> cache, IGetValByKey<K, T> source) {
		this.cache = cache;
		this.source = source;
	}
	
	private long avgGetTime = -1;
	private long avgCleanTime = -1;
	
	
	public long getAvgCleanTime() {
		return avgCleanTime;
	}
	
	public long getAvgGetTime() {
		return avgGetTime;
	}
	
	/**
	 * 同等频率的回收，但是速率要求更严格---survivor则到二级缓存里
	 */
	@Override
	public void cleanCache() {
		long currT = System.currentTimeMillis();
		long t1 = System.nanoTime();
		Iterator<Entry<K, CacheEntity3<T>>> ite = fromCache.entrySet().iterator();
		int count = 0;
		while(ite.hasNext()) {
			Entry<K, CacheEntity3<T>> entity = ite.next();
			CacheEntity3<T> ca = entity.getValue();
			float rate =  (ca.getVisiCount() / ((float)(currT - ca.getFirstTime()) / ((float)statUnit)));//10min种内需要有一个
			if(rate < numPerStatUnit ) {//&& cacheMap.size() >= maxKeyNum   移除要求更严，所以缓存量只能稳步增增
				ite.remove();
				count++;
			}
			//转移到cache里
			if(ca.getRateOkCount() >= rateOkCount) {
				//转移
				System.out.println("--------zhuanyi  occure--------");
				int rs = cache.putData(entity.getKey(), ca.getData());
				if(rs == 1) {
					ite.remove();
				}
			}
		}
		long t2 = System.currentTimeMillis();
		long t3 = System.nanoTime();
		logger.info(String.format("clean cacheKey total:%s, take :%s ms", count, t2 - currT));
		if(count != 0) {
			avgCleanTime = avgCleanTime == -1 ? (t3 - t1) /1000 / count : (avgCleanTime + (t3 - t1) /1000 / count) / 2;
		}
	}

	@Override
	public T getData(K key) {
		AbstractCacheEntity<T> en = cache.cacheMap.get(key);
		if(en != null) {
			return en.data;
		}
		T data = null;
		long currT = System.currentTimeMillis();
		long t1 = System.nanoTime();
		CacheEntity3<T> entity = fromCache.get(key);
		if(entity != null) {
			entity.setVisiCount(entity.getVisiCount() + 1);
			entity.setRateOkCount(entity.getRateOkCount() + 1);
			System.out.println(entity.getRateOkCount() + "---" + key);
			data = entity.getData();
			//也可以转移
		}else {
			if(fromCache.size() < maxKeyNum) {
				data = source.getValByKey(key);
				CacheEntity3<T> one = new CacheEntity3<T>(data, currT, 1, 0);
				fromCache.put(key, one);
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

	@Override
	public void configAndStartClean(CacheConfig config) {
		taskPerid = config.getTaskPerid();
		maxKeyNum = config.getMaxKeyNum();
		numPerStatUnit = config.getNumPerStatUnit();
		statUnit = config.getStatUnit();
		updateDelay = config.getUpdateDelay();
		updatePerid = config.getTaskPerid();
		rateOkCount = config.getRateOkCount();
		taskStartHM = config.getTaskStartHM();
		taskEndHM = config.getTaskEndHM();
		scheduledTaskByVisitTime(source);
	}

	@Override
	public int putData(K key, T data) {
		return 0;
	}

	public int getMapSize() {
		return fromCache.size();
	}

	
}
