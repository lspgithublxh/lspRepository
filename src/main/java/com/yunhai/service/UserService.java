/**
 * 
 */
package com.yunhai.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yunhai.model.UserEntity;

/**
 * @author Administrator
 *2017年3月5日
 *UserService
 */

public interface UserService {
	
	public List<UserEntity> getAllUsers();
}
