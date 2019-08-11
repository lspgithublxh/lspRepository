package com.bj58.fang.hugopenapi.client.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bj58.fang.hugopenapi.client.Entity.result.CommonResult;
import com.bj58.fang.hugopenapi.client.annotations.Request;
import com.bj58.fang.hugopenapi.client.annotations.UpdateRequest;
import com.bj58.fang.hugopenapi.client.consta.UrlConst;
import com.bj58.fang.hugopenapi.client.enumn.AddOrUpdate;
import com.bj58.fang.hugopenapi.client.enumn.RequestMethod;
import com.bj58.fang.hugopenapi.client.exception.ESFException;
import com.bj58.fang.hugopenapi.client.provider.TokenProvider;
import com.bj58.fang.hugopenapi.client.service.InitService;

public class PostUtils {

    private static final Logger logger = LoggerFactory.getLogger(PostUtils.class);

	private static PostUtils util = new PostUtils();
	
	public static PostUtils getInstance() {
		return util;
	}
	
	/**
	 * 
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月12日
	 * @Package com.bj58.fang.hugopenapi.client.util
	 * @return Map<String,String>
	 */
	public Map<String, String> entityToMap(Object entity, List<String> exceptKeys) throws ESFException {
		Field[] fields = entity.getClass().getDeclaredFields();//, Map<String, Boolean> keyNeedMap
		Map<String, String> data = new HashMap<String, String>();
		try {
			for(Field f : fields) {
				String name = f.getName();
				String val = f.get(entity).toString();
				if(exceptKeys != null && !exceptKeys.contains(name)) {
					data.put(name, val);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * 覆盖问题也可以，值只是子类的
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月13日
	 * @Package com.bj58.fang.hugopenapi.client.util
	 * @return Map<String,String>
	 */
	public Map<String, String> entityToMapByAnno(Object entity, List<String> exceptKeys, AddOrUpdate type) throws Exception {
		Class<?> curr = entity.getClass();
		Map<String, String> data = new HashMap<String, String>();
		while(!"java.lang.Object".equals(curr.getName())) {
			Field[] fields = curr.getDeclaredFields();//, Map<String, Boolean> keyNeedMap
			try {
				//父类的属性也要传
				for(Field f : fields) {
					String name = f.getName();
					f.setAccessible(true);
					Object val = f.get(entity);
					String v = null;
					if(val == null) {
						continue;//比如更新的时候不传数据--不设置数据
					}else {
						v = val.toString();
					}
					if(exceptKeys != null && exceptKeys.contains(name)) {
					}else {
						if(AddOrUpdate.Add.equals(type)) {
							Request req = f.getAnnotation(Request.class);
							if(req != null) {
								data.put(name, v);
							}
						}else if(AddOrUpdate.Update.equals(type)) {
							Request req = f.getAnnotation(Request.class);
							UpdateRequest upReq = f.getAnnotation(UpdateRequest.class);
							if(req != null || upReq != null) {
								data.put(name, v);
							}
						}else {
							Request req = f.getAnnotation(Request.class);
							if(req != null) {
								data.put(name, v);
							}
						}
					}
					
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				logger.error("bean to map error!" + e.getMessage());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				logger.error("bean to map error!" + e.getMessage());
			}
			curr = curr.getSuperclass();
		}
		return data;
	}
	
	/**
	 * 统一的数据发送调用接口
	 * @param 
	 * @author lishaoping
	 * @Package com.bj58.fang.hugopenapi.client.util
	 * @return CommonResult
	 */
	public CommonResult postProxy(RequestMethod method, String message, String uri, Map<String, Object> paramsMap) {//Object... params
		String res = null;
		try {
			Map<String, String> data = new HashMap<String, String>();
			if(paramsMap != null && paramsMap.size() > 0) {
				for(Entry<String, Object> entry : paramsMap.entrySet()) {
					String variaName = entry.getKey();
					Object param = entry.getValue();
					String typeName = param.getClass().getName();
					if(typeName.contains(".enumn.")) {//认为是枚举类型
						Method getValue = param.getClass().getDeclaredMethod("getValue");
						Object val = getValue.invoke(param);
						data.put(variaName, val.toString());
					}else if(typeName.contains(".entity.")) {//认为是实体
						Map<String, String> dmap = PostUtils.getInstance().entityToMapByAnno(param, null, null);
						data.putAll(dmap);
					}else {//认为就是基本类型,String, int, long,....
						data.put(variaName, param.toString());
					}
				}
			}
			if(method.equals(RequestMethod.GET)) {
				res = DefaultHttpPoolingManager.getInstance().getGetJson(String.format(UrlConst.HOUSE_PREX_URL, uri), data);
			}else if(method.equals(RequestMethod.POST)) {
				Map<String, String> headers = new HashMap<String, String>();
				long time = System.currentTimeMillis();
				res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(String.format(UrlConst.HOUSE_HANDLE_URL, uri, InitService.getInstance().getClientId(),
						 time, TokenProvider.getInstance().getTokenSign(time)), data, headers);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(String.format("%s failed", message));
		}
		CommonResult result = null;
    	if(!StringUtils.isEmptyString(res)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(res);
    	}
		return result;
	}
}
