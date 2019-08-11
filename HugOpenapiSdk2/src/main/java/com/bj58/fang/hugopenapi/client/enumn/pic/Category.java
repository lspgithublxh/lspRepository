package com.bj58.fang.hugopenapi.client.enumn.pic;

public enum Category {
	shineitu(1), shiwaitu(2), huxingtu(3);
	private int value;

	private Category(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	
	public static String getName(int type) {
		if(type == 1) {
			return shineitu.name();
		}else if(type == 2) {
			return huxingtu.name();
		}else if(type == 3) {
			return shiwaitu.name();
		}
		return "notknown";
	}
}
