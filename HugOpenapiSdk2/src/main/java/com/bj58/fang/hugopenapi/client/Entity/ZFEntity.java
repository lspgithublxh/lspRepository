package com.bj58.fang.hugopenapi.client.Entity;

import java.util.List;

import com.bj58.fang.hugopenapi.client.annotations.Request;

public class ZFEntity extends HouseEntity {

	@Request(needVali = false)
	private Integer mendianid;// 门店id
	@Request(needVali = true, enumVal = "1,2")
	private Integer rentType;// 出租类型
	@Request(needVali = false, timeFormat = "yyyy-MM-dd")
	private String earliestMoveInTime;// 最早入住时间 格式yy-mm-dd
	@Request(needVali = true, enumVal = "1,1,1,2,1,2,3,2,1,4,2,2,5,3,1,6,3,2,7,8,9,10,1,11,12,13,1")
	private Integer paymentTerms;// 付款方式
	@Request(needVali = false, enumVal = "1,2,3,4,5,6,7,8,9,10,11,12,13")
	private String supportFacilities;// 公共使用配套
	@Request(needVali = false, enumVal = "1,2,3,4,5,6,7,8,9,10,11,12,13")
	private String independFacilities;// 出租间使用配套(合租字段)
	@Request(needVali = false)
	private Integer jointTenantNum;// 合租户数(合租字段)
	@Request(needVali = true, enumVal = "1,2")
	private Integer roomType;// 房间类型(合租字段)
	@Request(needVali = true, numBetween = "[5,5000]")
	private Double buildingArea;// 合租的建筑面积 必须为数字，限制[5,5000] (合租字段)
	@Request(needVali = false, enumVal = "1,2,3,4")
	private Integer jtTenRestrict;// 限制室友类型(合租字段)
	@Request(needVali = false, numBetween = "[0,9)&[0,jointTenantNum)")
	private Integer rentedHouseNum;// 已出租户数(合租字段) 必须为整数，限制[0,9)，需＜房屋情况(合租户数)，当用户已经输入出租室友情况多于已出租户数时，从下方隐藏多余的已出租户数
	@Request(needVali = false, strLenth = "(0,1024]", jsonVali = "RentedDetailsEntity", isJsonArray = true)
	private String rentedDetails;// 已出租室友类型 (json数组格式,格式即枚举值见下已出租室友类型格式说明,长度限制<1024字符)(合租字段)
	@Request(needVali = true, numBetween = "[100,400000]", override = true)
	private Long jiage;// 总价，单位：元

	private List<RentedDetailsEntity> rentedDetailsList;//方便填写rentedDetails
	
	public Long getJiage() {
		return jiage;
	}

	/**
	 * 总价，单位：元
	 */
	public void setJiage(Long jiage) {
		this.jiage = jiage;
	}

	public Integer getMendianid() {
		return mendianid;
	}

	/**
	 * 门店id
	 */
	public void setMendianid(Integer mendianid) {
		this.mendianid = mendianid;
	}

	public Integer getRentType() {
		return rentType;
	}

	/**
	 * 出租类型
	 */
	public void setRentType(Integer rentType) {
		this.rentType = rentType;
	}

	public String getEarliestMoveInTime() {
		return earliestMoveInTime;
	}

	/**
	 * 最早入住时间 格式yy-mm-dd
	 */
	public void setEarliestMoveInTime(String earliestMoveInTime) {
		this.earliestMoveInTime = earliestMoveInTime;
	}

	public Integer getPaymentTerms() {
		return paymentTerms;
	}

	/**
	 * 付款方式
	 */
	public void setPaymentTerms(Integer paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getSupportFacilities() {
		return supportFacilities;
	}

	/**
	 * 公共使用配套
	 */
	public void setSupportFacilities(String supportFacilities) {
		this.supportFacilities = supportFacilities;
	}

	public String getIndependFacilities() {
		return independFacilities;
	}

	/**
	 * 出租间使用配套(合租字段)
	 */
	public void setIndependFacilities(String independFacilities) {
		this.independFacilities = independFacilities;
	}

	public Integer getJointTenantNum() {
		return jointTenantNum;
	}

	/**
	 * 合租户数(合租字段)
	 */
	public void setJointTenantNum(Integer jointTenantNum) {
		this.jointTenantNum = jointTenantNum;
	}

	public Integer getRoomType() {
		return roomType;
	}

	/**
	 * 房间类型(合租字段)
	 */
	public void setRoomType(Integer roomType) {
		this.roomType = roomType;
	}

	public Double getBuildingArea() {
		return buildingArea;
	}

	/**
	 * 合租的建筑面积 必须为数字，限制[5,5000] (合租字段)
	 */
	public void setBuildingArea(Double buildingArea) {
		this.buildingArea = buildingArea;
	}

	public Integer getJtTenRestrict() {
		return jtTenRestrict;
	}

	/**
	 * 限制室友类型(合租字段)
	 */
	public void setJtTenRestrict(Integer jtTenRestrict) {
		this.jtTenRestrict = jtTenRestrict;
	}

	public Integer getRentedHouseNum() {
		return rentedHouseNum;
	}

	/**
	 * 已出租户数(合租字段) 必须为整数，限制[0,9)，需＜房屋情况(合租户数)，当用户已经输入出租室友情况多于已出租户数时，从下方隐藏多余的已出租户数
	 */
	public void setRentedHouseNum(Integer rentedHouseNum) {
		this.rentedHouseNum = rentedHouseNum;
	}

	public String getRentedDetails() {
		return rentedDetails;
	}

	/**
	 * 已出租室友类型 (json数组格式,格式即枚举值见下已出租室友类型格式说明,长度限制<1024字符)(合租字段)
	 */
	public void setRentedDetails(String rentedDetails) {
		this.rentedDetails = rentedDetails;
	}

	public List<RentedDetailsEntity> getRentedDetailsList() {
		return rentedDetailsList;
	}

	/**
	 * 已出租室友类型 (json数组格式,格式即枚举值见下已出租室友类型格式说明,长度限制<1024字符)(合租字段)
	 */
	public void setRentedDetailsList(List<RentedDetailsEntity> rentedDetailsList) {
		StringBuilder builder = new StringBuilder();
		if(rentedDetailsList != null && rentedDetailsList.size() > 0) {
			builder.append("[");
			for(RentedDetailsEntity entity : rentedDetailsList) {
				builder.append(String.format("{\"renterType\":\"%s\",\"renterNum\":\"%s\",\"renterAge\":\"%s\",\"renterWork\":\"%s\"},", 
						entity.getRenterType(), entity.getRenterNum(), entity.getRenterAge(), entity.getRenterWork()));
			}
			builder.replace(builder.length() - 1, builder.length(), "]");//逗号替换为]
		}
		this.rentedDetails = builder.toString();
		this.rentedDetailsList = rentedDetailsList;
	}
	
	

}
