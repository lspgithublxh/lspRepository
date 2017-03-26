/**
 * 
 */
package com.yunhai.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.yunhai.model.UserEntity;
import com.yunhai.service.UserService;

/**
 * @author Administrator
 * 2017年3月26日
 * UserAction
 */
@Controller(value="userAction")
public class UserAction extends ActionSupport implements ServletRequestAware, ServletResponseAware{

	@Resource
	private UserService userService;
	
	private UserEntity user;
	
	private String message = "this is message!";
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	public String getUsers(){
		request.setAttribute("name", "lsp");
		request.setAttribute("user", userService.getUsers());
		return SUCCESS;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		
	}
	
	
	
}