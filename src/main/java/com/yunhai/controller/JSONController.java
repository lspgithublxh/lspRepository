/**
 * 
 */
package com.yunhai.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yunhai.model.UserEntity;
import com.yunhai.service.UserService;
import com.yunhai.util.JsonUtil;

/**
 * @author Administrator
 * 2017年3月9日
 * VisitController
 */
@RestController
@RequestMapping("/json")
public class JSONController {

	public static final Logger LOGGER = LoggerFactory.getLogger(JSONController.class);
	
	 @Autowired
     private UserService myUserService;
	 
	@CacheEvict(value="accountCache",key="#user.getName()")
	@RequestMapping(value="/user", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String getObject(@RequestBody UserEntity user){
		LOGGER.info(user.getName());
		List<UserEntity> list = myUserService.getAllUsers();
		return JsonUtil.toJson(list);
	}
}
