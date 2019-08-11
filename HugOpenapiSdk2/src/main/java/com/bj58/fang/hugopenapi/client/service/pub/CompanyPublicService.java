package com.bj58.fang.hugopenapi.client.service.pub;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bj58.fang.hugopenapi.client.Entity.result.CommonResult;
import com.bj58.fang.hugopenapi.client.consta.UrlConst;
import com.bj58.fang.hugopenapi.client.enumn.Cateid;
import com.bj58.fang.hugopenapi.client.enumn.Plats;
import com.bj58.fang.hugopenapi.client.enumn.RequestMethod;
import com.bj58.fang.hugopenapi.client.enumn.pub.DeptIdType;
import com.bj58.fang.hugopenapi.client.enumn.pub.IsFreeze;
import com.bj58.fang.hugopenapi.client.enumn.pub.State;
import com.bj58.fang.hugopenapi.client.enumn.pub.Type;
import com.bj58.fang.hugopenapi.client.enumn.pub.UserIdType;
import com.bj58.fang.hugopenapi.client.provider.TokenProvider;
import com.bj58.fang.hugopenapi.client.service.InitService;
import com.bj58.fang.hugopenapi.client.service.pub.entity.BrokerPhoneEnttiy;
import com.bj58.fang.hugopenapi.client.service.pub.entity.DealRecordEntity;
import com.bj58.fang.hugopenapi.client.service.pub.entity.DepartmentEntity;
import com.bj58.fang.hugopenapi.client.service.pub.entity.EmployeeEntity;
import com.bj58.fang.hugopenapi.client.service.pub.entity.HouseWorkEntity;
import com.bj58.fang.hugopenapi.client.service.pub.entity.RoleEntity;
import com.bj58.fang.hugopenapi.client.util.DefaultHttpPoolingManager;
import com.bj58.fang.hugopenapi.client.util.JSONUtils;
import com.bj58.fang.hugopenapi.client.util.PostUtils;
import com.bj58.fang.hugopenapi.client.util.StringUtils;

/**
 * 
 * @ClassName:CommonService
 * @Description:
 * @Author lishaoping
 * @Date 2018年12月14日
 * @Version V1.0
 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
 */
public class CompanyPublicService {

    private static final Logger logger = LoggerFactory.getLogger(CompanyPublicService.class);

	/**
	 * 公司库房源认领
	 * 公司房源库到经纪人房源库, brokerids如果多值，值之间|分割，如122|334|567
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月14日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
	 * @return String
	 */
	public CommonResult postCompanyFangToBrokerFang(String bianhao, String brokerids, Plats plats, Cateid cateid) {
		String res = null;
		try {
			Map<String, String> data = new HashMap<String, String>();
			data.put("bianhao", bianhao);
			data.put("brokerids", brokerids);
			data.put("plats", plats.getValue() + "");
			data.put("cateid", cateid.getValue() + "");
			Map<String, String> headers = new HashMap<String, String>();
			//2.接口调用时必须使用实时的当前时间--且tokenSign和timeSign中的一样
			long time = System.currentTimeMillis();
			res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(String.format(UrlConst.HOUSE_HANDLE_URL, UrlConst.PUB_COM_TO_BROKER, InitService.getInstance().getClientId(),
					 time, TokenProvider.getInstance().getTokenSign(time)), data, headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("postCompanyFangToBrokerFang failed");
		}
		CommonResult result = null;
    	if(!StringUtils.isEmptyString(res)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(res);
    	}
		return result;
	}
	
	/**
	 * 删除房源
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月19日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
	 * @return CommonResult
	 */
	public CommonResult deleteHouse(String bianhao, Cateid cateid) {
		String res = null;
		try {
			Map<String, String> data = new HashMap<String, String>();
			data.put("bianhao", bianhao);
			data.put("cateid", cateid.getValue() + "");
			Map<String, String> headers = new HashMap<String, String>();
			//2.接口调用时必须使用实时的当前时间--且tokenSign和timeSign中的一样
			long time = System.currentTimeMillis();
			res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(String.format(UrlConst.HOUSE_HANDLE_URL, UrlConst.PUB_HOUSE_DELETE, InitService.getInstance().getClientId(),
					 time, TokenProvider.getInstance().getTokenSign(time)), data, headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("deleteHouse failed");
		}
		CommonResult result = null;
    	if(!StringUtils.isEmptyString(res)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(res);
    	}
		return result;
	}
	
	
	
	/**
	 * 获取当前城市的房源标签的接口
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月19日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
	 * @return CommonResult
	 */
	public CommonResult getHousetag(int cityId) {
		String res = null;
		try {
			Map<String, String> data = new HashMap<String, String>();
			data.put("cityId", cityId + "");
			Map<String, String> headers = new HashMap<String, String>();
			//2.接口调用时必须使用实时的当前时间--且tokenSign和timeSign中的一样
			long time = System.currentTimeMillis();
			res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(String.format(UrlConst.HOUSE_HANDLE_URL, UrlConst.PUB_HOUSETAG, InitService.getInstance().getClientId(),
					 time, TokenProvider.getInstance().getTokenSign(time)), data, headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getHousetag failed");
		}
		CommonResult result = null;
    	if(!StringUtils.isEmptyString(res)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(res);
    	}
		return result;
	}
	
	
	
	/**
	 * 公司库关联经纪人房源列表
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月19日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
	 * @return CommonResult
	 */
	public CommonResult getSanWangFANGByCompanyFang(String bianhao, Cateid cateid) {
		String res = null;
		try {
			Map<String, String> data = new HashMap<String, String>();
			data.put("bianhao", bianhao);
			data.put("cateid", cateid.getValue() + "");
			Map<String, String> headers = new HashMap<String, String>();
			//2.接口调用时必须使用实时的当前时间--且tokenSign和timeSign中的一样
			long time = System.currentTimeMillis();
			res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(String.format(UrlConst.HOUSE_HANDLE_URL, UrlConst.PUB_FANGCOM_FANGSAN, InitService.getInstance().getClientId(),
					 time, TokenProvider.getInstance().getTokenSign(time)), data, headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getSanWangFANGByCompanyFang failed");
		}
		CommonResult result = null;
    	if(!StringUtils.isEmptyString(res)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(res);
    	}
		return result;
	}
	
	/**
	 * 公司库房源关联经纪人房源总数查询接口
	 * @param type: 查询类别
	 * @param starTime: 开始时间(时间戳毫秒)
	 * @param endTime: 结束时间(时间戳毫秒)
	 * @author lishaoping
	 * @Date 2018年12月19日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
	 * @return CommonResult
	 */
	public CommonResult getBrokerHouseCountByBianhao(String bianhao, Cateid cateid, Type type,
			Long startTime, Long endTime) {
		String res = null;
		try {
			Map<String, String> data = new HashMap<String, String>();
			data.put("bianhao", bianhao);
			data.put("cateid", cateid.getValue() + "");
			data.put("type", type.getValue() + "");
			if(null != startTime) {
				data.put("startTime", startTime + "");
			}
			if(null != endTime) {
				data.put("endTime", endTime + "");
			}
			Map<String, String> headers = new HashMap<String, String>();
			//2.接口调用时必须使用实时的当前时间--且tokenSign和timeSign中的一样
			long time = System.currentTimeMillis();
			res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(String.format(UrlConst.HOUSE_HANDLE_URL, UrlConst.PUB_FANGCOM_BROKER_FCOUNT, InitService.getInstance().getClientId(),
					 time, TokenProvider.getInstance().getTokenSign(time)), data, headers);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getBrokerHouseCountByBianhao failed");
		}
		CommonResult result = null;
    	if(!StringUtils.isEmptyString(res)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(res);
    	}
		return result;
	}
	
	/**
	 * 公司库房源关联经纪人房源总数查询接口; 分页查询
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月19日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
	 * @return CommonResult
	 */
	public CommonResult getBrokerHouseCountByBianhao(String bianhao, Cateid cateid, Type type, Integer pageIndex, Integer pageSize,
			Long startTime, Long endTime) {
		String res = null;
		try {
			Map<String, String> data = new HashMap<String, String>();
			data.put("bianhao", bianhao);
			data.put("cateid", cateid.getValue() + "");
			data.put("type", type.getValue() + "");
			if(null != pageIndex) {
				data.put("pageIndex", pageIndex + "");
			}
			if(null != pageSize) {
				data.put("pageSize", pageSize + "");
			}
			if(null != startTime) {
				data.put("startTime", startTime + "");
			}
			if(null != endTime) {
				data.put("endTime", endTime + "");
			}
			res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(
					String.format("%s%s", String.format(UrlConst.HOUSE_PREX_URL, UrlConst.PUB_FANGCOM_BROKER_FCOUNT2), "?"), data,
					null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getBrokerHouseCountByBianhao failed");
		}
		CommonResult result = null;
    	if(!StringUtils.isEmptyString(res)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(res);
    	}
		return result;
	}
	/**
	 * 公司房源是否存在
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月19日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
	 * @return CommonResult
	 */
	public CommonResult existsCompanyFang(String bianhao, Cateid cateid) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("bianhao", bianhao);
		data.put("cateid", cateid);
		return PostUtils.getInstance().postProxy(RequestMethod.POST, "existsCompanyFang", UrlConst.PUB_FANGCOM_EXISTS, data);
	}
	
	/**
	 * 查询公司库房源状态
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月19日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
	 * @return CommonResult
	 */
	public CommonResult getCompanyFangState(String bianhao, Cateid cateid) {
		String res = null;
		try {
			Map<String, String> data = new HashMap<String, String>();
			data.put("bianhao", bianhao);
			data.put("cateid", cateid.getValue() + "");
			Map<String, String> headers = new HashMap<String, String>();
			//2.接口调用时必须使用实时的当前时间--且tokenSign和timeSign中的一样
			long time = System.currentTimeMillis();
			res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(String.format(UrlConst.HOUSE_HANDLE_URL, UrlConst.PUB_FANGCOM_STATUS, InitService.getInstance().getClientId(),
					 time, TokenProvider.getInstance().getTokenSign(time)), data, headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getCompanyFangState failed");
		}
		CommonResult result = null;
    	if(!StringUtils.isEmptyString(res)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(res);
    	}
		return result;
	}
	
	/**
	 * 内部房源编号更新公司库房源状态
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月19日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
	 * @return CommonResult
	 */
	public CommonResult updateCompanyFangState(String bianhao, Cateid cateid, State state) {
		String res = null;
		try {
			Map<String, String> data = new HashMap<String, String>();
			data.put("bianhao", bianhao);
			data.put("cateid", cateid.getValue() + "");
			data.put("state", state.getValue() + "");
			res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(
					String.format("%s%s", String.format(UrlConst.HOUSE_PREX_URL, UrlConst.PUB_FANGCOM_STATUS_UPDATE), "?"), data,
					null);
			Map<String, String> headers = new HashMap<String, String>();
			//2.接口调用时必须使用实时的当前时间--且tokenSign和timeSign中的一样
			long time = System.currentTimeMillis();
			res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(String.format(UrlConst.HOUSE_HANDLE_URL, UrlConst.PUB_FANGCOM_STATUS_UPDATE, InitService.getInstance().getClientId(),
					 time, TokenProvider.getInstance().getTokenSign(time)), data, headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("updateCompanyFangState failed");
		}
		CommonResult result = null;
    	if(!StringUtils.isEmptyString(res)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(res);
    	}
		return result;
	}
	
	/**
	 * 删除经纪人的全部房源
	 * @param reason 删除原因 0：离职
	 * @author lishaoping
	 * @Date 2018年12月19日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
	 * @return CommonResult
	 */
	public CommonResult deleteBrokerFang(String brokerid, String reason) {
		String res = null;
		try {
			Map<String, String> data = new HashMap<String, String>();
			data.put("brokerid", brokerid);
			data.put("reason", reason);
			Map<String, String> headers = new HashMap<String, String>();
			//2.接口调用时必须使用实时的当前时间--且tokenSign和timeSign中的一样
			long time = System.currentTimeMillis();
			res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(String.format(UrlConst.HOUSE_HANDLE_URL, UrlConst.PUB_FANGBRO_DELETE, InitService.getInstance().getClientId(),
					 time, TokenProvider.getInstance().getTokenSign(time)), data, headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("deleteBrokerFang failed");
		}
		CommonResult result = null;
    	if(!StringUtils.isEmptyString(res)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(res);
    	}
		return result;
	}
	
	/**
	 * 获取当前城市的房源标签
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月19日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
	 * @return CommonResult
	 */
	public CommonResult currCityFangTag(int cityId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cityId", cityId);
		return PostUtils.getInstance().postProxy(RequestMethod.GET, "currCityFangTag", UrlConst.PUB_CURRCITY_FANG_TAG, paramMap);
	}
	
	/**
	 * 部门查询
	 * @param brokerageDeptLevel 经纪公司部门层级
	 * @author lishaoping
	 * @Date 2018年12月19日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
	 * @return CommonResult
	 */
	public CommonResult departmentQuery(String deptId, DeptIdType deptIdType, Integer brokerageDeptLevel) {
		String res = null;
		try {
			Map<String, String> data = new HashMap<String, String>();
			data.put("deptId", deptId);
			data.put("deptIdType", deptIdType.getValue() + "");
			data.put("brokerageDeptLevel", brokerageDeptLevel + "");
			Map<String, String> headers = new HashMap<String, String>();
			long time = System.currentTimeMillis();
			res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(String.format(UrlConst.HOUSE_HANDLE_URL, UrlConst.PUB_DEPARTMENT_QUERY, InitService.getInstance().getClientId(),
					 time, TokenProvider.getInstance().getTokenSign(time)), data, headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("departmentQuery failed");
		}
		CommonResult result = null;
    	if(!StringUtils.isEmptyString(res)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(res);
    	}
		return result;
	}
	/**
	 * 部门新增
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月19日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
	 * @return CommonResult
	 */
	public CommonResult departmentAdd(DepartmentEntity dept) {
		String res = null;
		try {
			Map<String, String> data = PostUtils.getInstance().entityToMapByAnno(dept, null, null);
			Map<String, String> headers = new HashMap<String, String>();
			long time = System.currentTimeMillis();
			res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(String.format(UrlConst.HOUSE_HANDLE_URL, UrlConst.PUB_DEPARTMENT_ADD, InitService.getInstance().getClientId(),
					 time, TokenProvider.getInstance().getTokenSign(time)), data, headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("departmentAdd failed");
		}
		CommonResult result = null;
    	if(!StringUtils.isEmptyString(res)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(res);
    	}
		return result;
	}
	
	/**
	 * 员工新增
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月19日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
	 * @return CommonResult
	 */
	public CommonResult employeeAddUpdate(EmployeeEntity employee) {
		return PostUtils.getInstance().postProxy(RequestMethod.POST, "employeeAddOrUpdate", UrlConst.PUB_EMPLOYEE_ADD, null);
	}
	
	/**
	 * 员工查询
	 * @param userIdType 用户ID类型
	 * @author lishaoping
	 * @Date 2018年12月19日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
	 * @return CommonResult
	 */
	public CommonResult employeeQuery(String userId, UserIdType userIdType) {
		String res = null;
		try {
			Map<String, String> data = new HashMap<String, String>();
			data.put("userId", userId);
			data.put("userIdType", userIdType.getValue() + "");
			Map<String, String> headers = new HashMap<String, String>();
			long time = System.currentTimeMillis();
			res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(String.format(UrlConst.HOUSE_HANDLE_URL, UrlConst.PUB_EMPLOYEE_QUERY, InitService.getInstance().getClientId(),
					 time, TokenProvider.getInstance().getTokenSign(time)), data, headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("employeeQuery failed");
		}
		CommonResult result = null;
    	if(!StringUtils.isEmptyString(res)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(res);
    	}
		return result;
	}
	
	/**
	 * 员工离职
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月19日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
	 * @return CommonResult
	 */
	public CommonResult employeeDimission(String userId, UserIdType userIdType) {
		String res = null;
		try {
			Map<String, String> data = new HashMap<String, String>();
			data.put("userId", userId);
			data.put("userIdType", userIdType.getValue() + "");
			Map<String, String> headers = new HashMap<String, String>();
			long time = System.currentTimeMillis();
			res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(String.format(UrlConst.HOUSE_HANDLE_URL, UrlConst.PUB_EMPLOYEE_DIMISSION, InitService.getInstance().getClientId(),
					 time, TokenProvider.getInstance().getTokenSign(time)), data, headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("employeeDimission failed");
		}
		CommonResult result = null;
    	if(!StringUtils.isEmptyString(res)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(res);
    	}
		return result;
	}
	
	/**
	 * 员工批量冻结
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月19日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
	 * @return CommonResult
	 */
	public CommonResult employeeFreezeBatch(String userIds, UserIdType userIdType, IsFreeze isFreeze) {
		String res = null;
		try {
			Map<String, String> data = new HashMap<String, String>();
			data.put("userIds", userIds);
			data.put("userIdType", userIdType.getValue() + "");
			data.put("isFreeze", isFreeze.getValue() + "");
			Map<String, String> headers = new HashMap<String, String>();
			long time = System.currentTimeMillis();
			res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(String.format(UrlConst.HOUSE_HANDLE_URL, UrlConst.PUB_EMPLOYEE_BATCH_FREEZE, InitService.getInstance().getClientId(),
					 time, TokenProvider.getInstance().getTokenSign(time)), data, headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("employeeFreezeBatch failed");
		}
		CommonResult result = null;
    	if(!StringUtils.isEmptyString(res)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(res);
    	}
		return result;
	}
	/**
	 * 绑定三网账号
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月19日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
	 * @return CommonResult
	 */
	public CommonResult bindSanWangAcount(String userId, UserIdType userIdType, Long brokerId) {
		String res = null;
		try {
			Map<String, String> data = new HashMap<String, String>();
			data.put("userId", userId);
			data.put("brokerId", brokerId + "");
			data.put("userIdType", userIdType.getValue() + "");
			Map<String, String> headers = new HashMap<String, String>();
			long time = System.currentTimeMillis();
			res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(String.format(UrlConst.HOUSE_HANDLE_URL, UrlConst.PUB_BIND_SANWANG_ACOUNT, InitService.getInstance().getClientId(),
					 time, TokenProvider.getInstance().getTokenSign(time)), data, headers);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("bindSanWangAcount failed");
		}
		CommonResult result = null;
    	if(!StringUtils.isEmptyString(res)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(res);
    	}
		return result;
	}
	
	/**
	 * 解除三网绑定
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月19日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
	 * @return CommonResult
	 */
	public CommonResult deleteSiteBinding(String userId, UserIdType userIdType) {
		String res = null;
		try {
			Map<String, String> data = new HashMap<String, String>();
			data.put("userId", userId);
			data.put("userIdType", userIdType.getValue() + "");
			Map<String, String> headers = new HashMap<String, String>();
			long time = System.currentTimeMillis();
			res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(String.format(UrlConst.HOUSE_HANDLE_URL, UrlConst.PUB_DELETE_SITE_BINDING, InitService.getInstance().getClientId(),
					 time, TokenProvider.getInstance().getTokenSign(time)), data, headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("deleteSiteBinding failed");
		}
		CommonResult result = null;
    	if(!StringUtils.isEmptyString(res)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(res);
    	}
		return result;
	}
	
	/**
	 * 角色新增和更新
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月19日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
	 * @return CommonResult
	 */
	public CommonResult roleAddUpdate(RoleEntity role) {
		String res = null;
		try {
			Map<String, String> data = PostUtils.getInstance().entityToMapByAnno(role, null, null);
			Map<String, String> headers = new HashMap<String, String>();
			long time = System.currentTimeMillis();
			res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(String.format(UrlConst.HOUSE_HANDLE_URL, UrlConst.PUB_ROLE_ADD, InitService.getInstance().getClientId(),
					 time, TokenProvider.getInstance().getTokenSign(time)), data, headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("roleAddUpdate failed");
		}
		CommonResult result = null;
    	if(!StringUtils.isEmptyString(res)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(res);
    	}
		return result;
	}
	
	/**
	 * 新建成交记录接口
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月19日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
	 * @return CommonResult
	 */
	public CommonResult dealRecordAddUpdate(DealRecordEntity dealRecord) {
		String res = null;
		try {
			Map<String, String> data = PostUtils.getInstance().entityToMapByAnno(dealRecord, null, null);
			Map<String, String> headers = new HashMap<String, String>();
			long time = System.currentTimeMillis();
			res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(String.format(UrlConst.HOUSE_HANDLE_URL, UrlConst.PUB_DEALRECORD_ADD, InitService.getInstance().getClientId(),
					 time, TokenProvider.getInstance().getTokenSign(time)), data, headers);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("dealRecordAddUpdate failed");
		}
		CommonResult result = null;
    	if(!StringUtils.isEmptyString(res)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(res);
    	}
		return result;
	}
	
	/**
	 * 新建更新带看记录
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月19日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
	 * @return CommonResult
	 */
	public CommonResult houseworkAddUpdate(HouseWorkEntity housework) {
		String res = null;
		try {
			Map<String, String> data = PostUtils.getInstance().entityToMapByAnno(housework, null, null);
			Map<String, String> headers = new HashMap<String, String>();
			long time = System.currentTimeMillis();
			res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(String.format(UrlConst.HOUSE_HANDLE_URL, UrlConst.PUB_HOUSEWORK_ADD, InitService.getInstance().getClientId(),
					 time, TokenProvider.getInstance().getTokenSign(time)), data, headers);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("houseworkAddUpdate failed");
		}
		CommonResult result = null;
    	if(!StringUtils.isEmptyString(res)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(res);
    	}
		return result;
	}
	
	/**
	 * 配置可视盘
	 * 
	 * @param communityList 磐石小区id，多个id之间使用逗号间隔
	 * @author lishaoping
	 * @Date 2018年12月19日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub
	 * @return CommonResult
	 */
	public CommonResult settingsViewRange(String deptId, DeptIdType deptIdType, String communityList) {
		String res = null;
		try {
			Map<String, String> data = new HashMap<String, String>();
			data.put("deptId", deptId);
			data.put("deptIdType", deptIdType.getValue() + "");
			data.put("communityList", communityList);
			Map<String, String> headers = new HashMap<String, String>();
			long time = System.currentTimeMillis();
			res = DefaultHttpPoolingManager.getInstance().getPostJsonWithHeader(String.format(UrlConst.HOUSE_HANDLE_URL, UrlConst.PUB_SETTING_VIEW_RANGE, InitService.getInstance().getClientId(),
					 time, TokenProvider.getInstance().getTokenSign(time)), data, headers);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("settingsViewRange failed");
		}
		CommonResult result = null;
    	if(!StringUtils.isEmptyString(res)) {
    		result = JSONUtils.getInstance().jsonCommonToJAVACommonResult(res);
    	}
		return result;
	}
}
