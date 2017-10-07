package com.construct.spring_cloud_consumer.service;

import org.springframework.stereotype.Component;

@Component
public class HystricFeinService implements IFeignService{

	@Override
	public String hi(String name) {
		return "hi, " + name + ", service is down";
	}

}
