package recommend;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class SubRecommend extends RecommendTest{

	static Map<String, String> map = new HashMap<String, String>();
	
	public static void main(String[] args) {
		while(true) {
			tt();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void tt() {
//		String url = "http://is.snssdk.com/api/news/feed/v46/?refer=1&refresh_reason=1&count=20&min_behot_time=1536716575&last_refresh_sub_entrance_interval=1536716585&loc_mode=7&loc_time=1536716191&latitude=39.993650265401925&longitude=116.51101884965183&city=%E5%8C%97%E4%BA%AC%E5%B8%82&tt_from=pull&lac=4150&cid=239883787&plugin_enable=4&iid=27307175300&device_id=49103918648&ac=wifi&channel=gdt_xu5_kp1&aid=35&app_name=news_article_lite&version_code=628&version_name=6.2.8&device_platform=android&ab_version=425650%2C486956%2C439457%2C265868%2C374099%2C378327%2C478829%2C437007%2C442271%2C467499%2C495788%2C424188&ab_client=a1%2Cc4%2Ce1%2Cf2%2Cg2%2Cf7&ab_feature=z1&abflag=3&ssmix=a&device_type=NCE-AL10&device_brand=HUAWEI&language=zh&os_api=23&os_version=6.0&openudid=395e7a319d9aa8f1&manifest_version_code=628&resolution=720*1208&dpi=320&update_version_code=6283&_rticket=1536716585344&fp=22TqJzmuF2mOFlP5J2U1FYFScMGt&rom_version=emotionui_4.1_nice-al10c00b128&ts=1536716586&as=l27615959639ba02ab7f88&mas=00a28975e8d67eeac58ddfc3ed54067758844a2680a072d5e8&cp=5cb4968768f29q1";
		String url = "https://is.snssdk.com/api/news/feed/v46/?refer=1&refresh_reason=1&count=20&min_behot_time=1536718785&last_refresh_sub_entrance_interval=1536718788&loc_mode=7&loc_time=1536716576&latitude=39.99375991001468&longitude=116.51118655946632&city=%E5%8C%97%E4%BA%AC%E5%B8%82&tt_from=pull&lac=4150&cid=239883787&plugin_enable=4&iid=27307175300&device_id=49103918648&ac=wifi&channel=gdt_xu5_kp1&aid=35&app_name=news_article_lite&version_code=628&version_name=6.2.8&device_platform=android&ab_version=425650%2C486956%2C439457%2C265868%2C374099%2C378327%2C478829%2C437007%2C442271%2C467499%2C495788%2C424188&ab_client=a1%2Cc4%2Ce1%2Cf2%2Cg2%2Cf7&ab_feature=z1&abflag=3&ssmix=a&device_type=NCE-AL10&device_brand=HUAWEI&language=zh&os_api=23&os_version=6.0&openudid=395e7a319d9aa8f1&manifest_version_code=628&resolution=720*1208&dpi=320&update_version_code=6283&_rticket=1536718788281&fp=22TqJzmuF2mOFlP5J2U1FYFScMGt&rom_version=emotionui_4.1_nice-al10c00b128&ts=1536718789&as=l258047527d9f53cdb4728&mas=007839e6f30daf46b5b499b5f0c526a95e02422c40268ad089&cp=52bd9b88717c4q1";
		Map<String, String> data = new HashMap<>();
		data.put("", "");
		try {
			String rs =	DefaultHttpConectionManager.getInstance().getPostJson(url, data);
			//是可以的
			JSONObject  r = JSONObject.parseObject(rs);
//			System.out.println(r);
			JSONArray da = r.getJSONArray("data");
			if(da != null) {
				for(int i = 0; i < da.size(); i++) {
					JSONObject item = da.getJSONObject(i);
					JSONObject article = null;
					if(item != null && null != item.getString("content")) {
						article = JSONObject.parseObject(item.getString("content"));
					}
					if(article != null) {
						String title = article.getString("abstract");
						String comment_count = article.getString("comment_count");
						String article_url = article.getString("article_url");
						String id = article.getString("item_id");
						generateSql(id, title, comment_count, article_url, "tt");
					}//
				}//
				String sql = map.get("tt");
				if(sql != null) {
					sql = sql.endsWith(",") ? sql.substring(0, sql.length() - 1) : sql;
					System.out.println(sql);
					saveToMysql(sql);
					
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void generateSql(String id , String title, String count, String url, String type) {
		String sql = map.get(type);
		if(sql == null) {
			sql = "insert into `test`.`tuijian`(id, title, html, comment_text, insert_time, insert_date, insert_timestamp) values";
		}else {
			sql += String.format("('%s', '%s', '%s', '%s','%s',date('%s'),CURRENT_TIMESTAMP()),", id, title,
					url, count,
					formatDate(), formatTime());
		}
		map.put(type, sql);
	}
}
