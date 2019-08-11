package com.bj58.fang.hugopenapi.client.enumn.pub;

public enum DeptLevel {
	daqu(2),
	weizhi(0),
	gongsi(1),
	shangquan(3),
	zu(5),
	mendian(4);
	 private int value;
	 private DeptLevel(int value){
	this.value = value;
	} 
	 public int getValue() {
	 return value;}
}