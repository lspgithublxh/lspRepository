package com.li.shao.ping.KeyListBase.datastructure.util.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WarningInfo {

	private String fulGCTimes;
	private String heapUsage;
	private String threadLock;
	private String threadCount;
	private String hashMapNodeCount;
	private String cpuUsage;
	private String memUsage;
	private String diskUsage;

	private String netIoUsage;


}
