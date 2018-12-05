package com.bj58.fang.cache.HotDataMemCache;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

/**
 * saveTime最长缓存时间，超过也会被清除------所以可以提前删除--也就是<1天时，比如1h这种
 * lastStatisTime上次统计时间，以便计算时间周期内总共的访问次数---而不是累计平均每分钟访问次数
 * map大小稳步增长，更安全
 * 满了就删除	排序上---删除5%   ;;;;;不自己实现，则别人也会实现
 * @ClassName:HotDataCache
 * @Description:
 * @Author lishaoping
 * @Date 2018年11月24日
 * @Version V1.0
 * @Package com.bj58.fang.cache
 */
public class PuHotDataCache_sort<K, T> extends AbstractHotDataCache<K, T, CacheEntity4<K,T>>{

	private static final Logger logger = Logger.getLogger(PuHotDataCache_sort.class);
	
	private int maxKeyNum = 5000;
	
	private int numPerStatUnit = 1;//1/10min
	private long statUnit = 1000 * 60 * 10;//10min
	private long saveTime = 24 * 60 * 60 * 1000;//缓存时间1天
	private int percent = 10;
	
	private IGetValByKey<K, T> source = null;
	
	TreeMap<CacheEntity4<K, T>, String> sortmap = new TreeMap<>(new Comparator<CacheEntity4<K, T>>() {
		@Override
		public int compare(CacheEntity4<K, T> o1, CacheEntity4<K, T> o2) {
			if(o1.getVisiCount() > o2.getVisiCount()) {
				return 1;
			}else if(o1.getVisiCount() < o2.getVisiCount()) {
				return -1;
			}
			return 0;
		}
	});
	
	public PuHotDataCache_sort(IGetValByKey<K, T> source) throws NoCallbackInterException {
		super();
		if(source != null) {
			this.source = source;
			cacheMap = new ConcurrentHashMap<K, CacheEntity4<K,T>>();
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
		updatePerid = config.getUpdatePerid();
		taskStartHM = config.getTaskStartHM();
		taskEndHM = config.getTaskEndHM();
		saveTime = config.getCacheTime();
		percent = config.getPercent();
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
			CacheEntity4<K,T> entity = cacheMap.get(key);
			entity.setVisiCount(entity.getVisiCount() + 1);
			data = entity.getData();
		}else {
			if(cacheMap.size() < maxKeyNum) {
				data = source.getValByKey(key);
				CacheEntity4<K,T> one = new CacheEntity4<K,T>(data, currT, 1);
				one.setKey(key);
				cacheMap.put(key, one);
				sortmap.put(one, null);
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
		Iterator<Entry<K, CacheEntity4<K,T>>> ite = cacheMap.entrySet().iterator();
		int count = 0;
		Iterator<Entry<CacheEntity4<K,T>, String>> entryS = sortmap.entrySet().iterator();
		int rmCount = (int)(sortmap.size() * (percent / 100));
		int i = 0;
		//1.先进行排序量角度的的回收
		while(entryS.hasNext()) {
			if(++i <= rmCount) {
				Entry<CacheEntity4<K,T>, String> toRemo = entryS.next();
				entryS.remove();//本策略是这样规定
				cacheMap.remove(toRemo.getKey().getKey());
				continue;
			}
		}
		//2.进行其他策略的回收---比如回收量
//		while(ite.hasNext()) {
//			Entry<K, CacheEntity4<K,T>> entity = ite.next();
//			CacheEntity4<K,T> ca = entity.getValue();
//			float rate = ca.getVisiCount() / ((float)(currT - ca.getLastStatisTime()) /((float)statUnit));
////			float rate =  (ca.getVisiCount() / ((float)(currT - ca.getFirstTime()) / ((float)statUnit)));//10min种内需要有一个
////			System.out.println(rate);
//			if(rate < numPerStatUnit || currT - ca.getFirstTime() > saveTime) {//&& cacheMap.size() >= maxKeyNum   移除要求更严，所以缓存量只能稳步增增
//				ite.remove();
//				count++;
////				System.out.println("remove ok:");
//			}
//		}
		long t2 = System.currentTimeMillis();
		logger.info(String.format("clean cacheKey total:%s, take :%s ms", count, t2 - currT));
	}
	
	public void autoSeletedXiaoquCache() {
		
	}

	@Override
	public int putData(K key, T data) {
		long currT = System.currentTimeMillis();
		int ok = 0;
		if(cacheMap.containsKey(key)) {
			CacheEntity4<K,T> entity = cacheMap.get(key);
			entity.setVisiCount(entity.getVisiCount() + 1);
			ok = 1;
		}else {
			if(cacheMap.size() <= maxKeyNum) {
				CacheEntity4<K,T> one = new CacheEntity4<K,T>(data, currT, 1);
				cacheMap.put(key, one);
				logger.info(String.format("put key success: %s", key));
				ok = 1;
			}
		}
		return ok;
	}
}
