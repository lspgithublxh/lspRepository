package com.bj58.fang.hugopenapi.client.Entity;

import com.bj58.fang.hugopenapi.client.annotations.Request;

public class CompanyESFEntity extends ESFEntity {

	@Request(needVali = false)
	private Integer mendianid;// 门店id 废弃

	public Integer getMendianid() {
		return mendianid;
	}

	/**
	 * 门店id 废弃
	 */
	public void setMendianid(Integer mendianid) {
		this.mendianid = mendianid;
	}

}
