package com.bj58.im.client.mediaTest;

public class Item {

	private int cishu;
	private int jieshu;
	private double xishu;
	private double val;
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
	
	public double getXishu() {
		return xishu;
	}
	public void setXishu(double xishu) {
		this.xishu = xishu;
	}
	public double getVal() {
		return val;
	}
	public void setVal(double val) {
		this.val = val;
	}
	public Item(int cishu, int jieshu, double xishu, double val) {
		super();
		this.cishu = cishu;
		this.jieshu = jieshu;
		this.xishu = xishu;
		this.val = val;
	}
	
	
	
}
