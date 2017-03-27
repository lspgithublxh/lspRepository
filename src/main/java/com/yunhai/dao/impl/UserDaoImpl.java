/**
 * 
 */
package com.yunhai.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.yunhai.dao.UserDao;
import com.yunhai.model.UserEntity;

/**
 * @author Administrator
 * 2017年3月26日
 * UserDaoImpl
 */
@Transactional
@Repository(value="usersDao")
public class UserDaoImpl implements UserDao{

	@Resource(name="hibernateTemplate")//spring组件setter方法注入（观看顺序2）
    private HibernateTemplate hibernateTemplate;//hibernate4,5以后可以尝试
	@Override
	public List<UserEntity> getAllUser() {
		List user = hibernateTemplate.findByNamedQueryAndNamedParam("getAllUser", "name", "lsp");
		System.out.println(user);
		 List<?> list = hibernateTemplate.find("from UserEntity");
		 System.out.println(list);
		return (List<UserEntity>) list;
	}

}
