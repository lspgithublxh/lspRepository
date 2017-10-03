package com.construct.tools.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService
public class MessageService implements IMessageService{

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);
	
	@WebMethod
	@Override
	public String getMessage(String id) {
		LOGGER.info("----webservice------start:");
		System.out.println("get the message:" + id);
		return "success";
	}

}
