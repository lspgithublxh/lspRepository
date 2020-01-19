package com.li.shao.ping.KeyListBase.datastructure.util.httpserver.monitor;

import com.li.shao.ping.KeyListBase.datastructure.util.model.WarningInfo;
import com.li.shao.ping.KeyListBase.datastructure.util.monitor.MemoryVisitUtil.AllMonitorEntity;
import com.li.shao.ping.KeyListBase.datastructure.util.monitor.MemoryVisitUtil.BaseInfoEntity;
import com.li.shao.ping.KeyListBase.datastructure.util.monitor.MemoryVisitUtil.NetIOinfoEntity;

public class StaticsDatabase {
	
	public static StaticsDatabase instance = new StaticsDatabase();
	private Double cpuUsageLimit = 0.6;
	private Double diskUsageLimit = 0.4;
	private Double memUsageLimit = 0.6;
	private Double netInLimit = 1024 * 1024D;
	private Double fgcLimit = 10D;

	public void getWarningInfo(AllMonitorEntity allInfo) {
		WarningInfo winfo = new WarningInfo();
		BaseInfoEntity base = allInfo.getBase();
		Double cu = Double.valueOf(base.getCpuUseTotalPers());
		if(cu > cpuUsageLimit ) {
			winfo.setCpuUsage("[warning] cpu usage overUse: " + cu);
		}
		Double disk = Double.valueOf(base.getDiskUsage());
		if(disk > diskUsageLimit ) {
			winfo.setCpuUsage("[warning] disk usage overUse: " + disk);
		}
		Double memU = Double.valueOf(base.getMemUseTotalPers());
		if(memU > memUsageLimit) {
			winfo.setCpuUsage("[warning] mem usage overUse: " + memU);
		}
		Double netIn = base.getNetIoInfo().stream().map(item -> {
			String line = item.getNetworkIn().trim();
			return Double.valueOf(line.substring(0, line.indexOf(" ")));
		}).reduce(0D, (item1, item2) -> item1 + item2);
		if(netIn > netInLimit ) {
			winfo.setCpuUsage("[warning] netIn usage overUse: " + netIn);
		}
		Double fgc = allInfo.getJstatMap().get("FGC");
		if(fgc > fgcLimit  ) {
			winfo.setCpuUsage("[warning] fgc usage overUse: " + fgc);
		}
		
	}
}
