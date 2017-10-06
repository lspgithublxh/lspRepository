package com.tools.spring_boot.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="boot_user4")
public class AEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "system-uuid")  //浣跨敤uuid鐢熸垚涓婚敭鐨勬柟寮�  
	@GenericGenerator(name = "system-uuid", strategy = "uuid")   
	@Column(length=32) 
	private String id;
	
	@Column(length=32,nullable=false)
	private String name;
	
	private String loginName;
	
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
	private Date createdate;

//	@ManyToOne
//	@JoinColumn(name="did")
//	@JsonBackReference
	private String deptId;
	
//	@ManyToMany(cascade= {}, fetch=FetchType.EAGER)
//	@JoinTable(name="boot_role2",joinColumns= {@JoinColumn(name="roles_id")}
//								)//inverseJoinColumns={@JoinColumn(name="roles_id")}
//	private List<Role> roleList;
	private String roleList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}


	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getRoleList() {
		return roleList;
	}

	public void setRoleList(String roleList) {
		this.roleList = roleList;
	}


	
	
}
