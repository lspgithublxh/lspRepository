package com.bj58.fang.hugopenapi.client.enumn.house;

public enum Dianti {
	shi(1), fou(0);
	private int value;

	private Dianti(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}