package com.explore.known.Consumer_A.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.explore.known.Consumer_A.interfaces.IUserService;
import com.explore.known.Consumer_A.interfaces.IUserService2;

/**
 * 异常处理：@ExceptionHandler
 * 入参检验：@Valid
 *
 * @author lishaoping
 * @date 2020年3月9日
 * @package  com.explore.known.Consumer_A.controller
 */
@RestController("UserController")
@RequestMapping("/api/consumer")
public class UserController {

	@Value("${server.port}")
	String port;

	@Autowired
	private IUserService userService;
	@Autowired
	private IUserService2 userService2;
//    public static  final String URL_PREFIX = "http://localhost:8001";
	public static final String URL_PREFIX = "http://Service-B";// 可以去掉端口，根据服务名访问

	@GetMapping("/start/{user_id}")
	public String hello(@PathVariable("user_id") Long id) {
		return userService.getUserById(id).toString();
	}
	
	@GetMapping("/start2/{user_id}")
	public String hello2(@PathVariable("user_id") Long id) {
		System.out.println("info");
		return userService2.getUserById(id).toString();
	}

	/**
	 * 方法统一的异常处理，而不是每个方法上进行捕获try-catch
	 * @param ex
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler
	public String doError(Exception ex) throws Exception {
		ex.printStackTrace();
		return ex.getMessage();
	}

}
