package com.bj58.reflect;

public class FFFchai {

	private Integer age;
	private String name;
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getName() {
		for(int i = 0; i < 100; i++) {
			int j = i * 2;
			System.out.println(j);
		}
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
