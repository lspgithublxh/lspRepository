package com.explore.known.Consumer_A.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 要这个请求客户端 ， 配置服务器才会去获取配置文件----实际上也是被动的：不如直接通知：/actuator/bus-refresh?destination=customers:**
 * 
 *
 * @author lishaoping
 * @date 2020年3月9日
 * @package  com.explore.known.Consumer_A.controller
 */
@RestController("UserController")
@RequestMapping("/api/consumer")
@RefreshScope
public class UserController {

	/**
	 * 从配置中心获取的配置
	 */
	@Value("${foo}")
	String foo;
	@Autowired
	private RestTemplate template;
    public static  final String URL_PREFIX = "http://localhost:8001";

	
	@GetMapping("/start/{user_id}")
	public String hello(@PathVariable("user_id") Long id) {
		return template.getForObject(URL_PREFIX + "/api/service/start/" + id, String.class);
	}
	
	@GetMapping("/hi/{user_id}")
	public String hi(@PathVariable("user_id") Long id) {
		return "foo:" + foo;
	}
}
