package com.bj58.fang.timer_keyvalue;

public class CacheEntity<V> {

	private long startTime;
	private V v;
	public CacheEntity(long startTime, V v) {
		super();
		this.startTime = startTime;
		this.v = v;
	}
	public long getStartTime() {
		return startTime;
	}

	public V getV() {
		return v;
	}
	public void setV(V v) {
		this.v = v;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	
	
	
}
