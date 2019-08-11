package com.bj58.fang.hugopenapi.client.service.pub.entity;

import com.bj58.fang.hugopenapi.client.annotations.Request;

public class ConfigEntity {

	@Request( needVali=true, strLenth="[0,20]")
	private String bianhao;//经纪公司内部房源编号，<=20位，字母数字均可
	@Request( needVali=true)
	private String brokerids;//三网经纪人用户id列表
	@Request( needVali=true, enumVal="1,2")
	private String plats;//发布平台
	@Request( needVali=true, enumVal="11,12,13")
	private int cateid;//业务类别
	public String getBianhao() {
		return bianhao;
	}
	public void setBianhao(String bianhao) {
		this.bianhao = bianhao;
	}
	public String getBrokerids() {
		return brokerids;
	}
	
	/**
	 * 多个brokerid时，用|分割，比如122|12|12
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月14日
	 * @Package com.bj58.fang.hugopenapi.client.annoService.pub.entity
	 * @return void
	 */
	public void setBrokerids(String brokerids) {
		this.brokerids = brokerids;
	}
	public String getPlats() {
		return plats;
	}
	public void setPlats(String plats) {
		this.plats = plats;
	}
	public int getCateid() {
		return cateid;
	}
	public void setCateid(int cateid) {
		this.cateid = cateid;
	}
	
}
