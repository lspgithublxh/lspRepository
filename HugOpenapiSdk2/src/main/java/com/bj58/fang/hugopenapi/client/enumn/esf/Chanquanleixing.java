package com.bj58.fang.hugopenapi.client.enumn.esf;

public enum Chanquanleixing {
	shangzhuliangyong(2), shangpinfang(1), jingjishiyongfang(3), shiyongquan(4), qita(6), gongfang(5);
	private int value;

	private Chanquanleixing(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}