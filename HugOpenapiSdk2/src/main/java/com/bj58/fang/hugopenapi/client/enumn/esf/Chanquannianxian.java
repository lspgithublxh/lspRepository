package com.bj58.fang.hugopenapi.client.enumn.esf;

public enum Chanquannianxian {
	seventy(1), fivty(2), fourty(3);
	private int value;

	private Chanquannianxian(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}