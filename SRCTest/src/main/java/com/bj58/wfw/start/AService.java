package com.bj58.wfw.start;

import com.alibaba.fastjson.JSONObject;
import com.bj58.fang.filter.plugin.core.contract.annotation.FPluginPluginService;
import com.bj58.fang.filter.plugin.store.annotation.FPluginAutoMemcached;
import com.bj58.fang.filter.plugin.store.annotation.FPluginAutoMemcached2;

@FPluginPluginService
public class AService {

	@FPluginAutoMemcached2(memXml="/hbg_miniapp.xml", cacheTime=24 * 60, preKey="aservice_test")
	public JSONObject justTest(String name, int id) {
		System.out.println("diaoyong  shiji jiekou");
		JSONObject ss = new JSONObject();
		ss.put("name", name);
		ss.put("id", id);
		return ss;
	}
	
	@FPluginAutoMemcached(cacheTime=24 * 60, preKey="aservice_test")
	public JSONObject justTest2(String name, int id) {
		System.out.println("diaoyong  shiji jiekou");
		JSONObject ss = new JSONObject();
		ss.put("name", name);
		ss.put("id", id);
		return ss;
	}
}
