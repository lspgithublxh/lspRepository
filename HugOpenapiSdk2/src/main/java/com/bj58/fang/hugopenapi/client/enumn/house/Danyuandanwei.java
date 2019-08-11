package com.bj58.fang.hugopenapi.client.enumn.house;

public enum Danyuandanwei {
	hao(3), chuang(2), dong(1), danyuan(5), haolou(4);
	private int value;

	private Danyuandanwei(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}