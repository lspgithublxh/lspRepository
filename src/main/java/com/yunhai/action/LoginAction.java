/**
 * 
 */
package com.yunhai.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Administrator
 * 不注解loginAction则不会被扫描到！！！所以这个是扫描的意思。给spring用的。如果要给struts?可以吗？
 * 2017年3月26日
 * LoginAction
 */
@Controller(value="loginAction")
public class LoginAction extends ActionSupport implements ServletRequestAware, ServletResponseAware{

	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	private String username;
	
	private String password;
	
	public String login(){
		System.out.println(request.getAttribute("username") + "    --    " + username);
		return SUCCESS;
	}
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	
}
