package com.bj58.fang.hugopenapi.client.enumn.house;

public enum Zhuangxiuqingkuang {
	jiandanzhuangxiu(2), maopi(1), gaoduanzhuangxiu(6), jingzhuangxiu(4);
	private int value;

	private Zhuangxiuqingkuang(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}