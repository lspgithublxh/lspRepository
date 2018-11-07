package com.bj58.fang.log;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.FactoryConfigurationError;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;


public class LogTest_Linux {

	public static void main(String[] args) {
//		DOMConfigurator.configure("/opt/test/dataProcess/scf_log4j.xml");  
//		Logger logger = Logger.getLogger(LogTest_Linux.class);
//        logger.info("Startup base path " + "");
		//这种方式不生效
		try {
			DOMConfigurator.configure(new URL("file:///D:/log4j.xml"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
		}  
		Logger logger = Logger.getLogger(LogTest_Linux.class);
        logger.info("Startup base path " + "");
	}
}
