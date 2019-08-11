package com.bj58.fang.hugopenapi.client.enumn.house;

public enum Chaoxiang {
	xi(3), nan(2), dongxi(10), dong(1), xinan(7), dongbei(6), dongnan(5), bei(4), nanbei(9), xibei(8);
	private int value;

	private Chaoxiang(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
}