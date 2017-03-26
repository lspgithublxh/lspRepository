/**
 * 
 */
package com.yunhai.dao;

import java.util.List;

import com.yunhai.model.UserEntity;

/**
 * @author Administrator
 * 2017年3月26日
 * UserDao
 */
public interface UserDao {

	List<UserEntity> getAllUser();
}
