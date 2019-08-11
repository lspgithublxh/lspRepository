package com.bj58.fang.hugopenapi.client.service.pub.entity;

import com.bj58.fang.hugopenapi.client.annotations.Request;

public class HouseWorkEntity {

	@Request(needVali = true)
	private Integer bizType;// 资源类型 1.二手房 2.租房
	@Request(needVali = true)
	private String brokerageWorkId;// 经纪公司带看id
	@Request(needVali = true, strLenth = "[0,20]")
	private String bianhao;// 经纪公司内部房源编号，<=20位，字母数字均可
	@Request(needVali = false)
	private String customerId;// 客源id
	@Request(needVali = false)
	private Integer customerIdType;// 客源id类型 1.网络门店 3.经纪公司客源id
	@Request(needVali = false)
	private String customerName;// 客源姓名
	@Request(needVali = true)
	private String userId;// 带看经纪人id
	@Request(needVali = true)
	private Integer userIdType;// 带看经纪人id类型 1.网络门店 3.经纪公司
	@Request(needVali = true)
	private Long followTime;// 带看时间（10位时间戳）
	@Request(needVali = false)
	private String workContent;// 带看反馈（限400字符）

	/**
	 * 资源类型 1.二手房 2.租房
	 *
	 */
	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public Integer getBizType() {
		return bizType;
	}

	/**
	 * 经纪公司带看id
	 *
	 */
	public void setBrokerageWorkId(String brokerageWorkId) {
		this.brokerageWorkId = brokerageWorkId;
	}

	public String getBrokerageWorkId() {
		return brokerageWorkId;
	}

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
	 * 客源id
	 *
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerId() {
		return customerId;
	}

	/**
	 * 客源id类型 1.网络门店 3.经纪公司客源id
	 *
	 */
	public void setCustomerIdType(Integer customerIdType) {
		this.customerIdType = customerIdType;
	}

	public Integer getCustomerIdType() {
		return customerIdType;
	}

	/**
	 * 客源姓名
	 *
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerName() {
		return customerName;
	}

	/**
	 * 带看经纪人id
	 *
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	/**
	 * 带看经纪人id类型 1.网络门店 3.经纪公司
	 *
	 */
	public void setUserIdType(Integer userIdType) {
		this.userIdType = userIdType;
	}

	public Integer getUserIdType() {
		return userIdType;
	}

	/**
	 * 带看时间（10位时间戳）
	 *
	 */
	public void setFollowTime(Long followTime) {
		this.followTime = followTime;
	}

	public Long getFollowTime() {
		return followTime;
	}

	/**
	 * 带看反馈（限400字符）
	 *
	 */
	public void setWorkContent(String workContent) {
		this.workContent = workContent;
	}

	public String getWorkContent() {
		return workContent;
	}

}
