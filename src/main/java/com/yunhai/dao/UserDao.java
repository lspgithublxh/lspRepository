/**
 * 
 */
package com.yunhai.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yunhai.model.UserEntity;

/**
 * @author Administrator
 * 2017年3月19日
 * UserDao
 */
@Repository
public interface UserDao {

	List<UserEntity> getAllUser();
}
