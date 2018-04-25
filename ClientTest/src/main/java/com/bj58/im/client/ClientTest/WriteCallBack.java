package com.bj58.im.client.ClientTest;

public abstract class WriteCallBack {
	String line = null;
	Object lock = new Object();
	public abstract void setLine(String input);
	public String getLine() {
		String line_c = line;
		line = null;
		return line_c;
	}
	public Object getLock() {
		return lock;
	}
	
}

