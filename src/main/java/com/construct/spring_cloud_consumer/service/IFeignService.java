package com.construct.spring_cloud_consumer.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="service-hi")
public interface IFeignService {

	@RequestMapping(value = "/hi",method = RequestMethod.GET)
	public String hi(@RequestParam(value = "name") String name);
}
