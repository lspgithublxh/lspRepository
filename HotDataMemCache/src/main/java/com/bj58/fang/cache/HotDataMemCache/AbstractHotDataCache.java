package com.bj58.fang.cache.HotDataMemCache;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * 另一种更新策略是：根据每条数据的缓存时间----超过某个时间则更新
 * @ClassName:AbstractHotDataCache
 * @Description:
 * @Author lishaoping
 * @Date 2018年11月26日
 * @Version V1.0
 * @Package com.bj58.fang.cache.HotDataMemCache
 */
public abstract class AbstractHotDataCache<K, T, E extends AbstractCacheEntity<T>> {

	private static final Logger logger = Logger.getLogger(AbstractHotDataCache.class);
	protected Map<K, E> cacheMap = null;
	protected long taskPerid = 1000 * 60;//1min
	protected int taskStartHM = 730;
	protected int taskEndHM = 2359;//59-00 可以写2400
	protected long updateDelay = 0;
	protected long updatePerid = 24 * 60 * 60 * 1000;
	private String cacheName = "default";

	public abstract void cleanCache();
	public abstract T getData(K key);
	public abstract int putData(K key, T data);
	public abstract void configAndStartClean(CacheConfig config);
	
	public void scheduledTaskByVisitTime(final IGetValByKey<K, T> source) {
		ScheduledExecutorService executor = null;
		try {
			executor = Executors.newScheduledThreadPool(2);//只启动2个线程
			executor.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					try {
						Calendar dar = Calendar.getInstance();
						int curhour = dar.get(Calendar.HOUR_OF_DAY);
						int curmin = dar.get(Calendar.MINUTE);
						int curhm = curhour * 100 + curmin;
						if(curhm >= taskStartHM && curhm < taskEndHM) {
							cleanCache();
						}
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
					Iterator<Entry<K, E>> ite = cacheMap.entrySet().iterator();
					while(ite.hasNext()) {
						Entry<K, E> entity = ite.next();
						AbstractCacheEntity<T> ca = entity.getValue();
						T data = source.getValByKey(entity.getKey());
						ca.setData(data);
					}
					long t2 = System.currentTimeMillis();
					logger.info(String.format("update cache %s ok, total:%s, take %s ms", cacheName, cacheMap.size(), t2 - t1));
				}
			}, updateDelay, updatePerid, TimeUnit.MILLISECONDS);
		}catch (Exception e) {
			if(executor != null) {
				executor.shutdownNow();
			}
			logger.error(String.format("cache %s: task run error!%s", cacheName, e.getMessage()));
		}
	} 
}
