package com.bj58.fang.cache;

public class CacheEntity<T> {

	private T data;
	private long lastMod;
	private int hotPot;
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public long getLastMod() {
		return lastMod;
	}
	public void setLastMod(long lastMod) {
		this.lastMod = lastMod;
	}
	public int getHotPot() {
		return hotPot;
	}
	public void setHotPot(int hotPot) {
		this.hotPot = hotPot;
	}
	public CacheEntity(T data, long lastMod, int hotPot) {
		super();
		this.data = data;
		this.lastMod = lastMod;
		this.hotPot = hotPot;
	}
	
}
