package com.bj58.fang.hugopenapi.client.enumn;

public enum Plats {

	anjuke(2), wuba(1);
	private int value;

	private Plats(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
