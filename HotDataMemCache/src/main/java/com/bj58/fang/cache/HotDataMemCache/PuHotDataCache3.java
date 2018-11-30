package com.bj58.fang.cache.HotDataMemCache;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

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
public class PuHotDataCache3<K, T> extends AbstractHotDataCache<K, T, CacheEntity2<T>>{

	private static final Logger logger = Logger.getLogger(PuHotDataCache3.class);
	
	private int maxKeyNum = 5000;
	
	private int numPerStatUnit = 1;//1/10min
	private long statUnit = 1000 * 60 * 10;//10min
	
	private IGetValByKey<K, T> source = null;
	
	public PuHotDataCache3(IGetValByKey<K, T> source) throws NoCallbackInterException {
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
	public T getData(K key) {
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
	
	public void cleanCache() {
		long currT = System.currentTimeMillis();
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
		logger.info(String.format("clean cacheKey total:%s, take :%s ms", count, t2 - currT));
	}
	
	public void autoSeletedXiaoquCache() {
		
	}

	@Override
	public int putData(K key, T data) {
		return 0;
		
	}
}
