package com.bj58.fang.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.FactoryConfigurationError;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class LogTest2 {

	public static void main(String[] args) throws FileNotFoundException, InstantiationException, IllegalAccessException, FactoryConfigurationError {
//		File file = new File("D:\\scf_log.xml");
//		DOMConfigurator configurer = new DOMConfigurator();
//		configurer.doConfigure(new FileInputStream(file), new NOPLoggerRepository());
		//1.ok
//		method1();
		//2.ok
		try {
			method2();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		for(String s : args) {
//			System.out.println(s);
//		}
//		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS a");
//		System.out.println(formate.format(new Date(System.currentTimeMillis())));
//		long startTime = 1517023620l;
//		long endTime = 1519963200l;
//		int i = 60;
//		System.out.println((endTime - startTime) / 60 * 1.5 / 60 / 60 + "h");
//		DOMConfigurator.configure("/opt/test/dataProcess/scf_log4j.xml");  
//		Logger logger = Logger.getLogger(LogTest_Linux.class);
//        logger.info("Startup base path " + "");
	}

	private static void method2() throws FileNotFoundException, MalformedURLException {
		File file = new File("D:\\scf_log.xml");
//		PropertyConfigurator.configure(new FileInputStream(file));
//		Logger log2 = LogManager.getLogger(LogTest2.class);
//		Logger log = Logger.getLogger(LogTest2.class);
//		log.info("abc");
//		log2.info("dev");
		//
//		URL url = LogTest2.class.getResource("file:///D:/scf_log.xml");
		URL url = new URL("file:///D:/scf_log2.xml");
		DOMConfigurator.configure(url);  
		Logger log = Logger.getLogger(LogTest2.class);
		for(int i = 0; i < 100; i++) {
			log.info("df我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是我是是 ");
		}
	}
	
	private static void method1() {
		BasicConfigurator.configure();
		Logger log2 = LogManager.getLogger(LogTest2.class);
		Logger log = Logger.getLogger(LogTest2.class);
		log.info("abc");
		log2.info("dev");
	}
}
