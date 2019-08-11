package com.bj58.fang.hugopenapi.client.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bj58.fang.hugopenapi.client.Entity.BrokerZFEntity;
import com.bj58.fang.hugopenapi.client.Entity.CompanyZFEntity;
import com.bj58.fang.hugopenapi.client.Entity.ZFEntity;
import com.bj58.fang.hugopenapi.client.Entity.result.CommonResult;
import com.bj58.fang.hugopenapi.client.Entity.result.Result;
import com.bj58.fang.hugopenapi.client.consta.UrlConst;
import com.bj58.fang.hugopenapi.client.enumn.AddOrUpdate;
import com.bj58.fang.hugopenapi.client.enumn.ComanyOrBroker;
import com.bj58.fang.hugopenapi.client.exception.ESFException;
import com.bj58.fang.hugopenapi.client.exception.ZFException;
import com.bj58.fang.hugopenapi.client.provider.TokenProvider;
import com.bj58.fang.hugopenapi.client.util.DefaultHttpPoolingManager;
import com.bj58.fang.hugopenapi.client.util.JSONUtils;
import com.bj58.fang.hugopenapi.client.util.PostUtils;
import com.bj58.fang.hugopenapi.client.util.StringUtils;

public class ZFService {

    private static final Logger logger = LoggerFactory.getLogger(ZFService.class);

    private static ZFService instance = new ZFService();
    
    public static ZFService getInstance() {
    	return instance;
    }
    
    public CommonResult addUpdateBrokerZF(ZFEntity entity, AddOrUpdate type) throws ESFException {
    	String rs = null;
		try {
			rs = addUpdateZF(entity, type);
		} catch (ZFException e) {
			e.printStackTrace();
			logger.error("addUpdateBrokerESF failed!");
		}
		CommonResult result = null;
    	if(!StringUtils.isEmptyString(rs)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(rs);
    	}
		return result;
    }
    
    public CommonResult addUpdateCompanyZF(ZFEntity entity, AddOrUpdate type) throws ESFException {
    	String rs = null;
		try {
			rs = addUpdateZF(entity, type);
		} catch (ZFException e) {
			e.printStackTrace();
			logger.error("addUpdateCompanyESF failed!");
		}
    	CommonResult result = null;
    	if(!StringUtils.isEmptyString(rs)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(rs);
    	}
		return result;
    }
    
	public String addUpdateZF(ZFEntity entity, AddOrUpdate type) throws ZFException {
		String entityType = entity.getClass().getName();
		String uri = null;
		ComanyOrBroker corb = null;
		if(BrokerZFEntity.class.getName().equals(entityType)) {
			corb = ComanyOrBroker.Broker;
			uri = type.equals(AddOrUpdate.Add) ? UrlConst.BRO_ZF_ADD : UrlConst.BRO_ZF_UPDATE;
		}else if(CompanyZFEntity.class.getName().equals(entityType)) {
			corb = ComanyOrBroker.Comany;
			uri = type.equals(AddOrUpdate.Add) ? UrlConst.COM_ZF_ADD : UrlConst.COM_ZF_UPDATE;
		}
		//2.封装
		List<String> removeKeys = null;
		if(AddOrUpdate.Update.equals(type)) {
			if(ComanyOrBroker.Broker.equals(corb)) {
				removeKeys = Arrays.asList(new String[] {"title","dizhi","xiaoqu","quyu","xiaoquId"});
			}else if(ComanyOrBroker.Comany.equals(corb)) {
				//公司二手房的更新 和发布一样的接口
			}
		}
		String rs = "";
		try {
			Map<String, String> data = PostUtils.getInstance().entityToMapByAnno(entity, removeKeys, type);
			Map<String, String> headers = new HashMap<String, String>();
			//2.接口调用时必须使用实时的当前时间--且tokenSign和timeSign中的一样
			long time = System.currentTimeMillis();
			 rs = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(String.format(UrlConst.HOUSE_HANDLE_URL, uri, InitService.getInstance().getClientId(),
					 time, TokenProvider.getInstance().getTokenSign(time)), data, headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("add zf house failed! clientId:" + InitService.getInstance().getClientId());
		}
		return rs;
		//3.发送返回封装
	}
	
	public Result deleteZF(long brokerid, long houseid, int rentType, ComanyOrBroker corb) throws ESFException {
		String uri = null;
		if(ComanyOrBroker.Broker.equals(corb)) {
			uri =  UrlConst.BRO_ZF_DELETE;
		}
		String rs = "";
		Result result = null;
		try {
			 Map<String, String> data = new HashMap<String, String>();
			 data.put("brokerid", brokerid + "");
			 data.put("houseid", houseid + "");
			 data.put("rentType", rentType + "");
			 Map<String, String> headers = new HashMap<String, String>();
			 long time = System.currentTimeMillis();
			 rs = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(
					 String.format(UrlConst.HOUSE_HANDLE_URL, uri,
							 InitService.getInstance().getClientId(),
							 time, TokenProvider.getInstance().getTokenSign(time)), data, headers);
			 if(!StringUtils.isEmptyString(rs)) {
				 result = JSONUtils.getInstance().jsonResultToJAVAResult(rs);
			 }
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("delete zf house failed! clientId:" + InitService.getInstance().getClientId());
		}
		return result;
	}
}
