package com.construct.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.construct.persistence.entity.UserBean;
import com.construct.psersistence.dao.IHUserDao;

@Service("userService")
public class UserService implements IUserService{

	@Autowired
	private IHUserDao userDao;
	@Override
	public List<UserBean> queryUsers() {
		
		return userDao.findAll();
	}

	
}
