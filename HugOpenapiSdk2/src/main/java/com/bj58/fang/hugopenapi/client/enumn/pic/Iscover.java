package com.bj58.fang.hugopenapi.client.enumn.pic;

public enum Iscover {
	shi(1), fou(0);
	private int value;

	private Iscover(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}
