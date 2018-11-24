package com.bj58.fang.cache;

import org.apache.log4j.Logger;

public class NoCallbackInterException extends Exception {
	
	private static final Logger logger = Logger.getLogger(NoCallbackInterException.class);

	private static final long serialVersionUID = 1L;

	public NoCallbackInterException(String message) {
		logger.error("no callback:" + message);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
