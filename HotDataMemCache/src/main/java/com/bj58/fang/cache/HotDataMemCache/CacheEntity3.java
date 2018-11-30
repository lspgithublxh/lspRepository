package com.bj58.fang.cache.HotDataMemCache;

public class CacheEntity3<T> extends AbstractCacheEntity<T>{

	private long firstTime;
	private int visiCount;
	private int rateOkCount;
	
	public int getRateOkCount() {
		return rateOkCount;
	}
	public void setRateOkCount(int rateOkCount) {
		this.rateOkCount = rateOkCount;
	}
	public long getFirstTime() {
		return firstTime;
	}
	public void setFirstTime(long firstTime) {
		this.firstTime = firstTime;
	}
	public int getVisiCount() {
		return visiCount;
	}
	public void setVisiCount(int visiCount) {
		this.visiCount = visiCount;
	}
	public CacheEntity3(T data, long firstTime, int visiCount, int rateOkCount) {
		super();
		this.data = data;
		this.firstTime = firstTime;
		this.visiCount = visiCount;
		this.rateOkCount = rateOkCount;
	}
	
	
}
