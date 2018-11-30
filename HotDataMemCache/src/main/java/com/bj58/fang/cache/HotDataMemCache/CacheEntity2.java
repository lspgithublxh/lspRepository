package com.bj58.fang.cache.HotDataMemCache;

public class CacheEntity2<T> extends AbstractCacheEntity<T>{

	private long firstTime;
	private int visiCount;
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
	public CacheEntity2(T data, long firstTime, int visiCount) {
		super();
		this.data = data;
		this.firstTime = firstTime;
		this.visiCount = visiCount;
	}
	
	
}
