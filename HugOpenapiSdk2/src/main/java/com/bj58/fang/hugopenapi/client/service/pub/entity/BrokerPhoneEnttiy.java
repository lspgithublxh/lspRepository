package com.bj58.fang.hugopenapi.client.service.pub.entity;

import com.bj58.fang.hugopenapi.client.annotations.Request;

public class BrokerPhoneEnttiy {

	@Request( needVali=true)
	private Long brokerid;//三网经纪人id
	@Request( needVali=false)
	private String clientPhone;//非小号所有者的号码，可筛选出经纪人与该号码的通话详情
	@Request( needVali=false)
	private String startTime;//开始时间，格式yyyy-MM-dd HH:mm:ss。
	@Request( needVali=false)
	private String endTime;//结束时间，格式yyyy-MM-dd HH:mm:ss。
	@Request( needVali=false)
	private String page;//页码。默认为1，最大为50
	@Request( needVali=false)
	private String size;//每次请求条数。默认10，最大为100；保证总条数最大为5000
	/**
	*三网经纪人id
	*
	 */
	public void setBrokerid(Long brokerid) {this.brokerid = brokerid;}
	 public Long getBrokerid() {return brokerid;}
	/**
	*非小号所有者的号码，可筛选出经纪人与该号码的通话详情
	*
	 */
	public void setClientPhone(String clientPhone) {this.clientPhone = clientPhone;}
	 public String getClientPhone() {return clientPhone;}
	/**
	*开始时间，格式yyyy-MM-dd HH:mm:ss。
	*
	 */
	public void setStartTime(String startTime) {this.startTime = startTime;}
	 public String getStartTime() {return startTime;}
	/**
	*结束时间，格式yyyy-MM-dd HH:mm:ss。
	*
	 */
	public void setEndTime(String endTime) {this.endTime = endTime;}
	 public String getEndTime() {return endTime;}
	/**
	*页码。默认为1，最大为50
	*
	 */
	public void setPage(String page) {this.page = page;}
	 public String getPage() {return page;}
	/**
	*每次请求条数。默认10，最大为100；保证总条数最大为5000
	*
	 */
	public void setSize(String size) {this.size = size;}
	 public String getSize() {return size;}


}
