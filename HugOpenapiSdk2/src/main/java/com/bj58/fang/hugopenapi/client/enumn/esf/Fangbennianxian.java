package com.bj58.fang.hugopenapi.client.enumn.esf;

public enum Fangbennianxian {
	bumanernian(3), maner(2), manwu(1);
	private int value;

	private Fangbennianxian(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}