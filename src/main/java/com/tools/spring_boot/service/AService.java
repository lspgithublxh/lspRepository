package com.tools.spring_boot.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.tools.spring_boot.dao.UserDao;
import com.tools.spring_boot.domain.AEntity;
import com.tools.spring_boot.mapper.AMapper;

@Service
public class AService implements IAService{

	@Autowired
	private UserDao userDao;
	
	@Autowired
    private AMapper mapper;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void testData() {
		//1.jpa方式
		System.out.println("start test-----------");
		System.out.println("总数统计：" + userDao.count());
		List<AEntity> list = userDao.findAll();
		System.out.println("get list : " + list.toString());
		//2.jdbc方式
		String sql = "select * from boot_user4";
		List<Map<String, Object>> list2 = jdbcTemplate.queryForList(sql);
		System.out.println(list2);
		//3.mybatis方式
		List<AEntity> result = mapper.selectAll();
		System.out.println(list);
	}

}
