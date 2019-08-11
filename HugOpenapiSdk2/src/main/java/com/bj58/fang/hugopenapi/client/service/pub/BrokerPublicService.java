package com.bj58.fang.hugopenapi.client.service.pub;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bj58.fang.hugopenapi.client.Entity.result.CommonResult;
import com.bj58.fang.hugopenapi.client.consta.UrlConst;
import com.bj58.fang.hugopenapi.client.enumn.pub.QueryType;
import com.bj58.fang.hugopenapi.client.provider.TokenProvider;
import com.bj58.fang.hugopenapi.client.service.InitService;
import com.bj58.fang.hugopenapi.client.service.pub.entity.BrokerPhoneEnttiy;
import com.bj58.fang.hugopenapi.client.util.DefaultHttpPoolingManager;
import com.bj58.fang.hugopenapi.client.util.JSONUtils;
import com.bj58.fang.hugopenapi.client.util.PostUtils;
import com.bj58.fang.hugopenapi.client.util.StringUtils;

public class BrokerPublicService {
	
    private static final Logger logger = LoggerFactory.getLogger(BrokerPublicService.class);

	/**
	 * 获取三网经纪人id
	 * @param loginMobile 登录手机号（通过用户名查询时，可不传该参数）
	 * @param userName 用户名（通过手机号查询时，可不传该参数）
	 * @param returnJsonStr 指定返回值格式，参数存在则返回json格式数据，否则直接返回id（用于兼容旧的返回值）
	 * @author lishaoping
	 * @Date 2018年12月18日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
	 * @return CommonResult
	 */
	public CommonResult getSanwangBrokerByPhoneOrUserName(QueryType type, String loginMobile, String userName, boolean returnJsonStr) {
		String res = null;
		try {
			Map<String, String> data = new HashMap<String, String>();
			data.put("type", type.getValue() + "");
			data.put("loginMobile", loginMobile);
			data.put("userName", userName);
			if(returnJsonStr) {
				data.put("json", "");
			}
			Map<String, String> headers = new HashMap<String, String>();
			long time = System.currentTimeMillis();
			res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(String.format(UrlConst.HOUSE_HANDLE_URL, UrlConst.PUB_BROID_GET, InitService.getInstance().getClientId(),
					 time, TokenProvider.getInstance().getTokenSign(time)), data, headers);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getSanwangBrokerByPhoneOrUserName failed");
		}
		CommonResult result = null;
    	if(!StringUtils.isEmptyString(res)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(res);
    	}
		return result;
	}
	
	/**
	 * 根据用户名获取三网经纪人ID
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月18日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
	 * @return CommonResult
	 */
	public CommonResult getSanwangBrokerIdByUserName(String userName) {
		String res = null;
		try {
			Map<String, String> data = new HashMap<String, String>();
			data.put("userName", userName);
			Map<String, String> headers = new HashMap<String, String>();
			long time = System.currentTimeMillis();
			res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(String.format(UrlConst.HOUSE_HANDLE_URL, UrlConst.PUB_BROID_GETBY_UN, InitService.getInstance().getClientId(),
					 time, TokenProvider.getInstance().getTokenSign(time)), data, headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getSanwangBrokerIdByUserName failed");
		}
		CommonResult result = null;
    	if(!StringUtils.isEmptyString(res)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(res);
    	}
		return result;
	}
	
	/**
	 * 根据身份证号码查询三网经纪人id
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月19日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
	 * @return CommonResult
	 */
	public CommonResult getSanwangBrokerIdByIdentity(String userCard) {
		String res = null;
		try {
			Map<String, String> data = new HashMap<String, String>();
			data.put("userCard", userCard);
			Map<String, String> headers = new HashMap<String, String>();
			//2.接口调用时必须使用实时的当前时间--且tokenSign和timeSign中的一样
			long time = System.currentTimeMillis();
			res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(String.format(UrlConst.HOUSE_HANDLE_URL, UrlConst.PUB_BROID_GETBY_IDEN, InitService.getInstance().getClientId(),
					 time, TokenProvider.getInstance().getTokenSign(time)), data, headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getSanwangBrokerIdByIdentity failed");
		}
		CommonResult result = null;
    	if(!StringUtils.isEmptyString(res)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(res);
    	}
		return result;
	}
	
	/**
	 * 经纪人通话详情
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月18日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
	 * @return CommonResult
	 */
	public CommonResult queryBrokerTelephone(BrokerPhoneEnttiy entity) {
		String res = null;
		try {
			Map<String, String> data = PostUtils.getInstance().entityToMapByAnno(entity, null, null);
			res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(
					String.format("%s%s", String.format(UrlConst.HOUSE_PREX_URL, UrlConst.PUB_BROTELEPHONE_DETAIL), "?"), data,
					null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("queryBrokerTelephone failed");
		}
		CommonResult result = null;
    	if(!StringUtils.isEmptyString(res)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(res);
    	}
		return result;
	}
	
	/**
	 * 查询经纪人通话详情
	 * @param clientPhone 非小号所有者的号码，可筛选出经纪人与该号码的通话详情
	 * @author lishaoping
	 * @Date 2018年12月19日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
	 * @return CommonResult
	 */
	public CommonResult queryBrokerTelephone(String brokerid, String clientPhone, String startTime, String endTime, Integer page, Integer size ) {
		String res = null;
		try {
			Map<String, String> data = new HashMap<String, String>();
			data.put("brokerid", brokerid);
			if(!StringUtils.isEmptyString(startTime)) {
				data.put("startTime", startTime);
			}
			if(!StringUtils.isEmptyString(endTime)) {
				data.put("endTime", endTime);
			}
			
			if(page != null) {
				data.put("page", page + "");
			}
			if(size != null) {
				data.put("size", size + "");
			}
			Map<String, String> headers = new HashMap<String, String>();
			long time = System.currentTimeMillis();
			res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(String.format(UrlConst.HOUSE_HANDLE_URL, UrlConst.PUB_BROTELEPHONE_DETAIL, InitService.getInstance().getClientId(),
					 time, TokenProvider.getInstance().getTokenSign(time)), data, headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("queryBrokerTelephone failed");
		}
		CommonResult result = null;
    	if(!StringUtils.isEmptyString(res)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(res);
    	}
		return result;
	}
	
	/**
	 * 批量查询经纪人通话详情
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月19日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
	 * @return CommonResult
	 */
	public CommonResult queryBrokerTelephoneBatch(String brokerIds, String startDate, String endDate, Integer page, Integer size ) {
		String res = null;
		try {
			Map<String, String> data = new HashMap<String, String>();
			data.put("brokerIds", brokerIds);
			data.put("startDate", startDate);
			data.put("endDate", endDate);
			if(page != null) {
				data.put("page", page + "");
			}
			if(size != null) {
				data.put("size", size + "");
			}
			res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(
					String.format("%s%s", String.format(UrlConst.HOUSE_PREX_URL, UrlConst.PUB_BROTELEPHONE_DETAILBATCH), "?"), data,
					null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("queryBrokerTelephoneBatch failed");
		}
		CommonResult result = null;
    	if(!StringUtils.isEmptyString(res)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(res);
    	}
		return result;
	}

}
