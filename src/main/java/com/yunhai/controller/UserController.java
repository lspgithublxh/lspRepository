/**
 * 
 */
package com.yunhai.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.yunhai.model.UserEntity;
import com.yunhai.service.UserService;

/**
 * @author Administrator
 *2017年3月5日
 *UserController
 */
@Controller
@RequestMapping("/user")
public class UserController {
 
	 private String name;
     private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
 
     @Autowired
     private UserService userService;
 
     @RequestMapping(value = "/all", method = RequestMethod.POST ,produces="application/json;charset=UTF-8")//不是必须的：
     @ResponseBody//也不用配置convertor-。。如果输入参数是单一字符串
     public String getAllUsers(@RequestBody UserEntity user){
    	 LOGGER.info("::" + user.getName());
         LOGGER.info("[GET] /user/all getAllUsers");
         List<UserEntity> list = userService.getAllUsers();
         LOGGER.debug("This is log of level of debug");
         LOGGER.trace("log4j2 Demo");
         LOGGER.error("哎呀，出错啦~");
         return new Gson().toJson(list);//"{name:'sss',id:8}";
     }
     
     @RequestMapping(value = "/name", method = RequestMethod.POST)//不是必须的：,produces="application/json;charset=UTF-8"
     @ResponseBody//也不用配置convertor-。。如果输入参数是单一字符串
     public String getA(String name){
    	 LOGGER.info("::" + name);
         LOGGER.info("[GET] /user/all getAllUsers");
         List<UserEntity> list = userService.getAllUsers();
         LOGGER.debug("This is log of level of debug");
         LOGGER.trace("log4j2 Demo");
         LOGGER.error("哎呀，出错啦~");
         return new Gson().toJson(list);//"{name:'sss',id:8}";
     }
     
     @RequestMapping(value = "/page", method = RequestMethod.POST)
     public String getPage(String name){
    	 LOGGER.info("::" + name);
         LOGGER.info("[GET] /user/all getAllUsers");
         List<UserEntity> list = userService.getAllUsers();
         LOGGER.debug("This is log of level of debug");
         LOGGER.trace("log4j2 Demo");
         LOGGER.error("哎呀，出错啦~");
         return "inde";
     }
     
    public static void main(String[] args) {
		ApplicationContext app = new ClassPathXmlApplicationContext("spring-core.xml");//是classpath下面
		UserController con = app.getBean(UserController.class);
		System.out.println(con.getName());
		LOGGER.error("error occurence :" + System.currentTimeMillis());
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
     
     
}
