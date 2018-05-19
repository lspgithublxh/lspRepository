package com.bj58.fang.log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

public class LogTest {

	public static void main(String[] args) {
		try {
			File file = new File("D:\\log4j.xml");
			ConfigurationSource source = new ConfigurationSource(new FileInputStream(file), file);
			Configurator.initialize(null, source);
			Logger log = (Logger) LogManager.getLogger(LogTest.class);
			log.info("abc");
		} catch (IOException e) {
			e.printStackTrace();
		}
//		Logger logger = LoggerFactory.getLogger(LogTest.class);
	}
}
