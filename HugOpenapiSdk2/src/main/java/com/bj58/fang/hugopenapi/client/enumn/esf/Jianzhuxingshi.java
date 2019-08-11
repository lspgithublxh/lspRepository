package com.bj58.fang.hugopenapi.client.enumn.esf;

public enum Jianzhuxingshi {
	bantajiehe(3), talou(2), banlou(1);
	private int value;

	private Jianzhuxingshi(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}