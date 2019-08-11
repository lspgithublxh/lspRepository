package com.bj58.fang.db.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {

	public static void main(String[] args) {
		test();
	}

	private static void test() {
		//a,b,c
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://jinpu-ajk.db.58dns.org:4670/jinpu_db?&zeroDateTimeBehavior=CONVERT_TO_NULL", "jinpu_rx_58", "jinpu_rx_58");
			conn.setAutoCommit(false);
			Statement statement = conn.createStatement();
			ResultSet reSet = statement.executeQuery("select count(*) from jinpu_db");
			while(reSet.next()) {
				int count = reSet.getInt(1);
				System.out.println("count:" + count);
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
}
