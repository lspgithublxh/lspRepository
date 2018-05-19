package com.bj58.fang.timer_keyvalue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 新问题，如果想用来做多种类型对象的容器，如何保证多种类型且只有一个容器？  -----创建一个容器的容器，即HashMap的值也是一个HashMap
 * @ClassName:CacheContainer
 * @Description:
 * @Author lishaoping
 * @Date 2018年5月3日
 * @Version V1.0
 * @Package com.bj58.fang.timer_keyvalue
 */
public class CacheContainer {

	private CacheContainer() {}
	private static CacheContainer instance = new CacheContainer();
	
	/**
	 * 不担心并发问题
	 * @param 
	 * @author lishaoping
	 * @Date 2018年5月3日
	 * @Package com.bj58.fang.timer_keyvalue
	 * @return CacheContainer
	 */
	public static CacheContainer getInstance() {
		return instance;
	}
	
	private Map<Object, CacheEntity<Object>> map = new ConcurrentHashMap<Object, CacheEntity<Object>>();
	
	@SuppressWarnings("rawtypes")
	IGetValue iget = null;
	Long expiredTime = -1l;//默认永久不失效
	public <K, V> void setIGetValue(IGetValue<K, V> callback) {
		synchronized (this) {
			if(iget == null) {
				iget = callback;
			}
		}
	}
	
	public void setExpiredTime(long expiredTime) {
		synchronized (this) {
			if(this.expiredTime == -1l) {
				this.expiredTime = expiredTime;
			}
		}
	}
	
	/**
	 * 多线程问题,其实同时put或者get也都不影响功能本身(如果在设置的时候再取值，可能会影响)，只是重复。。但是同步可以避免重复
	 * @param 
	 * @author lishaoping
	 * @Date 2018年5月3日
	 * @Package com.bj58.fang.timer_keyvalue
	 * @return V
	 */
	public <K> Object get(K key) {//, IGetValue<K, V> callback移出 long expiredTime
		if(map.containsKey(key)) {
			long now = System.currentTimeMillis();
			if(now - map.get(key).getStartTime() > expiredTime) {//大多数时间内不必进入这个分支
				synchronized (this) {//可能有多个线程同时执行到这里，所以要在里面判断一下是否有线程先进入设置值了--来不再远程调用
					if(now - map.get(key).getStartTime() > expiredTime) {
						map.get(key).setV(iget.getValue(key));
						map.get(key).setStartTime(now);
					}
				}
			}
		}else {//大多数时间内不会进入这个分支
			synchronized (this) {
				if(!map.containsKey(key)) {
					CacheEntity<Object> entity = new CacheEntity<Object>(System.currentTimeMillis(), iget.getValue(key));
					map.put(key, entity);
				}
			}
		}
		Object v = map.get(key).getV();
		return v;
	}
}
