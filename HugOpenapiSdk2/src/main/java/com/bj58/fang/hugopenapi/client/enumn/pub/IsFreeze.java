package com.bj58.fang.hugopenapi.client.enumn.pub;

public enum IsFreeze {
	jiedong(0), dongjie(1);
	private int value;

	private IsFreeze(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}