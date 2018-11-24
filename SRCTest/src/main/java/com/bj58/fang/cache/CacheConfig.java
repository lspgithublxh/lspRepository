package com.bj58.fang.cache;

public class CacheConfig {

	private long taskPerid = 1000 * 60;//1min
	private int maxKeyNum = 5000;
	
	private int numPerStatUnit = 1;//1/10min
	private long statUnit = 1000 * 60 * 10;//10min
	
	public final long ONEMIN = 1000 * 60;
	
	public CacheConfig() {
		super();
	}
	public CacheConfig(long taskPerid, int maxKeyNum, int numPerStatUnit, long statUnit) {
		super();
		this.taskPerid = taskPerid;
		this.maxKeyNum = maxKeyNum;
		this.numPerStatUnit = numPerStatUnit;
		this.statUnit = statUnit;
	}
	public long getTaskPerid() {
		return taskPerid;
	}
	public void setTaskPerid(long taskPerid) {
		this.taskPerid = taskPerid;
	}
	public int getMaxKeyNum() {
		return maxKeyNum;
	}
	public void setMaxKeyNum(int maxKeyNum) {
		this.maxKeyNum = maxKeyNum;
	}
	public int getNumPerStatUnit() {
		return numPerStatUnit;
	}
	public void setNumPerStatUnit(int numPerStatUnit) {
		this.numPerStatUnit = numPerStatUnit;
	}
	public long getStatUnit() {
		return statUnit;
	}
	public void setStatUnit(long statUnit) {
		this.statUnit = statUnit;
	}
	
}
