package com.bj58.fang.hugopenapi.client.enumn.house;

public enum Heating {
	bugongnuan(3), zigongnuan(2), jitigongnuan(1);
	private int value;

	private Heating(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}