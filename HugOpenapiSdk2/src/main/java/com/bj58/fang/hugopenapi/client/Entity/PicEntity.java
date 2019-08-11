package com.bj58.fang.hugopenapi.client.Entity;

import com.bj58.fang.hugopenapi.client.annotations.Request;

public class PicEntity {

	@Request(needVali = true, enumVal = "1,2,3")
	private String category;// 图片类型{1:室内图，2：户型图，3：室外图} --- 类型改变
	@Request(needVali = true)
	private String url;// 图片源地址
	@Request(needVali = true, enumVal = "0,1")
	private String iscover;// 是否为封面图{0:否，1：是}

	public String getUrl() {
		return url;
	}

	/**
	 * 图片源地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	public String getCategory() {
		return category;
	}

	/**
	 * 图片类型{1:室内图，2：户型图，3：室外图} --- 类型改变
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	public String getIscover() {
		return iscover;
	}

	/**
	 * 是否为封面图{0:否，1：是}
	 */
	public void setIscover(String iscover) {
		this.iscover = iscover;
	}

}
