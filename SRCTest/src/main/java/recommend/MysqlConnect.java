package recommend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * 权限不足将访问不到   root'@'localhost  这个代表了localhost来登陆root用户
 * 访问不到的根本原因是：启动了两个mysqld进程
 * 
 * 连接池问题，需要ok
 * @ClassName:MysqlConnect
 * @Description:
 * @Author lishaoping
 * @Date 2018年9月10日
 * @Version V1.0
 * @Package recommend
 */
public class MysqlConnect {

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
	
	public static void main(String[] args) {
//		base();
	}



	private static void base() {
		try {
//			String url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8";//10.252.62.125
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
			
			Connection con = DriverManager.getConnection(url, user, password);
			con.setAutoCommit(false);
			String sql = "select * from abc where 1 = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, 1);
//			con.commit();
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getString(1));
			}
//			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
