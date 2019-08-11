package com.bj58.fang.hugopenapi.client.enumn.pub;

public enum BizType {
	ershoufang(1), zufang(2);
	private int value;

	private BizType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}