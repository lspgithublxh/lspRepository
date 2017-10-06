package com.tools.spring_boot.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tools.spring_boot.domain.AEntity;

/**
 * 这个jpaRepository会检查查询字段是否在bean里，所以方法名不是随便写的，越多的方便，越多的规范。越规范越方便
 *
 *@author lishaoping
 *spring_boot
 *2017年10月6日
 */
public interface UserDao extends JpaRepository<AEntity, String>{
	AEntity findByLoginNameLike(String name);

	AEntity readByLoginName(String name);

	List<AEntity> getByCreatedateLessThan(Date star);
}
