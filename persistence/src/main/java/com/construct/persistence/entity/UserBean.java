package com.construct.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.GenericGenerator;

//@Data
@Entity  
@Table(name="T_USER") 
public class UserBean {

	@Id  
    @GeneratedValue(generator = "system-uuid")  //使用uuid生成主键的方式  
	@GenericGenerator(name = "system-uuid", strategy = "uuid")   
    @Column(length=32) 
	private String id;
	
	@Column(length=32) 
	private String name;
	
	@Column(length=32) 
	private String address;
	
	@Column(length=32) 
	private String mobilephone;
	
	@Column(length=32) 
	private String sex;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	
	
}
