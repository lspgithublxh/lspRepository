package com.scrapy.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.logging.log4j.core.Logger;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import redis.clients.jedis.Jedis;

/**
 * 缓存丢失——cpu级别(因为非简单数据结构) 和内存仲裁消耗时间---arrayblokingqueue不是简单数据结构--因为使用了起止数据位 等标记变量
 * 
 * @ClassName:GetRestId
 * @Description:
 * @Author lishaoping
 * @Date 2018年11月20日
 * @Version V1.0
 * @Package com.scrapy.test
 */
public class GetRestId {

	static Logger logger = null;
//	static {
//		try {
//			File file = new File("D:\\log4j.xml");
//			ConfigurationSource source = new ConfigurationSource(new FileInputStream(file), file);
//			Configurator.initialize(null, source);
//			logger = (Logger) LogManager.getLogger(DetailPage.class);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	static Pattern p = Pattern.compile("^\\d+$");
	public static void main(String[] args) {
//		start();
//		mysqlrest();
		putToRedis();
		
	}

	private static void putToRedis() {
		try {
			Connection conn = MysqlTest.getConnection();
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery("select id,city from quanguo2");
			Map<String, String> map = new HashMap<String, String>();
			while(rs.next()) {
				String city = rs.getString(2);
				String id = rs.getString(1);
				map.put(id, city);
			}
			
			BufferedReader reader2 = new BufferedReader(new FileReader("D:\\log\\else2.log"));
			String line = "";
			Jedis jedis = RedisTest.getJedis();
			int i = 0;
			while((line = reader2.readLine())!= null) {
				System.out.println(line);
				System.out.println(i++);
				String city = map.get(line);
				jedis.lpush("ctid", line + "," + city);
//				List<String> yuan = new ArrayList<String>();
//				while(rs.next()) {
//					yuan.add(rs.getString(1));
//				}
				
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void mysqlrest() {
		Connection con = MysqlTest.getConnection();
		try {
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery("select id from quanguo2");
			List<String> yuan = new ArrayList<String>();
			while(rs.next()) {
				yuan.add(rs.getString(1));
			}
			MongoClient client = MongodTest.getClient();
			MongoCollection<Document> col = client.getDatabase("detail").getCollection("quanguo2");
//			col.find().projection();
			FindIterable<Document> du = col.find().projection(Filters.eq("id", 1));//Filters.and(Filters.eq("id", 1), Filters.eq("city", 1)
			MongoCursor<Document> it = du.iterator();
			List<String> shu = new ArrayList<String>();
			int i = 0; 
			while(it.hasNext()) {
				Document ducument = it.next();
				shu.add(ducument.getString("id"));
				System.out.println(i++);
			}
			System.out.println("over");
//			yuan.removeAll(shu);
			i = 0;
			for(String s : shu) {
				yuan.remove(s);
				i++;
				System.out.println(i);
			}
			System.out.println("over2");
			for(String id : yuan) {
				logger.info(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param 
	 * @author lishaoping
	 * @Date 2018年11月20日
	 * @Package com.scrapy.test
	 * @return void
	 */
	private static void start() {
		String[] fs = {"D:\\log\\detailp.log","D:\\log\\detailps.log","D:\\log\\detailps.log.2018-11-10","D:\\log\\detailps.log.2018-11-15",
				"D:\\log\\detailps13.log","D:\\log\\detailps14.log"};
		List<String> can = new ArrayList<String>();
		int j = 0; 
		List<String> rest = new ArrayList<String>();
		try {
			BufferedReader reader2 = new BufferedReader(new FileReader("D:\\log\\new.log"));
			String line = "";
			while((line = reader2.readLine())!= null) {
				rest.add(line.trim());
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(String fi : fs) {
			File f = new File(fi);
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(f));
				String line = null;
				String lastLine = "";
				while((line = reader.readLine()) != null) {//此方法并行读是否可以reader.readLine()
					if(line.length() < 10) {
						System.out.println(line);
						System.out.println(fi);//日志写入----并发时可能有问题：竟然会有：3016"}的残留--12243比较多
						System.out.println(lastLine);
						j++;
						can.add(line);
						continue;
					}
					j++;
					String l = line.substring(
							line.lastIndexOf(",") + 9, 
							line.length() - 2);
					System.out.println(j);
					rest.remove(l);
//					Matcher m = p.matcher(l);
//					if(!m.find()) {
////						System.out.println(l);
////						System.out.println(lastLine);
//						can.add(l);
////						return;
//					}
//					if(l.contains("\"")) {
//						can.add(l);
////						System.out.println(lastLine);
////						System.out.println(l);
////						return;
//					}
//					lastLine = line;
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(can);
		File el = new File("D:\\log\\else2.log");
		try {
			FileWriter writer = new FileWriter(el);
			for(String s : rest) {
				writer.write(s);
				writer.write("\r\n");
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void tt2() {
		
	}
}
