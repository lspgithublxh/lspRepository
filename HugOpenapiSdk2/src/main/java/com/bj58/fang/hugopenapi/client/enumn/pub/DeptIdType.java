package com.bj58.fang.hugopenapi.client.enumn.pub;

public enum DeptIdType {
	wangluomendian(1), jingjigongsibumenid(3);
	private int value;

	private DeptIdType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}