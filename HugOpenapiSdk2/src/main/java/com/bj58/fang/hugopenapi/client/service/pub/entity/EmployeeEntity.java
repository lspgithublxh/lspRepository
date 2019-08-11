package com.bj58.fang.hugopenapi.client.service.pub.entity;

import com.bj58.fang.hugopenapi.client.annotations.Request;

public class EmployeeEntity {

	@Request(needVali = true)
	private String accountId;// 经济公司用户ID
	@Request(needVali = false)
	private Long brokerId;// 三网经纪人id
	@Request(needVali = true)
	private String accountName;// 账号名
	@Request(needVali = false)
	private String password;// 密码（不填写密码系统将自动生成）
	@Request(needVali = true)
	private String trueName;// 用户真实姓名
	@Request(needVali = false)
	private String deptId;// 部门ID
	@Request(needVali = false)
	private Integer deptIdType;// 部门ID类型 1.网络门店 3.经纪公司部门id
	@Request(needVali = false)
	private Integer brokerageDeptLevel;// 经纪公司部门层级
	@Request(needVali = true)
	private Integer positionId;// 职位ID(职位id 1.管理员 2.大区经理 3.区域经理 4.店长 5.组长 6.店秘书 7.经纪人 8.实习经纪人 10.店东)
	@Request(needVali = true)
	private String phone;// 联系方式(手机号，不支持座机号)
	@Request(needVali = true)
	private String entryTime;// 入职时间(10位unix时间戳)

	/**
	 * 经济公司用户ID
	 *
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAccountId() {
		return accountId;
	}

	/**
	 * 三网经纪人id
	 *
	 */
	public void setBrokerId(Long brokerId) {
		this.brokerId = brokerId;
	}

	public Long getBrokerId() {
		return brokerId;
	}

	/**
	 * 账号名
	 *
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountName() {
		return accountName;
	}

	/**
	 * 密码（不填写密码系统将自动生成）
	 *
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	/**
	 * 用户真实姓名
	 *
	 */
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getTrueName() {
		return trueName;
	}

	/**
	 * 部门ID
	 *
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptId() {
		return deptId;
	}

	/**
	 * 部门ID类型 1.网络门店 3.经纪公司部门id
	 *
	 */
	public void setDeptIdType(Integer deptIdType) {
		this.deptIdType = deptIdType;
	}

	public Integer getDeptIdType() {
		return deptIdType;
	}

	/**
	 * 经纪公司部门层级
	 *
	 */
	public void setBrokerageDeptLevel(Integer brokerageDeptLevel) {
		this.brokerageDeptLevel = brokerageDeptLevel;
	}

	public Integer getBrokerageDeptLevel() {
		return brokerageDeptLevel;
	}

	/**
	 * 职位ID(职位id 1.管理员 2.大区经理 3.区域经理 4.店长 5.组长 6.店秘书 7.经纪人 8.实习经纪人 10.店东)
	 *
	 */
	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public Integer getPositionId() {
		return positionId;
	}

	/**
	 * 联系方式(手机号，不支持座机号)
	 *
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}

	/**
	 * 入职时间(10位unix时间戳)
	 *
	 */
	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}

	public String getEntryTime() {
		return entryTime;
	}

}
