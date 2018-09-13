package com.forwf.center.base.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.forwf.center.base.domain.User;

/**
 * 
 * @ClassName:HttpRequestController
 * @Description:
 * @Author lishaoping
 * @Date 2018年9月13日
 * @Version V1.0
 * @Package com.forwf.center.base.controller
 */
@RestController("/aa")
public class HttpRequestController {

	@RequestMapping("/hello")
	public String justReturn() {
		return "world, good!";
	};
//	
//	@RequestMapping("/user")
//	public User user() {
//		User user = new User("lsp", 26);
//		return user;
//	}
}
