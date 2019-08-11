package com.bj58.fang.hugopenapi.client.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bj58.fang.hugopenapi.client.Entity.BrokerESFEntity;
import com.bj58.fang.hugopenapi.client.Entity.CompanyESFEntity;
import com.bj58.fang.hugopenapi.client.Entity.ESFEntity;
import com.bj58.fang.hugopenapi.client.Entity.result.CommonResult;
import com.bj58.fang.hugopenapi.client.Entity.result.Result;
import com.bj58.fang.hugopenapi.client.consta.UrlConst;
import com.bj58.fang.hugopenapi.client.enumn.AddOrUpdate;
import com.bj58.fang.hugopenapi.client.enumn.ComanyOrBroker;
import com.bj58.fang.hugopenapi.client.exception.ESFException;
import com.bj58.fang.hugopenapi.client.provider.TokenProvider;
import com.bj58.fang.hugopenapi.client.util.DefaultHttpPoolingManager;
import com.bj58.fang.hugopenapi.client.util.JSONUtils;
import com.bj58.fang.hugopenapi.client.util.PostUtils;
import com.bj58.fang.hugopenapi.client.util.StringUtils;

public class ESFService {

    private static final Logger logger = LoggerFactory.getLogger(ESFService.class);

    private static ESFService instance = new ESFService();
    
    public static ESFService getInstance() {
    	return instance;
    }
	
    public Result addUpdateBrokerESF(ESFEntity entity, AddOrUpdate type) throws ESFException {
    	String rs = addUpdateESF(entity, type);
    	Result result = null;
    	if(!StringUtils.isEmptyString(rs)) {
    		result = JSONUtils.getInstance().jsonResultToJAVAResult(rs);
    	}
		return result;
    }
    
    public CommonResult addUpdateCompanyESF(ESFEntity entity, AddOrUpdate type) throws ESFException {
    	String rs = addUpdateESF(entity, type);
    	CommonResult result = null;
    	if(!StringUtils.isEmptyString(rs)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(rs);
    	}
		return result;
    }
    
    /**
     * 
     * 经纪人 和公司返回的json结果不同
     * */
	public String addUpdateESF(ESFEntity entity, AddOrUpdate type) throws ESFException {
		String entityType = entity.getClass().getName();
		String uri = null;
		ComanyOrBroker corb = null;
		if(BrokerESFEntity.class.getName().equals(entityType)) {
			corb = ComanyOrBroker.Broker;
			uri = type.equals(AddOrUpdate.Add) ? UrlConst.BRO_ESF_ADD : UrlConst.BRO_ESF_UPDATE;
		}else if(CompanyESFEntity.class.getName().equals(entityType)) {
			corb = ComanyOrBroker.Comany;
			uri = type.equals(AddOrUpdate.Add) ? UrlConst.COM_ESF_ADD : UrlConst.COM_ESF_UPDATE;
		}
		//1.验证
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
			 long time = System.currentTimeMillis();
			 rs = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(
					 String.format(UrlConst.HOUSE_HANDLE_URL, uri,
							 InitService.getInstance().getClientId(),
							 time, TokenProvider.getInstance().getTokenSign(time)), data, headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("add esf house failed! clientId:" + InitService.getInstance().getClientId());
		}
		return rs;
		//3.发送返回封装
	}
	
	public Result deleteESF(long brokerid, long houseid, ComanyOrBroker corb) throws ESFException {
		String uri = null;
		if(ComanyOrBroker.Broker.equals(corb)) {
			uri =  UrlConst.BRO_ESF_DELETE;
		}
//		else if(ComanyOrBroker.Comany.equals(corb)) {
//			uri = UrlConst.BRO_ESF_ADD;
//		}
		String rs = "";
		Result result = null;
		try {
			 Map<String, String> data = new HashMap<String, String>();
			 data.put("brokerid", brokerid + "");
			 data.put("houseid", houseid + "");
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
			logger.error("delete esf house failed! clientId:" + InitService.getInstance().getClientId());
		}
		return result;
	}
}
