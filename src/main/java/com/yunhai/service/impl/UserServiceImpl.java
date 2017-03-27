/**
 * 
 */
package com.yunhai.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yunhai.dao.UserDao;
import com.yunhai.model.UserEntity;
import com.yunhai.service.UserService;

/**
 * @author Administrator
 * 2017年3月26日
 * UserServiceImpl
 */
@Service(value="userService2")
public class UserServiceImpl implements UserService{

	@Resource
	private UserDao userDao;
	@Override
	public List<UserEntity> getUsers() {
		return userDao.getAllUser();
	}

}
