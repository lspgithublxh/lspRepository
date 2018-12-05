package com.bj58.fang.cache;

public class CacheEntity2<T> {

	private T data;
	private long firstTime;
	private int visiCount;
	private String key;
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
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
	public CacheEntity2(T data, long firstTime, int visiCount) {
		super();
		this.data = data;
		this.firstTime = firstTime;
		this.visiCount = visiCount;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
}
