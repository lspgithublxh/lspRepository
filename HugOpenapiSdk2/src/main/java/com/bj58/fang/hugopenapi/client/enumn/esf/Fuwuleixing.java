package com.bj58.fang.hugopenapi.client.enumn.esf;

public enum Fuwuleixing {
	mianfeibaojie(1), youyaochi(2);
	private int value;

	private Fuwuleixing(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}