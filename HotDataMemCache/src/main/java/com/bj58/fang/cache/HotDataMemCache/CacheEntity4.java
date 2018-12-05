package com.bj58.fang.cache.HotDataMemCache;

public class CacheEntity4<K, T> extends AbstractCacheEntity<T>{

	private long firstTime;
	private int visiCount;
	private long lastStatisTime;
	private K key;
	
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
	
	public K getKey() {
		return key;
	}
	public void setKey(K key) {
		this.key = key;
	}
	public CacheEntity4(T data, long firstTime, int visiCount) {
		super();
		this.data = data;
		this.firstTime = firstTime;
		this.visiCount = visiCount;
	}
	public long getLastStatisTime() {
		return lastStatisTime;
	}
	public void setLastStatisTime(long lastStatisTime) {
		this.lastStatisTime = lastStatisTime;
	}
	
}
