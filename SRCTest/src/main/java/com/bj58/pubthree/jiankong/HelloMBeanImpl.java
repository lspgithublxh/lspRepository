package com.bj58.pubthree.jiankong;

public class HelloMBeanImpl implements HelloMXBean {

	private String name;
	@Override
	public String getName() {
		return name + "  this is name";
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

}
