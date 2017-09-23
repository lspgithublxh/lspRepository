package com.tools.test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.UUID;

import com.tools.model.Site;

/**
 * 数据库连接,jdbc方式
 *
 *@author lishaoping
 *ToolsTest
 *2017年9月23日
 */
public class JDBC_MYSQLTool {

	private static Connection connection;
	static {
		try {
			InputStream in = JDBC_MYSQLTool.class.getResourceAsStream("jdbc.properties");
			Properties pro = new Properties();
			pro.load(in);
			String url = pro.getProperty("url");
			String user = pro.getProperty("username");
			String password = pro.getProperty("password");
			connection = DriverManager.getConnection(url, user, password);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void connectionTest() throws SQLException {
		//1.连接测试
		String sql = "select count(1) from website";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet set = statement.executeQuery();
		while(set.next()) {
			System.out.println(set.getInt(1));
		}
	}
	
	public static void writeWebSiteToDB(Site site) {
		UUID id = UUID.randomUUID();
		String uuid = id.toString().replace("-", "");
		String sql = "insert into website(id,url,content,remark) values(?,?,?,?)";
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, uuid);
			statement.setString(2, site.getUrl());
			statement.setString(3, site.getContent());
			statement.setString(4, site.getRemark());
			int i = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 批量插入
	 *@author lishaoping
	 *ToolsTest
	 *2017年9月23日
	 */
	public static void multipleInsert(String[] url) {
		String sql = "insert into website2(id,url) values";
		for(String ur : url) {
//			if(null == ur) continue;
			UUID id = UUID.randomUUID();
			String uuid = id.toString().replace("-", "");
			 sql += "('" + uuid + "','" + ur + "'),";
		}
		if(url.length > 0) {
			sql = sql.substring(0, sql.length() - 1);
			try {
				Statement stat = connection.createStatement();
				System.out.println(sql);
				int i = stat.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 单个插入
	 *@author lishaoping
	 *ToolsTest
	 *2017年9月23日
	 * @param url
	 * @param content
	 * @param remark
	 */
	public static void writeWebSiteToDB(String url, String content, String remark) {
		UUID id = UUID.randomUUID();
		String uuid = id.toString().replace("-", "");
		String sql = "insert into website(id,url,content,remark) values(?,?,?,?)";
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, uuid);
			statement.setString(2, url);
			statement.setString(3, content);
			statement.setString(4, remark);
			int i = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws SQLException {
//		connectionTest();
		multipleInsert(new String[] {"httxxx","https:"});
//		UUID id = UUID.randomUUID();
//		System.out.println(id.toString().replace("-", ""));
	}
}
