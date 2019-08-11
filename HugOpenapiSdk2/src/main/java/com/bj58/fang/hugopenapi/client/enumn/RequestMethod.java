package com.bj58.fang.hugopenapi.client.enumn;

public enum RequestMethod {

	POST("post"), GET("get");
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private RequestMethod(String name) {
		this.name = name;
	}
	
}
