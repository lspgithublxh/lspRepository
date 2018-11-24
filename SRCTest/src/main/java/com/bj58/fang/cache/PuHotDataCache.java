package com.bj58.fang.cache;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 另一种统计方法：按访问量进行/上次访问时间
 * @ClassName:HotDataCache
 * @Description:
 * @Author lishaoping
 * @Date 2018年11月24日
 * @Version V1.0
 * @Package com.bj58.fang.cache
 */
public class PuHotDataCache {

	Map<String, CacheEntity> cacheMap = new ConcurrentHashMap<String, CacheEntity>();
	private static final long maxEdle = 1000 * 60 * 60;//1h
	private static final long nonActive = 1000 * 60 * 6;//6min
	private static final long taskPerid = 1000 * 60;//1min
	private static final int iniScore = 10;//
	private static final int nAScore = 3;
	private static final int maxKeyNum = 5000;
	
	public static void main(String[] args) {
		
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
			CacheEntity entity = cacheMap.get(key);
			//1处理 ，太久没有访问量-则初始分3 ;  
			if(currT - entity.getLastMod() > maxEdle) {
				entity.setHotPot(nAScore);
			}else {
				//2.处理 ， 距离上次时间超过活跃时间--减1分，没超过 则 恢复初始分10分
				int hotpot = entity.getHotPot();
				if(currT - entity.getLastMod() > nonActive) {
					entity.setHotPot(hotpot - 1);
				}else {
					entity.setHotPot(iniScore);
				}
			}
			entity.setLastMod(currT);
		}else {
			T data = source.getValByKey(key);
			if(cacheMap.size() > maxKeyNum) {
				//3.直接返回数据
			}else {
				//4.直接存放
				CacheEntity<T> one = new CacheEntity<T>(data, currT, iniScore);
				cacheMap.put(key, one);
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
	private void scheduledTaskByVisitTime() {
		ScheduledExecutorService executor = null;
		try {
			executor = Executors.newScheduledThreadPool(1);//只启动一个线程
			executor.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					long curr = System.currentTimeMillis();
					Iterator<Entry<String, CacheEntity>> ite = cacheMap.entrySet().iterator();
					while(ite.hasNext()) {
						Entry<String, CacheEntity> entity = ite.next();
						CacheEntity ca = entity.getValue();
						long interval = curr - ca.getLastMod();
						if(interval > maxEdle) {
							//移除出map
							cacheMap.remove(entity.getKey());
							ca = null;
						}else if(interval > nonActive){//重置分数
							//不活跃，减1分
							int score = ca.getHotPot() - (int)(interval/nonActive);
							if(score == 0) {
								cacheMap.remove(entity.getKey());
								ca = null;
							}else {
								ca.setHotPot(score);
							}
							
						}
					}
				}
			}, 1, taskPerid, TimeUnit.SECONDS);
		}catch (Exception e) {
			if(executor != null) {
				executor.shutdownNow();
			}
		}
	} 
	
	public void autoSeletedXiaoquCache() {
		
	}
}
