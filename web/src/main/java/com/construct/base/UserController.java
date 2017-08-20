package com.construct.base;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	 	
	@RequestMapping(value = "/page2", method = RequestMethod.POST)
    public void getPage2(HttpServletRequest request, HttpServletResponse response){
   	 	LOGGER.info("::");
        System.out.println("-----------------");
//        request.getRequestDispatcher(arg0)
         try {
			response.sendRedirect("./view/jsp/index.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	public String findUser(){
		return "success";
	}
}
