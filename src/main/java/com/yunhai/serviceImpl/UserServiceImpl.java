/**
 * 
 */
package com.yunhai.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunhai.dao.UserDao;
import com.yunhai.model.UserEntity;
import com.yunhai.service.UserService;

/**
 * 
 * @author Administrator
 * 2017年3月5日
 * UserServiceImpl
 */
@Service("myUserService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	public List<UserEntity> getAllUsers() {
		List<UserEntity> list = new ArrayList<UserEntity>();
		UserEntity user = new UserEntity("李少平",1);
		UserEntity user2 = new UserEntity("李小海",1);
		list.add(user);
		list.add(user2);
		List<UserEntity> list2 = userDao.getAllUser();
		return list2;
	}

}
