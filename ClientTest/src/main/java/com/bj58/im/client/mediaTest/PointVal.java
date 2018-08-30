package com.bj58.im.client.mediaTest;

public class PointVal {

	private double x;
	private double y;
	private double z;
	private double step;
	
	
	public double getStep() {
		return step;
	}
	public void setStep(double step) {
		this.step = step;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
	public PointVal(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
}
