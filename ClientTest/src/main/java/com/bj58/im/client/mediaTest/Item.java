package com.bj58.im.client.mediaTest;

public class Item {

	private int cishu;
	private int jieshu;
	private int xishu;
	private int val;
	public int getCishu() {
		return cishu;
	}
	public void setCishu(int cishu) {
		this.cishu = cishu;
	}
	public int getJieshu() {
		return jieshu;
	}
	public void setJieshu(int jieshu) {
		this.jieshu = jieshu;
	}
	public int getXishu() {
		return xishu;
	}
	public void setXishu(int xishu) {
		this.xishu = xishu;
	}
	public int getVal() {
		return val;
	}
	public void setVal(int val) {
		this.val = val;
	}
	public Item(int cishu, int jieshu, int xishu, int val) {
		super();
		this.cishu = cishu;
		this.jieshu = jieshu;
		this.xishu = xishu;
		this.val = val;
	}
	
	
}
