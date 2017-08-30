package com.construct.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * 
 *
 * @author lishaoping
 *persistence
 *2017骞�8鏈�26鏃�
 */
@Entity  
@Table(name="USER_ROLES") 
@NamedQueries({
	@NamedQuery(name="user_roles.query1",query="from UserRoles where username = :name"),
	@NamedQuery(name="user_roles.query2",query="from UserRoles where username = :name"),
	@NamedQuery(name="user_roles.query3",query="select * from user_roles where username = :name")
})
public class UserRoles {
	@Id  
    @GeneratedValue(generator = "system-uuid")  //浣跨敤uuid鐢熸垚涓婚敭鐨勬柟寮�  
	@GenericGenerator(name = "system-uuid", strategy = "uuid")   
    @Column(length=32) 
	private String id;
	private String username;
	private String role;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
}
