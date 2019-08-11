package com.bj58.fang.hugopenapi.client.service.pub.entity;

import com.bj58.fang.hugopenapi.client.annotations.Request;

public class DealRecordEntity {

	@Request(needVali = true, strLenth = "[0,20]")
	private String bianhao;// 经纪公司内部房源编号，<=20位，字母数字均可
	@Request(needVali = true)
	private Long dealTime;// 成交时间（10位时间戳）
	@Request(needVali = true)
	private Long dealPrice;// 成交总价（单位：元）
	@Request(needVali = true)
	private String deptId;// 成交部门ID
	@Request(needVali = true)
	private Integer deptIdType;// 部门ID类型 1.网络门店 3.经纪公司部门id
	@Request(needVali = false)
	private Integer deptLevel;// 部门层级
	@Request(needVali = true)
	private String userId;// 成交经纪人id
	@Request(needVali = true)
	private Integer userIdType;// 用户id类型 1.网络门店 3.经纪公司
	@Request(needVali = false)
	private Long commission;// 佣金收入（单位：元）
	@Request(needVali = false)
	private String contractNo;// 交易合同编号(不超过100个字符)

	/**
	 * 经纪公司内部房源编号，<=20位，字母数字均可
	 *
	 */
	public void setBianhao(String bianhao) {
		this.bianhao = bianhao;
	}

	public String getBianhao() {
		return bianhao;
	}

	/**
	 * 成交时间（10位时间戳）
	 *
	 */
	public void setDealTime(Long dealTime) {
		this.dealTime = dealTime;
	}

	public Long getDealTime() {
		return dealTime;
	}

	/**
	 * 成交总价（单位：元）
	 *
	 */
	public void setDealPrice(Long dealPrice) {
		this.dealPrice = dealPrice;
	}

	public Long getDealPrice() {
		return dealPrice;
	}

	/**
	 * 成交部门ID
	 *
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptId() {
		return deptId;
	}

	/**
	 * 部门ID类型 1.网络门店 3.经纪公司部门id
	 *
	 */
	public void setDeptIdType(Integer deptIdType) {
		this.deptIdType = deptIdType;
	}

	public Integer getDeptIdType() {
		return deptIdType;
	}

	/**
	 * 部门层级
	 *
	 */
	public void setDeptLevel(Integer deptLevel) {
		this.deptLevel = deptLevel;
	}

	public Integer getDeptLevel() {
		return deptLevel;
	}

	/**
	 * 成交经纪人id
	 *
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	/**
	 * 用户id类型 1.网络门店 3.经纪公司
	 *
	 */
	public void setUserIdType(Integer userIdType) {
		this.userIdType = userIdType;
	}

	public Integer getUserIdType() {
		return userIdType;
	}

	/**
	 * 佣金收入（单位：元）
	 *
	 */
	public void setCommission(Long commission) {
		this.commission = commission;
	}

	public Long getCommission() {
		return commission;
	}

	/**
	 * 交易合同编号(不超过100个字符)
	 *
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getContractNo() {
		return contractNo;
	}

}
