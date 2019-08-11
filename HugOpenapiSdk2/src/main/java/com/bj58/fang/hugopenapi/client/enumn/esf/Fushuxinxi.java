package com.bj58.fang.hugopenapi.client.enumn.esf;

public enum Fushuxinxi {
	daihuayuan(3), daigelou(2), zidaiquanshuchewei(1), piaochuang(7), dixiashi(6), lutai(5), tianjing(4);
	private int value;

	private Fushuxinxi(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}