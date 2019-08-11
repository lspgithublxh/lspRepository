package com.bj58.fang.hugopenapi.client.service.heyan.entity;

import com.bj58.fang.hugopenapi.client.annotations.Request;

public class HeyanEntity {

	@Request(needVali = true)
	private Long houseId;// 房源编号
	@Request(needVali = true)
	private Long brokerId;// 新三网经纪人编号
	@Request(needVali = true)
	private Integer districtCode;// 国家行政区划编码（例如330201，请查询国家行政区划接口）
	@Request(needVali = false)
	private String agencyName;// 经纪机构（必须和房屋状况说明书中经纪机构一致的）
	@Request(needVali = true)
	private String verificationCode;// 核验码（必须和房屋状况说明书一致的，例如：201610073302120003）
	@Request(needVali = true)
	private String entrustAgreementNo;// 出售委托编号（20161121330206000273001A）
	@Request(needVali = false)
	private String remark;// 备注信息

	/**
	 * 房源编号
	 *
	 */
	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}

	public Long getHouseId() {
		return houseId;
	}

	/**
	 * 新三网经纪人编号
	 *
	 */
	public void setBrokerId(Long brokerId) {
		this.brokerId = brokerId;
	}

	public Long getBrokerId() {
		return brokerId;
	}

	/**
	 * 国家行政区划编码（例如330201，请查询国家行政区划接口）
	 *
	 */
	public void setDistrictCode(Integer districtCode) {
		this.districtCode = districtCode;
	}

	public Integer getDistrictCode() {
		return districtCode;
	}

	/**
	 * 经纪机构（必须和房屋状况说明书中经纪机构一致的）
	 *
	 */
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getAgencyName() {
		return agencyName;
	}

	/**
	 * 核验码（必须和房屋状况说明书一致的，例如：201610073302120003）
	 *
	 */
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	/**
	 * 出售委托编号（20161121330206000273001A）
	 *
	 */
	public void setEntrustAgreementNo(String entrustAgreementNo) {
		this.entrustAgreementNo = entrustAgreementNo;
	}

	public String getEntrustAgreementNo() {
		return entrustAgreementNo;
	}

	/**
	 * 备注信息
	 *
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}

}
