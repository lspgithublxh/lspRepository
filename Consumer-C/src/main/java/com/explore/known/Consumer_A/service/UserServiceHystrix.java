package com.explore.known.Consumer_A.service;

import org.springframework.stereotype.Component;

import com.explore.known.Consumer_A.interfaces.IUserService2;

@Component
public class UserServiceHystrix implements IUserService2{

	@Override
	public String getUserById(Long id) {
		return "error " + id;
	}

}
