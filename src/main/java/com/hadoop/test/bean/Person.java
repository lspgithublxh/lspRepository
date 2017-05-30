/**
 * 
 */
package com.hadoop.test.bean;

/**
 * @author lishaoping
 * 2017年5月30日上午10:06:53
 * Persion
 */
public class Person {
 
	private String name;
	private Integer age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	/**
	 * @param name
	 * @param age
	 */
	public Person(String name, Integer age) {
		super();
		this.name = name;
		this.age = age;
	}
	
	
}
