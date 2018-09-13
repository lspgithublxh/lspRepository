package com.forwf.center.base.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/bb")//这里的bb无效
public class WebController {

	@RequestMapping("/abc")
	public String aa(HttpSession session, HttpServletRequest request) {
		Map<String, String> m = new HashMap<String, String>();
		m.put("ss", "xxx");
		return net.minidev.json.JSONObject.toJSONString(m);
	}
	
}
