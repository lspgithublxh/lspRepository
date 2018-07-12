package com.bj58.fang;

import com.bj58.spat.scf.serializer.component.annotation.SCFMember;
import com.bj58.spat.scf.serializer.component.annotation.SCFSerializable;

@SCFSerializable(name="Wok")
public class Wok{

	@SCFMember(orderId=1)
	private String name;
	@SCFMember(orderId=2)
	private Integer id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
