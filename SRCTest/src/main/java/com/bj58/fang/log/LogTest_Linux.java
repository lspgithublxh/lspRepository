package com.bj58.fang.log;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;


public class LogTest_Linux {

	public static void main(String[] args) {
		DOMConfigurator.configure("/opt/test/dataProcess/scf_log4j.xml");  
		Logger logger = Logger.getLogger(LogTest_Linux.class);
        logger.info("Startup base path " + "");
	}
}
