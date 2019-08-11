package com.bj58.fang.hugopenapi.client.enumn.rentdetail;

public enum RenterAge {
	age_0_20("201"), age_20_29("202"), age_30_39("201"), age_40_49("201"), age_50old("201");
	private String value;

	private RenterAge(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}