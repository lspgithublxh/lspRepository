package com.bj58.fang.hugopenapi.client.enumn.pub;

public enum UserIdType {
	wangluomendianUser(1), jingjigongsiUser(3);
	private int value;

	private UserIdType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}