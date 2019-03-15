package com.bj58.fang.Dis_MRTaskTest.entity;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class OneTaskEntity {

	private ConcurrentHashMap<String, List<Object>> kvMap;
	private Set<String> hasFinishedIPMap;
	public ConcurrentHashMap<String, List<Object>> getKvMap() {
		return kvMap;
	}
	public void setKvMap(ConcurrentHashMap<String, List<Object>> kvMap) {
		this.kvMap = kvMap;
	}
	public Set<String> getHasFinishedIPMap() {
		return hasFinishedIPMap;
	}
	public void setHasFinishedIPMap(Set<String> hasFinishedIPMap) {
		this.hasFinishedIPMap = hasFinishedIPMap;
	}
	
}
