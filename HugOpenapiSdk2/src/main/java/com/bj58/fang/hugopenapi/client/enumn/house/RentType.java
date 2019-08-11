package com.bj58.fang.hugopenapi.client.enumn.house;

public enum RentType {
	hezu(2), zhengzu(1);
	private int value;

	private RentType(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}