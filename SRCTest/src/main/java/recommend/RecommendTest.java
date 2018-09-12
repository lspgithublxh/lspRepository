package recommend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bj58.fang.log.LogTest;

public class RecommendTest {
	static Logger logger;
	
	static {
		File file = new File("D:\\log4j.xml");
		ConfigurationSource source;
		try {
			source = new ConfigurationSource(new FileInputStream(file), file);
			Configurator.initialize(null, source);
			logger = (Logger) LogManager.getLogger(LogTest.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		while(true) {
			test();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	private static void test() {
		String url = "https://mbd.baidu.com/searchbox?action=feed&cmd=100&service=bdbox&uid=_aHau08lv80cav8W0iS_8g8328gkiHi_Yuvhi_8lviKuLKDfB&from=1001187w&ua=_avLC_aE2f4qywoUfpw1z4PLXizeas8DgN2w8ALqC&ut=5iSTI0fc2iyjaXiDyuvGC5kcSMzWtqqSB&osname=baiduboxapp&osbranch=a0&pkgname=com.baidu.searchbox&network=1_0&cfrom=1020315l&ctv=2&cen=uid_ua_ut&typeid=2&puid=gu28i_ul-tGqdqqqB&sid=1007247_21949-1007114_21527-1006857_20646-1006598_19750-1007235_21909-1422_3312-1005329_15525-1007266_22018-1006908_20811-1006779_20355-1006649_19885-1007156_21660-1007284_22080-1006799_20432-1007309_22180-1007179_21734-1007047_21271-1007300_22148-1006942_20949-1006812_20496-1006550_19580-1005423_15829-481_1000-1007082_21383-1006695_20067-1006694_20061-1006587_19716-1007226_21873-1005555_16313&imgtype=webp&refresh=1";
		Map<String, String> data = new HashMap<>();
		data.put("", "");
		try {
			String rs =	DefaultHttpConectionManager.getInstance().getPostJson(url, data);
//			logger.info(rs);
			//是可以的
			JSONObject  r = JSONObject.parseObject(rs);
			JSONObject da = r.getJSONObject("data");
			JSONObject da100 = da.getJSONObject("100");
			JSONObject itemlist = da100.getJSONObject("itemlist");
			JSONArray lis = itemlist.getJSONArray("items");
//			handleOne(lis);
//			handdleTwo(lis);
			handdleThree(lis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void handdleThree(JSONArray lis) {
		Connection con = MysqlConnect.getConnection();
		String insert = "insert into `test`.`tuijian`(id, title, html, comment_text, insert_time, insert_date, insert_timestamp) values";
		for(int i = 0; i < lis.size(); i++) {
			JSONObject news = lis.getJSONObject(i);
			JSONObject dax = news.getJSONObject("data");
//			logger.info(String.format("%s | %s | %s | %s | %s", news.get("id"), dax.get("title"), 
//					dax.get("source"),  dax.get("comment_num"), dax.get("prefetch_html")));
			Object html = dax.get("prefetch_html");
			if(html == null) {
				JSONArray videos = dax.getJSONArray("prefetch_video");
				if(videos != null && videos.size() > 0) {
					html = videos.getJSONObject(0).getString("url");
				}else {
					html = dax.get("image");
					if(html == null) {
						JSONArray items = dax.getJSONArray("items");
						if(items!=null && items.size() > 0) {
							html = items.getJSONObject(0).get("image");
						}else {
							System.out.println("bug" + dax.toJSONString());
						}
					}
				}
			}
			insert += String.format("('%s', '%s', '%s', '%s','%s',date('%s'),CURRENT_TIMESTAMP()),", news.get("id"), dax.get("title"), 
					html, dax.get("comment_num"),
					formatDate(), formatTime());//System.currentTimeMillis() / 1000
		}
		if(!insert.endsWith(",")) {
			return;
		}
		insert = insert.substring(0, insert.length() - 1);
		try {
			Statement statement = con.createStatement();
			boolean rs = statement.execute(insert);
			if(rs) {
				System.out.println("执行成功！");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static String formatTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}
	
	private static String formatDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(new Date());
	}
	
//	private static String formatTimestamp() {
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		return format.format(new Date());
//	}
	
	private static void handdleTwo(JSONArray lis) {
		for(int i = 0; i < lis.size(); i++) {
			JSONObject news = lis.getJSONObject(i);
			JSONObject dax = news.getJSONObject("data");
			logger.info(String.format("%s | %s | %s | %s | %s", news.get("id"), dax.get("title"), 
					dax.get("source"),  dax.get("comment_num"), dax.get("prefetch_html")));
		}
	}

	private static void handleOne(JSONArray lis) {
		JSONArray save = new JSONArray();
		for(int i = 0; i < lis.size(); i++) {
			JSONObject news = lis.getJSONObject(i);
			JSONObject obj = new JSONObject();
			JSONObject dax = news.getJSONObject("data");
			obj.put("id", news.get("id"));
			obj.put("title", dax.get("title"));
			obj.put("source", dax.get("source"));
			obj.put("comment_num", dax.get("comment_num"));
			obj.put("prefetch_html", dax.get("prefetch_html"));
			save.add(obj);
		}
		//保存到数据库
		for(int i = 0; i < save.size(); i++) {
			System.out.println(save.getJSONObject(i).toJSONString());
			logger.info(save.getJSONObject(i).toJSONString());
		}
	}
}
