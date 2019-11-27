package com.ltd.e.recommend.config.Main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ltd.e.recommend.config.Main.model.StaticsItem;
import com.ltd.e.recommend.config.Main.service.StaticsService;

/**
 * 
 * TODO 
 * @author lishaoping
 * @date 2019年11月27日
 * @file UserKnowledgePointCompleteController
 */
@Controller
public class UserKnowledgePointCompleteController {

	@Autowired
	private StaticsService staticsService;
	
	@RequestMapping("/statis")
	@ResponseBody
	public List<StaticsItem> findConfigByExamType(ModelMap map,@RequestParam(required = true) String examTypeId) {
		try {
			// 加入一个属性，用来在模板中读取
//			RecommendConfig config = configService.queryRcByExamType(Short.parseShort(examTypeId));
			// return模板文件的名称，对应src/main/resources/templates/welcome.html
//			map.addAttribute("config", config);
			List<StaticsItem> data = staticsService.getPriority(108, Short.parseShort(examTypeId));
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/statisPage")
	public String page(ModelMap map,@RequestParam(required = true) String examTypeId) {
		try {
			// 加入一个属性，用来在模板中读取
//			RecommendConfig config = configService.queryRcByExamType(Short.parseShort(examTypeId));
			// return模板文件的名称，对应src/main/resources/templates/welcome.html
//			map.addAttribute("config", config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "statics";
	}
}
