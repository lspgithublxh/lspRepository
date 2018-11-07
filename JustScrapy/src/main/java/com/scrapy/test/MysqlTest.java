package com.scrapy.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @ClassName:MysqlTest
 * @Description:
 * @Author lishaoping
 * @Date 2018年11月7日
 * @Version V1.0
 * @Package com.scrapy.test
 */
public class MysqlTest {

	public static void main(String[] args) {
		try {
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery("select id, city from quanguo2 limit 100");
			while(rs.next()) {
				System.out.println(rs.getString(1) + "," + rs.getString(2));
				RedisTest.getJedis().lpush("config", rs.getString(1) + "," + rs.getString(2));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private static Connection con;
	static {
		String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false";
		String user = "root";
		String password = "lsp";
		String driverName = "com.mysql.cj.jdbc.Driver";
		try {
			Class.forName(driverName);
			System.out.println("load success");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return con;
	}
}
