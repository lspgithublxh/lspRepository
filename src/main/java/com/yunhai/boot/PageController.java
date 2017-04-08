/**
 * 
 */
package com.yunhai.boot;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lishaoping
 * 2017年4月8日上午11:51:58
 * PageControll
 */
@Controller
@RequestMapping(value="/page")
public class PageController {

	@RequestMapping(value="/index/{name}")
	public String hello(@PathVariable("name") String name, Model model){
		model.addAttribute("name", name);
		return "index";
	}
}
