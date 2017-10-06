package com.tools.spring_boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tools.spring_boot.dao.UserDao;
import com.tools.spring_boot.domain.AEntity;

@Service
public class AService implements IAService{

	@Autowired
	private UserDao userDao;
	
	@Override
	public void testData() {
		System.out.println("start test-----------");
		System.out.println("总数统计：" + userDao.count());
		List<AEntity> list = userDao.findAll();
		System.out.println("get list : " + list.toString());
	}

}
