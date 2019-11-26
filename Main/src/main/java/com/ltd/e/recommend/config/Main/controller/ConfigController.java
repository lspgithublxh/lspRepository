package com.ltd.e.recommend.config.Main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.ltd.e.recommend.config.Main.model.RecommendConfig;
import com.ltd.e.recommend.config.Main.service.ConfigService;
/**
 * 配置controller
 * TODO 
 * @author lishaoping
 * @date 2019年11月25日
 * @file ConfigController
 */
@Controller
public class ConfigController {

	@Value("${userName}")
	private String userName;

	@Value("${bookTitle}")
	private String bookTitle;

	@Autowired
	private ConfigService configService;

	@GetMapping("/update")
	@ResponseBody
	public String dd(@RequestParam(required = true) String token) {
		try {
			RecommendConfig config = new Gson().fromJson(token, RecommendConfig.class);
			configService.saveRecommendConfig(config);
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
		return "success";
	}

	@RequestMapping("/config")
	public String findConfigByExamType(ModelMap map,@RequestParam(required = true) String examTypeId) {
		try {
			// 加入一个属性，用来在模板中读取
			map.addAttribute("name", userName);
			map.addAttribute("bookTitle", bookTitle);
			RecommendConfig config = configService.queryRcByExamType(Short.parseShort(examTypeId));
			// return模板文件的名称，对应src/main/resources/templates/welcome.html
			map.addAttribute("config", config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "welcome";
	}
}
