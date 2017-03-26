/**
 * 
 */
package com.yunhai.model;

/**
 * @author Administrator
 *2017年3月5日
 *UserEntity
 */
public class UserEntity {

	private String name;
	private Integer id;
	private Integer age;
	private Double degree;
	private String home;
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
	/**
	 * @param name
	 * @param id
	 */
	public UserEntity(String name, Integer id) {
		super();
		this.name = name;
		this.id = id;
	}
	/**
	 * 
	 */
	public UserEntity() {
		super();
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Double getDegree() {
		return degree;
	}
	public void setDegree(Double degree) {
		this.degree = degree;
	}
	public String getHome() {
		return home;
	}
	public void setHome(String home) {
		this.home = home;
	}
	
	
}
