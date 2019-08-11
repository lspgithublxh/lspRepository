package com.bj58.jdkjinspecial.atomic;

public class Entity {

	private String name = "haha";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Entity(String name) {
		super();
		this.name = name;
	}

	@Override
	public String toString() {
		return "Entity [name=" + name + "]";
	}
	
	
}
