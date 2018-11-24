package com.bj58.fang.cache;

public class CacheConfig {

	private long taskPerid = 1000 * 60;//1min
	private int maxKeyNum = 5000;
	
	private int numPerStatUnit = 1;//1/10min
	private long statUnit = 1000 * 60 * 10;//10min
	private long updateDelay = 0;
	private long updatePerid = 24 * 60 * 60 * 1000;
	
	public final long ONEMIN = 1000 * 60;
	
	public CacheConfig() {
		super();
	}
	
	public CacheConfig(long taskPerid, int maxKeyNum, int numPerStatUnit, long statUnit, long updateDelay,
			long updatePerid) {
		super();
		this.taskPerid = taskPerid;
		this.maxKeyNum = maxKeyNum;
		this.numPerStatUnit = numPerStatUnit;
		this.statUnit = statUnit;
		this.updateDelay = updateDelay;
		this.updatePerid = updatePerid;
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
	public long getUpdateDelay() {
		return updateDelay;
	}
	public void setUpdateDelay(long updateDelay) {
		this.updateDelay = updateDelay;
	}
	public long getUpdatePerid() {
		return updatePerid;
	}
	public void setUpdatePerid(long updatePerid) {
		this.updatePerid = updatePerid;
	}
}
