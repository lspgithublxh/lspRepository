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

	public String name;
	public Integer id;
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
	
	
}
