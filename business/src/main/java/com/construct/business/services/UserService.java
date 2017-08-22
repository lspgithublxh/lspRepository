package com.construct.business.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.construct.persistence.entity.UserBean;
import com.construct.psersistence.dao.IHUserDao;

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
