package com.bj58.fang.hugopenapi.client.enumn.rentdetail;

public enum RenterType {
	qinglv("103"), nansheng("101"), nvsheng("102");
	private String value;

	private RenterType(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}