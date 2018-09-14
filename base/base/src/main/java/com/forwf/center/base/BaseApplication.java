package com.forwf.center.base;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 了解原理，制作一个简单的实现::比如一些数据压缩和加密， 一些加密传输，四大主题中的一些理论问题、原理问题
 * ---第二，数据库原理，极端重要：redis, mongodb, mysql, oracle, hbase这常见的，引擎级别的；；简单实现可以
 * @ClassName:BaseApplication
 * @Description:
 * @Author lishaoping
 * @Date 2018年9月14日
 * @Version V1.0
 * @Package com.forwf.center.base
 */
@SpringBootApplication
@MapperScan("com.forwf.center.base.mapper")
public class BaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseApplication.class, args);
	}
}
