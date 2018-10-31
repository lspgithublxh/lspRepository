package com.bj58.wfw.start;

import java.io.IOException;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.bj58.fang.filter.plugin.core.FPluginServiceMethodFactory;
import com.bj58.fang.filter.plugin.proxy.FPluginServiceProxy;
import com.bj58.spat.memcached.MemcachedClient;
import com.bj58.spat.memcached.exception.ConfigException;

public class PluginTest {

	@Test
	public void testplugin() {
		try {
			MemcachedClient memcache = MemcachedClient.getInstrance("D:\\opt\\wf\\hbg_api_miniapp\\hbg_miniapp.xml");//D:\\opt\\wf\\srctest\\hbg_miniapp.xml
			System.out.println(memcache);
		} catch (ConfigException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		FPluginServiceMethodFactory.init();
		AService s = FPluginServiceProxy.getProxy(AService.class);
		JSONObject ss = s.justTest("朝阳", 123);
		System.out.println(ss);
		
		ss = s.justTest("朝阳", 123);
		System.out.println(ss);
		
		ss = s.justTest("朝阳", 123);
		System.out.println(ss);
	}
}
