package com.bj58.fang.hugopenapi.client.enumn.house;

public enum Loudongdanwei {
	zuo(3), nong(2), dong(1), hutong(6), haolou(5), hao(4);
	private int value;

	private Loudongdanwei(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}