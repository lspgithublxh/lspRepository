package com.bj58.fang.hugopenapi.client.Entity;

import com.bj58.fang.hugopenapi.client.annotations.Request;
import com.bj58.fang.hugopenapi.client.annotations.UpdateRequest;

/**
 * update的时候，字段再加，且只验证部分字段---或者忽视某些验证结果信息
 * 有@Request才传，有@UpdateRequest的更新时才传--才封装到map里：：okhttp更强更好！！！
 * 
 * @ClassName:BrokerESFEntity
 * @Description:
 * @Author lishaoping
 * @Date 2018年12月13日
 * @Version V1.0
 * @Package com.bj58.fang.hugopenapi.outentity
 */
public class BrokerESFEntity extends ESFEntity {

	@UpdateRequest(needVali = true)
	private Long houseid;// 三网标准帖子id ---更新用
	@Request(needVali = true)
	private Long brokerid;// 三网经纪人用户id
	@Request(needVali = false, enumVal = "1,2")
	private String plats;// 发布平台
	@Request(needVali = false, enumVal = "1,2")
	private Integer houseNumFromCo;// 楼栋门牌号是否来源于企业

	public Long getBrokerid() {
		return brokerid;
	}

	/**
	 * 三网经纪人用户id
	 */
	public void setBrokerid(Long brokerid) {
		this.brokerid = brokerid;
	}

	public String getPlats() {
		return plats;
	}

	/**
	 * 发布平台
	 */
	public void setPlats(String plats) {
		this.plats = plats;
	}

	public Integer getHouseNumFromCo() {
		return houseNumFromCo;
	}

	/**
	 * 楼栋门牌号是否来源于企业
	 */
	public void setHouseNumFromCo(Integer houseNumFromCo) {
		this.houseNumFromCo = houseNumFromCo;
	}
}
