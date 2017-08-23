package com.construct.business.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.construct.persistence.dao.IHUserDao;
import com.construct.persistence.entity.UserBean;

@Service("myuserService")
public class UserService implements IUserService{

	@Resource
	private IHUserDao userDao;
	@Override
	public List<UserBean> queryUsers() {
		
		return userDao.findAll();
	}
	public IHUserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(IHUserDao userDao) {
		this.userDao = userDao;
	}

	
}
