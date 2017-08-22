package com.construct.base;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.construct.business.services.IUserService;
import com.construct.persistence.entity.UserBean;
import com.construct.psersistence.dao.IHUserDao;

@Controller
@RequestMapping("/user")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	 
	@Autowired
    private IUserService myuserService;
	
	
	@RequestMapping(value = "/page2", method = RequestMethod.POST)
    public void getPage2(HttpServletResponse response , HttpServletRequest request) throws IOException{
   	 	LOGGER.info("::");
        System.out.println("-----------------");
        List<UserBean> result = myuserService.queryUsers();
        System.out.println(result);
        response.sendRedirect("./view/jsp/index.jsp");
    }
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(HttpServletResponse response , HttpServletRequest request) throws IOException{
   	 	LOGGER.info("::");
        System.out.println("-----------------");
        List<UserBean> result = myuserService.queryUsers();
        System.out.println(result);
        response.sendRedirect("./view/jsp/index.jsp");
    }
	
	public String findUser(){
		return "success";
	}

	public IUserService getUserService() {
		return myuserService;
	}

	public void setUserService(IUserService userService) {
		this.myuserService = userService;
	}

	public IUserService getMyuserService() {
		return myuserService;
	}

	public void setMyuserService(IUserService myuserService) {
		this.myuserService = myuserService;
	}

	
	
}
