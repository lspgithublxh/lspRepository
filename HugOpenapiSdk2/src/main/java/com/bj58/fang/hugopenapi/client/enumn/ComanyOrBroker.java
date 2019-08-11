package com.bj58.fang.hugopenapi.client.enumn;

public enum ComanyOrBroker {

	Comany("comany"), Broker("broker");

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private ComanyOrBroker(String name) {
		this.name = name;
	}
}
