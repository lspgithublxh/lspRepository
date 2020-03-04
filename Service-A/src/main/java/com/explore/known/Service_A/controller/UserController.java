package com.explore.known.Service_A.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("usercontroller")
@RequestMapping("/api/service")
public class UserController {

	@GetMapping("/start/{user_id}")
	public String hello(@PathVariable("user_id")Long id) {
		return "this is a json data" + id;
	}
}
