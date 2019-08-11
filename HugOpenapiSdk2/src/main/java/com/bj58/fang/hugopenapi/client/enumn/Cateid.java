package com.bj58.fang.hugopenapi.client.enumn;

public enum Cateid {
	ershoufang(13), zhengzu(11), hezu(12);
	private int value;

	private Cateid(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}