package com.bj58.fang.hugopenapi.client.enumn;

public enum ValidateXiaoqu {

	YES(true, 1), NO(false, 2);
	
	private boolean need;
	private int val;
	private ValidateXiaoqu(boolean need, int val) {
		this.need = need;
		this.val = val;
	}
	public boolean isNeed() {
		return need;
	}
	public void setNeed(boolean need) {
		this.need = need;
	}
	public int getVal() {
		return val;
	}
	public void setVal(int val) {
		this.val = val;
	}

}
