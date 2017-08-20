package com.construct.psersistence.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

	List<Map> queryList();
}
