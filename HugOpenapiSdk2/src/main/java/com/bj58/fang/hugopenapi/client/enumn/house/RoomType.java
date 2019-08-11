package com.bj58.fang.hugopenapi.client.enumn.house;

public enum RoomType {
	zhuwo(2), ciwo(1);
	private int value;

	private RoomType(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}