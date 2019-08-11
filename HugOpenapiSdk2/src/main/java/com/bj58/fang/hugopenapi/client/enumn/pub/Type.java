package com.bj58.fang.hugopenapi.client.enumn.pub;

public enum Type {
	youxiao(0), shanchu(1), all(2);
	private int value;

	private Type(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}