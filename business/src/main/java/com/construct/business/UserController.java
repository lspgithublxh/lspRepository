package com.construct.business;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.construct.persistence.entity.UserBean;
import com.construct.services.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	 
	@Autowired
    private IUserService userService;
	
	@RequestMapping(value = "/page2", method = RequestMethod.POST)
    public String getPage2(){
   	 	LOGGER.info("::");
        System.out.println("-----------------");
        List<UserBean> result = userService.queryUsers();
        System.out.println(result);
        return "index";
    }
	
	public String findUser(){
		return "success";
	}
}
