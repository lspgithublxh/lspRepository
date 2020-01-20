package com.li.shao.ping.KeyListBase.datastructure.util.httpserver.monitor;

import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;

import com.li.shao.ping.KeyListBase.datastructure.util.model.WarningInfo;
import com.li.shao.ping.KeyListBase.datastructure.util.monitor.MemoryVisitUtil.AllMonitorEntity;
import com.li.shao.ping.KeyListBase.datastructure.util.monitor.MemoryVisitUtil.BaseInfoEntity;
import com.li.shao.ping.KeyListBase.datastructure.util.monitor.MemoryVisitUtil.Entity;
import com.li.shao.ping.KeyListBase.datastructure.util.monitor.MemoryVisitUtil.ThreadEntity;

public class StaticsDatabase {
	
	public static StaticsDatabase instance = new StaticsDatabase();
	private Double cpuUsageLimit = 0.6;
	private Double diskUsageLimit = 0.4;
	private Double memUsageLimit = 0.6;
	private Double netInLimit = 1024 * 1024D;
	private Double fgcLimit = 10D;
	private Double heapUsageLimit = 0.6;
	private Integer threadCountLimit = 500;
	private long hashMapNodeRefCountLimit;

	public WarningInfo getWarningInfo(AllMonitorEntity allInfo) {
		WarningInfo winfo = new WarningInfo();
		try {
			BaseInfoEntity base = allInfo.getBase();
			Double cu = Double.valueOf(base.getCpuUseTotalPers());
			if(cu > cpuUsageLimit ) {
				winfo.setCpuUsage("[warning] cpu usage overUse: " + cu);
			}
			Double disk = Double.valueOf(base.getDiskUsage());
			if(disk > diskUsageLimit ) {
				winfo.setDiskUsage("[warning] disk usage overUse: " + disk);
			}
			String[] mUsage = base.getMemUseTotalPers().split(",");
			Double heapU = Double.valueOf(mUsage[0]);
			Double noHeapU = Double.valueOf(mUsage[1]);
			if(noHeapU > memUsageLimit) {
				winfo.setMemUsage("[warning] noheap usage overUse: " + noHeapU);
			}
			if(heapU > heapUsageLimit ) {
				winfo.setHeapUsage("[warning] heap usage overUse: " + heapU);
			}
			Double netIn = base.getNetIoInfo().stream().map(item -> {
				String line = item.getNetworkIn().trim();
				return Double.valueOf(line.substring(0, line.indexOf(" ")));
			}).reduce(0D, (item1, item2) -> item1 + item2);
			if(netIn > netInLimit ) {
				winfo.setNetIoUsage("[warning] netIn usage overUse: " + netIn);
			}
			String threadTotalPers = base.getThreadTotalPers();
			Double threadCount = Double.valueOf(threadTotalPers.substring(0, threadTotalPers.indexOf("/")));
			if(threadCount > threadCountLimit  ) {
				winfo.setThreadCount("[warning] threadCount usage overUse: " + threadCount);
			}
			Double fgc = allInfo.getJstatMap().get("FGC");
			if(fgc > fgcLimit  ) {
				winfo.setFulGCTimes("[warning] fgc usage overUse: " + fgc);
			}
			//查询有没有blocked的线程，有比较危险::持有而等待锁；该行有waiting for monitor entry
			ThreadEntity threadEntity = allInfo.getJstackMap().get(-1);
			if(threadEntity != null) {
				winfo.setThreadLock("[warning thread deadlock] " + threadEntity.getStack());
			}else {
				allInfo.getJstackMap().forEach((k,v) ->{
					if("BLOCKED".equals(v.getStack())) {
						winfo.setThreadLock("[warning thread deadlock] " + v.getStack());
					}
				});
			}
			TreeMap<Integer, Entity> jmapData = allInfo.getJmapData();
			AtomicLong totalCount = new AtomicLong(0);
			jmapData.forEach((key, val) -> {
				if(val.getName().contains("HashMap")) {
					totalCount.addAndGet(val.getCount());
				}
			});
			if(totalCount.get() > hashMapNodeRefCountLimit) {
				winfo.setHashMapNodeCount("[warning] hashMap usage overUse: " + totalCount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return winfo;
	}
}
