package com.bj58.fang.hugopenapi.client.enumn;

public enum AddOrUpdate {

	Add("add", 1), Update("update", 2);
	private String name;
	private Integer val;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getVal() {
		return val;
	}
	public void setVal(Integer val) {
		this.val = val;
	}
	private AddOrUpdate(String name, Integer val) {
		this.name = name;
		this.val = val;
	}
	
}
