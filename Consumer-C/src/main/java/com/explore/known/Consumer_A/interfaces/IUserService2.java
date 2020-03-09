package com.explore.known.Consumer_A.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.explore.known.Consumer_A.service.UserServiceHystrix;


/**
 * 接口形式来调用而不是RestTemplate, 集中在一起写对服务的调用
 * 
 * @author lishaoping
 * @date 2020年3月9日
 * @package  com.explore.known.Consumer_A.interfaces
 */
@FeignClient(value = "Service-B", fallback = UserServiceHystrix.class)
public interface IUserService2 {

	@RequestMapping("/api/serviceB/start/{id}")
	String getUserById(@PathVariable("id") Long id);
}
