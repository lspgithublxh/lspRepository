package com.bj58.fang.hugopenapi.client.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bj58.fang.hugopenapi.client.Entity.result.CommonResult;
import com.bj58.fang.hugopenapi.client.Entity.result.Detail;
import com.bj58.fang.hugopenapi.client.Entity.result.Detail2;
import com.bj58.fang.hugopenapi.client.Entity.result.DispatchResults;
import com.bj58.fang.hugopenapi.client.Entity.result.Result;
import com.bj58.fang.hugopenapi.client.Entity.result.Result2;

public class JSONUtils {

	private static final String pack = "com.bj58.fang.hugopenapi.client.annoEntity";
	
	private static JSONUtils util = new JSONUtils();
     
     public static JSONUtils getInstance() {
    	 return util;
     }
	
	public Object jsonToBean(Class<?> cla, JSONObject obj) {
		Object instance = null;
		try {
			instance = cla.newInstance();
			Set<Entry<String, Object>> entrySet = obj.entrySet();
			for(Entry<String, Object> entry : entrySet) {
				Field f;
				try {
					f = cla.getDeclaredField(entry.getKey());
					f.setAccessible(true);
					f.set(instance, entry.getValue());
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return instance;
	}
	
	public Class<?> findClass(String claName){
		String fullPath = String.format("%s.%s", pack, claName);
		try {
			Class<?> t = Class.forName(fullPath);
			return t;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Result2 jsonResultToJAVAResult2(String jsonstr) {
		Result2 rs = null;
		try {
			rs = new Result2();
			JSONObject obj = JSONObject.parseObject(jsonstr);
			rs.setCode(obj.getLongValue("code"));
			rs.setMessage(obj.getString("message"));
			rs.setStatus(obj.getString("status"));
			if(obj.containsKey("detail")) {
				List<Detail2> drlist = new ArrayList<Detail2>();
				
				rs.setDetail(drlist);
				JSONArray jdetail = obj.getJSONArray("detail");
				for(int j = 0; j < jdetail.size(); j++) {
					JSONObject item = jdetail.getJSONObject(j);
					Detail2 detail2 = new Detail2();
					drlist.add(detail2);
					detail2.setBrokerId(item.getLong("brokerId"));
					JSONObject unitedPostResult = item.getJSONObject("unitedPostResult");
					Detail detail = new Detail();
					detail2.setUnitedPostResult(detail);
					detail.setCode(unitedPostResult.getLong("code"));
					detail.setHouseState(unitedPostResult.getLong("houseState"));
					detail.setUnitedHouseId(unitedPostResult.getLongValue("unitedHouseId"));
					if(unitedPostResult.containsKey("dispatchResults")) {
						JSONArray drs = unitedPostResult.getJSONArray("dispatchResults");
						List<DispatchResults> drlist2 = new ArrayList<DispatchResults>();
						detail.setDispatchResults(drlist2);
						for(int i = 0; i < drs.size(); i++) {
							JSONObject san = drs.getJSONObject(i);
							DispatchResults dro = new DispatchResults();
							dro.setCode(san.getLong("code"));
							dro.setCompany(san.getLongValue("company"));
							dro.setInfoid(san.getLong("infoid"));
							dro.setInfoState(san.getLongValue("infoState"));
							dro.setMsg(san.getString("msg"));
							dro.setUrl(san.getString("url"));
							dro.setUnitedHouseId(san.getLongValue("unitedHouseId"));
							drlist2.add(dro);
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public Result jsonResultToJAVAResult(String jsonstr) {
		Result rs = null;
		try {
			rs = new Result();
			JSONObject obj = JSONObject.parseObject(jsonstr);
			rs.setCode(obj.getLongValue("code"));
			rs.setMessage(obj.getString("message"));
			rs.setStatus(obj.getString("status"));
			if(obj.containsKey("detail")) {
				Detail detail = new Detail();
				rs.setDetail(detail);
				JSONObject jdetail = obj.getJSONObject("detail");
				detail.setCode(jdetail.getLong("code"));
				detail.setHouseState(jdetail.getLong("houseState"));
				detail.setUnitedHouseId(jdetail.getLongValue("unitedHouseId"));
				if(jdetail.containsKey("dispatchResults")) {
					JSONArray drs = jdetail.getJSONArray("dispatchResults");
					List<DispatchResults> drlist = new ArrayList<DispatchResults>();
					detail.setDispatchResults(drlist);
					for(int i = 0; i < drs.size(); i++) {
						JSONObject san = drs.getJSONObject(i);
						DispatchResults dro = new DispatchResults();
						dro.setCode(san.getLong("code"));
						dro.setCompany(san.getLongValue("company"));
						dro.setInfoid(san.getLong("infoid"));
						dro.setInfoState(san.getLongValue("infoState"));
						dro.setMsg(san.getString("msg"));
						dro.setUrl(san.getString("url"));
						dro.setUnitedHouseId(san.getLongValue("unitedHouseId"));
						drlist.add(dro);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public CommonResult jsonCommonToJAVACommonResult(String jsonstr) {
		CommonResult cr = null;
		try {
			cr = new CommonResult();
			if(!StringUtils.isEmptyString(jsonstr)) {
				if(jsonstr.trim().startsWith("[")) {
					cr.setCode(200);
					cr.setMessage("成功，结果为json数组字符串");
					cr.setStatus("ok");
					cr.setDetail(jsonstr);
				}else if(jsonstr.trim().startsWith("{")){
					JSONObject obj = JSONObject.parseObject(jsonstr);
					boolean setOk = false;
					if(obj.containsKey("code")) {
						cr.setCode(obj.getIntValue("code"));
						setOk = true;
					}
					if(obj.containsKey("message")) {
						cr.setMessage(obj.getString("message"));
						setOk = true;
					}
					if(obj.containsKey("status")) {
						cr.setStatus(obj.getString("status"));
						setOk = true;
					}
					if(obj.containsKey("detail")) {
						cr.setDetail(obj.getString("detail"));
						setOk = true;
					}else if(obj.containsKey("data")) {
						cr.setDetail(obj.getString("detail"));
						setOk = true;
					}
					if(!setOk) {
						cr.setCode(200);
						cr.setMessage("成功");
						cr.setStatus("ok");
						cr.setDetail(jsonstr);
					}
				}else {
					cr.setCode(200);
					cr.setMessage("成功");
					cr.setStatus("ok");
					cr.setDetail(jsonstr);
				}
			}else {
				cr.setCode(500);
				cr.setMessage("失败");
				cr.setStatus("error");
				cr.setDetail(jsonstr);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cr;
	}
}
