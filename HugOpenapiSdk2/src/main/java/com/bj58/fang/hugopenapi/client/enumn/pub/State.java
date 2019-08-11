package com.bj58.fang.hugopenapi.client.enumn.pub;

public enum State {
	youxiao(0), wuxiao(1), chengjiao(2);
	private int value;

	private State(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}