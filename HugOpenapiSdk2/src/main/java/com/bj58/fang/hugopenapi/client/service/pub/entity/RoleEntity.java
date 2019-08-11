package com.bj58.fang.hugopenapi.client.service.pub.entity;

import com.bj58.fang.hugopenapi.client.annotations.Request;

public class RoleEntity {

	@Request(needVali = true, strLenth = "[0,20]")
	private String bianhao;// 经纪公司内部房源编号，<=20位，字母数字均可
	@Request(needVali = true)
	private Integer userIdType;// 用户id类型 1.网络门店 3.经纪公司
	@Request(needVali = false)
	private String inputPerson;// 录入人账号id
	@Request(needVali = false)
	private String guardian;// 维护人账号id
	@Request(needVali = false)
	private String survey;// 实勘人账号id
	@Request(needVali = false)
	private String promotion;// 委托人账号id
	@Request(needVali = false)
	private String key;// 钥匙人账号id

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
	 * 录入人账号id
	 *
	 */
	public void setInputPerson(String inputPerson) {
		this.inputPerson = inputPerson;
	}

	public String getInputPerson() {
		return inputPerson;
	}

	/**
	 * 维护人账号id
	 *
	 */
	public void setGuardian(String guardian) {
		this.guardian = guardian;
	}

	public String getGuardian() {
		return guardian;
	}

	/**
	 * 实勘人账号id
	 *
	 */
	public void setSurvey(String survey) {
		this.survey = survey;
	}

	public String getSurvey() {
		return survey;
	}

	/**
	 * 委托人账号id
	 *
	 */
	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}

	public String getPromotion() {
		return promotion;
	}

	/**
	 * 钥匙人账号id
	 *
	 */
	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
