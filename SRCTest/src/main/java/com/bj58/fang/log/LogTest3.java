package com.bj58.fang.log;

import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * https://www.cnblogs.com/jessezeng/p/5144317.html极好参考
 * @ClassName:LogTest3
 * @Description:
 * @Author lishaoping
 * @Date 2018年11月19日
 * @Version V1.0
 * @Package com.bj58.fang.log
 */
public class LogTest3 {

	static Logger logger = LoggerFactory.getLogger(LogTest3.class);
	
	public static void main(String[] args) throws MalformedURLException {
//		File file = new File("D:\\scf_log.xml");
//		URL url = new URL("file:///D:/scf_log.xml");
//		DOMConfigurator.configure(url);
//		logger.info("hello");
		//同样要先初始化日志系统。。但是可以找到而已---定位输出。同理会类似logback
	}
}
