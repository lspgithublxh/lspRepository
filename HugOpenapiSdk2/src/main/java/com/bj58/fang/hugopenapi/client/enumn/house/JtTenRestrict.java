package com.bj58.fang.hugopenapi.client.enumn.house;

public enum JtTenRestrict {
	xiannvsheng(3), xiannansheng(2), nannvbuxian(1), xianfuqi(4);
	private int value;

	private JtTenRestrict(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}