package com.construct.business.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.construct.persistence.common.IHibernateDao;
import com.construct.persistence.dao.IHUserDao;
import com.construct.persistence.entity.UserBean;
import com.construct.persistence.entity.UserRoles;

@Service("myuserService")
public class UserService implements IUserService{

	@Resource
	private IHUserDao userDao;
	@Autowired
	private IHibernateDao hibernateDao;
	
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
	@Override
	public List<UserRoles> queryUserRoles() {//
		Map<String, String> param = new HashMap<>();
		param.put("name", "Tom");
//		List<UserRoles> rs2 = hibernateDao.queryForListByNamedQuery("user_roles.query1", param, UserRoles.class);

		List<UserRoles> rs = hibernateDao.queryForListByNamedQuery("com.construct.persistence.entity.UserRoles.getUserByName_", param, UserRoles.class);
		System.out.println(rs);
		List<UserRoles> result = hibernateDao.queryForListByNamedQuery("getUserRolesBySql", param, UserRoles.class);
		System.out.println(result);
		return result;
	}

	
}
