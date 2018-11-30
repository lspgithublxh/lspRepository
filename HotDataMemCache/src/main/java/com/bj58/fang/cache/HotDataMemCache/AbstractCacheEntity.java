package com.bj58.fang.cache.HotDataMemCache;

public class AbstractCacheEntity<T> {

	protected T data;
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public AbstractCacheEntity(T data) {
		super();
		this.data = data;
	}
	public AbstractCacheEntity() {
	}
}
