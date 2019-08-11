package com.bj58.fang.hugopenapi.client.enumn.esf;

public enum Isyishoufang {
	fou(2), shi(1);
	private int value;

	private Isyishoufang(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}