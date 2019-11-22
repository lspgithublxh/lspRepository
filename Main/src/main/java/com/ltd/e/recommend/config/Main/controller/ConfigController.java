package com.ltd.e.recommend.config.Main.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ConfigController {

	@Value("${userName}")
    private String userName;
 
    @Value("${bookTitle}")
    private String bookTitle;
	
	 @GetMapping("/hello")
	 @ResponseBody
	public Object buyProductChannel(
	        @RequestParam(required=true) String token) {
		try {
			System.out.println("ddd");
		} catch (Exception e) {
			e.printStackTrace();
			return "sss";
		}
		return "ssss";
	}
	 
	 @GetMapping("/html")
	public String dd(
	        @RequestParam(required=true) String token) {
		try {
			System.out.println("ddd");
		} catch (Exception e) {
			e.printStackTrace();
			return "sss";
		}
		return "/index.html";
	}
	 
	 @RequestMapping("/")
	    public String index(ModelMap map) {
	        // 加入一个属性，用来在模板中读取
	        map.addAttribute("name", userName);
	        map.addAttribute("bookTitle", bookTitle);
	        // return模板文件的名称，对应src/main/resources/templates/welcome.html
	        return "welcome";
	    }

}
