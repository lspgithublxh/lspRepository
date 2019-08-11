package com.bj58.fang.hugopenapi.client.service;

import java.util.HashMap;
import java.util.Map;

import com.bj58.fang.hugopenapi.client.consta.UrlConst;
import com.bj58.fang.hugopenapi.client.provider.TokenProvider;
import com.bj58.fang.hugopenapi.client.util.DefaultHttpPoolingManager;

public class XQService {

	private static XQService instance = new XQService();
    
    public static XQService getInstance() {
    	return instance;
    }
	/**
	 * 获取模糊小区匹配到的磐石标准小区;name:匹配的名称， size匹配数量
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月14日
	 * @Package com.bj58.fang.hugopenapi.client.annoService
	 * @return String
	 */
	public String matchXiaoqu(String name, Integer size) {
		try {
			if(size == null || size == 0) {
				size = 10;//默认设置
			}
			Map<String, String> data = new HashMap<String, String>();
			data.put("name", name);
			data.put("size", size + "");
			long time = System.currentTimeMillis();
			String res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(
					String.format(UrlConst.HOUSE_HANDLE_URL, UrlConst.XQ_PANSHI_MATCH,InitService.getInstance().getClientId(),
							 time, TokenProvider.getInstance().getTokenSign(time)), data, null);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
