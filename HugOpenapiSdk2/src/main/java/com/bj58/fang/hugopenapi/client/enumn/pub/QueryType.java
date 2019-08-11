package com.bj58.fang.hugopenapi.client.enumn.pub;

public enum QueryType {
	by_mobilePhone(1), by_username(2);
	private int value;

	private QueryType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}