package com.tools.spring_boot.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="boot_role4")
public class Role implements Serializable{

	private static final long serialVersionUID = 5560470129597334352L;
	@Id
	@GeneratedValue(generator = "system-uuid")  //浣跨敤uuid鐢熸垚涓婚敭鐨勬柟寮�  
	@GenericGenerator(name = "system-uuid", strategy = "uuid")   
	@Column(length=32) 
	private String id;
	
	@Column
	private String name;
	
//	@ManyToMany(cascade= {}, fetch=FetchType.EAGER)
//	@JoinTable(name="boot_user2",joinColumns= {@JoinColumn(name="user_id")}
//								)//inverseJoinColumns= {@JoinColumn(name="user_id")}
//	private List<AEntity> userList;
	private String userList;
	
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

	public String getUserList() {
		return userList;
	}

	public void setUserList(String userList) {
		this.userList = userList;
	}

	
	
	
}
