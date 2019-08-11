package com.bj58.fang.hugopenapi.client.enumn.house;

public enum IndependFacilities {
	yangtai(13), kezuofan(11), weishengjian(12), xiyiji(3), dianshi(2), bingxiang(1), yiju(10), shafa(7), kuandai(
			6), kongdiao(5), reshuiqi(4), nuanqi(9), chuang(8);
	private int value;

	private IndependFacilities(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}