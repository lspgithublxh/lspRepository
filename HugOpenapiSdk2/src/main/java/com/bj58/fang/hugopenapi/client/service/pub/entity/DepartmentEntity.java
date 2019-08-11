package com.bj58.fang.hugopenapi.client.service.pub.entity;

import com.bj58.fang.hugopenapi.client.annotations.Request;

public class DepartmentEntity {

	@Request(needVali = true)
	private String brokerageDeptId;// 经纪公司部门id
	@Request(needVali = true)
	private String deptName;// 部门名称
	@Request(needVali = true)
	private Integer deptLevel;// 部门层级 0.未知 1.公司 2.大区 3.商圈 4.门店 5.组
	@Request(needVali = true)
	private String parentDeptId;// 上一级部门ID
	@Request(needVali = true)
	private Integer parentDeptIdType;// 上一级部门ID类型 1.网络门店 3.经济公司
	@Request(needVali = false)
	private Integer parentDeptLevel;// 上一级部门层级
	@Request(needVali = false)
	private String deptAddress;// 部门地址
	@Request(needVali = false)
	private String userId;// 负责人Id
	@Request(needVali = false)
	private Integer userIdType;// 负责人ID类型 1.网络门店用户id 3.经纪公司用户id

	/**
	 * 经纪公司部门id
	 *
	 */
	public void setBrokerageDeptId(String brokerageDeptId) {
		this.brokerageDeptId = brokerageDeptId;
	}

	public String getBrokerageDeptId() {
		return brokerageDeptId;
	}

	/**
	 * 部门名称
	 *
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptName() {
		return deptName;
	}

	/**
	 * 部门层级 0.未知 1.公司 2.大区 3.商圈 4.门店 5.组
	 *
	 */
	public void setDeptLevel(Integer deptLevel) {
		this.deptLevel = deptLevel;
	}

	public Integer getDeptLevel() {
		return deptLevel;
	}

	/**
	 * 上一级部门ID
	 *
	 */
	public void setParentDeptId(String parentDeptId) {
		this.parentDeptId = parentDeptId;
	}

	public String getParentDeptId() {
		return parentDeptId;
	}

	/**
	 * 上一级部门ID类型 1.网络门店 3.经济公司
	 *
	 */
	public void setParentDeptIdType(Integer parentDeptIdType) {
		this.parentDeptIdType = parentDeptIdType;
	}

	public Integer getParentDeptIdType() {
		return parentDeptIdType;
	}

	/**
	 * 上一级部门层级
	 *
	 */
	public void setParentDeptLevel(Integer parentDeptLevel) {
		this.parentDeptLevel = parentDeptLevel;
	}

	public Integer getParentDeptLevel() {
		return parentDeptLevel;
	}

	/**
	 * 部门地址
	 *
	 */
	public void setDeptAddress(String deptAddress) {
		this.deptAddress = deptAddress;
	}

	public String getDeptAddress() {
		return deptAddress;
	}

	/**
	 * 负责人Id
	 *
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	/**
	 * 负责人ID类型 1.网络门店用户id 3.经纪公司用户id
	 *
	 */
	public void setUserIdType(Integer userIdType) {
		this.userIdType = userIdType;
	}

	public Integer getUserIdType() {
		return userIdType;
	}

}
