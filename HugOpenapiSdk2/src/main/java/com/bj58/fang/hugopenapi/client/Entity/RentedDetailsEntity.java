package com.bj58.fang.hugopenapi.client.Entity;

import com.bj58.fang.hugopenapi.client.annotations.Request;

/**
 * json转bean之后的验证
 * 
 * @ClassName:RentedDetailsEntity
 * @Description:
 * @Author lishaoping
 * @Date 2018年12月13日
 * @Version V1.0
 * @Package com.bj58.fang.hugopenapi.outentity
 */
public class RentedDetailsEntity {

	@Request(needVali = true, enumVal = "101,102,103")
	private String renterType;// 室友类型
	@Request(needVali = true)
	private String renterNum;// 人数
	@Request(needVali = false, enumVal = "201,202,203,204,205")
	private String renterAge;// 年龄
	@Request(needVali = false, enumVal = "0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31")
	private String renterWork;// 工作

	public String getRenterType() {
		return renterType;
	}

	/**
	 * 室友类型
	 */
	public void setRenterType(String renterType) {
		this.renterType = renterType;
	}

	public String getRenterNum() {
		return renterNum;
	}

	/**
	 * 人数
	 */
	public void setRenterNum(String renterNum) {
		this.renterNum = renterNum;
	}

	public String getRenterAge() {
		return renterAge;
	}

	/**
	 * 年龄
	 */
	public void setRenterAge(String renterAge) {
		this.renterAge = renterAge;
	}

	public String getRenterWork() {
		return renterWork;
	}

	/**
	 * 工作
	 */
	public void setRenterWork(String renterWork) {
		this.renterWork = renterWork;
	}

}
