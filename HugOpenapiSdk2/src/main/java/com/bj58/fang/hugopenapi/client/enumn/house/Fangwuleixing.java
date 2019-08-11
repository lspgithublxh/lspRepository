package com.bj58.fang.hugopenapi.client.enumn.house;

public enum Fangwuleixing {
	bieshu(2), paiwu(10), putongzhuzhai(1), xinliyangfang(7), qita(6), pingfang(5), gongyu(4), siheyuan(9), laogongfang(
			8);
	private int value;

	private Fangwuleixing(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}