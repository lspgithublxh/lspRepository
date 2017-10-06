package com.tools.spring_boot.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="boot_dept2")
public class Department implements Serializable{

	private static final long serialVersionUID = -6105455036933510814L;

	@Id
	@GeneratedValue(generator = "system-uuid")  //浣跨敤uuid鐢熸垚涓婚敭鐨勬柟寮�  
	@GenericGenerator(name = "system-uuid", strategy = "uuid")   
	@Column(length=32) 
	private String id;
	
	@Column
	private String name;

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
	
}
