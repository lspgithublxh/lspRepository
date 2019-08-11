package com.bj58.fang.hugopenapi.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bj58.fang.hugopenapi.client.consta.UrlConst;
import com.bj58.fang.hugopenapi.client.util.DefaultHttpPoolingManager;

public class DispLocalService {

    private static final Logger logger = LoggerFactory.getLogger(DispLocalService.class);

    /**
     * 获取城市id列表
     * @param 
     * @author lishaoping
     * @Package com.bj58.fang.hugopenapi.client.annoService
     * @return String
     */
	public String getCityIdList() {
		String res = null;
		try {
			res = DefaultHttpPoolingManager.getInstance().get(String.format(UrlConst.HOUSE_PREX_URL, UrlConst.CITY_ID_LIST));
		} catch (Exception e) {
			logger.error("cityidList get failed!");
			e.printStackTrace();
		}
		return res;
	}
}
