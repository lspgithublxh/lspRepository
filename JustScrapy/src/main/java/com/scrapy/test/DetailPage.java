package com.scrapy.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * https://blog.csdn.net/codebattle/article/details/73650023
 * 
 *  wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
	sudo apt install -f
	dpkg -i google-chrome-stable_current_amd64.deb
	--------------------- 
	作者：MyCodeBattle 
	来源：CSDN 
	原文：https://blog.csdn.net/codebattle/article/details/73650023 
	版权声明：本文为博主原创文章，转载请附上博文链接！
 * @ClassName:DetailPage
 * @Description:
 * @Author lishaoping
 * @Date 2018年11月7日
 * @Version V1.0
 * @Package com.scrapy.test
 */
public class DetailPage {

	static Logger logger = null;
	static {
		System.setProperty("webdriver.chrome.driver","C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");//chromedriver服务地址
		try {
			File file = new File("D:\\log4j.xml");
			ConfigurationSource source = new ConfigurationSource(new FileInputStream(file), file);
			Configurator.initialize(null, source);
			logger = (Logger) LogManager.getLogger(DetailPage.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String count_ = args.length == 0 ? "0" : args[0];
		final String config = args.length < 2 ? "config" : args[1];
		int count = Integer.valueOf(count_);
		count = count == 0 ? 1 : count;
		ExecutorService exe = Executors.newCachedThreadPool();
		for(int i = 0 ; i < count; i++) {
			exe.execute(new Runnable() {
				public void run() {
					WebDriver driver = getOneDriver();
					while(true) {
						try {
							worker(config, driver);
						} catch (Exception e) {
							e.printStackTrace();
							driver.quit();
							break;
						}
					}
					
				}
			});
		}
	}

	private static void worker(String redis, WebDriver driver) {
		String config = RedisTest.getJedis().rpop(redis);
		String[] idCitys = config.split(",");
		driver.get(String.format("https://%s.ke.com/ershoufang/%s.html", idCitys[1], idCitys[0]));
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS); 
        
        //开始
//        Map<String, Object> detail = new HashMap<String, Object>();
        JSONObject detail = new JSONObject();
        System.out.println("start parse:");
        Document du = Jsoup.parse(driver.getPageSource());
    	try {
			Elements elements = du.getElementsByAttributeValue("class", "sellDetailHeader");
			Elements ele = elements.get(0).getElementsByAttributeValue("class", "title");
			Map<String, String> titlMap = new HashMap<String, String>();
			titlMap.put("main", ele.get(0).select("h1").get(0).attr("title"));
			titlMap.put("sub", ele.get(0).select("div.sub").get(0).text());
			detail.put("title", titlMap);
			//        String title = du.select("div.sellDetailHeader").get(0).select("div.title").get(0).select("h1").attr("title");
			//        System.out.println("------------:" + title);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Elements lis = du.select("div.thumbnail").get(0).select("ul.smallpic").get(0).select("li");
			//        String[] imgs = new String[lis.size()];
			JSONArray imgs = new JSONArray();
			for (int i = 0; i < lis.size(); i++) {
				//        	imgs[i] = lis.get(i).attr("data-src");
				imgs.add(lis.get(i).attr("data-src"));
			}
			detail.put("img", imgs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Elements duu = du.select("div.overview");
        try {
			Elements price = duu.get(0).select("div.content").get(0).select("div.price");
			Map<String, String> priceMap = new HashMap<String, String>();
			priceMap.put("total", price.get(0).select("span.total").get(0).text());
			priceMap.put("unit", price.get(0).select("span.unit").get(0).text());
			priceMap.put("unitPrice", price.get(0).select("div.unitPrice").get(0).text());
			detail.put("price", priceMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Map<String, String> houseMap = new HashMap<String, String>();
			Elements houseInfo = duu.get(0).select("div.houseInfo");
			Elements room = houseInfo.get(0).select("div.room");
			Elements type = houseInfo.get(0).select("div.type");
			Elements area = houseInfo.get(0).select("div.area");
			houseMap.put("ting", room.get(0).select("div.mainInfo").get(0).text());
			houseMap.put("layer", room.get(0).select("div.subInfo").get(0).text());
			houseMap.put("fangxiang", type.get(0).select("div.mainInfo").get(0).text());
			houseMap.put("loutype", type.get(0).select("div.subInfo").get(0).text());
			houseMap.put("mianji", area.get(0).select("div.mainInfo").get(0).text());
			houseMap.put("buildtime", area.get(0).select("div.subInfo").get(0).text());
			detail.put("house", houseMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Elements around = duu.get(0).select("div.aroundInfo");
			Element xiaoqu = around.get(0).select("div.communityName").get(0).select("a.info").get(0);
			String xiaoquId = xiaoqu.attr("href");
			if (xiaoquId != null && !"".equals(xiaoquId)) {
				xiaoquId = xiaoquId.split("/")[2];
			}
			Elements areaName = around.get(0).select("div.areaName");
			Map<String, String> aroundMap = new HashMap<String, String>();
			aroundMap.put("communityName", xiaoqu.text());
			aroundMap.put("xiaoquId", xiaoquId);
			aroundMap.put("area", areaName.get(0).select("span.info").get(0).text());
			aroundMap.put("ditie", areaName.get(0).select("a.supplement").get(0).text());
			detail.put("quyu", aroundMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Elements binfo = duu.get(0).select("div.brokerInfo");
			Elements brokerInfo = binfo.get(0).select("div.agentInfo");
			Elements headPhoto = brokerInfo.get(0).select("img.head_photo");
			String headHref = headPhoto.get(0).attr("src");
			Elements broker = brokerInfo.get(0).select("div.brokerName");
			Map<String, String> brokerMap = new HashMap<String, String>();
			brokerMap.put("head", headHref);
			brokerMap.put("brokerName", broker.get(0).select("span.name.LOGCLICK").get(0).text());
			brokerMap.put("brand", broker.get(0).select("span.brand_tag").get(0).text());
			try {
				brokerMap.put("xinxika", broker.get(0).select("span.jjr_infocard").get(0).attr("data-pop-img"));
			} catch (Exception e) {
//				e.printStackTrace();
			}
			try {
				brokerMap.put("zhengka", broker.get(0).select("span.jjr_license").get(0).attr("data-pop-img"));
			} catch (Exception e) {
//				e.printStackTrace();
			}
			brokerMap.put("phone",
					binfo.get(0).select("div.phone")
					.get(0).select("div.phone400")
					.get(0).text());
			detail.put("agentInfo", brokerMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Elements introContent = du.select("div#introduction").get(0).select("div.introContent");
			Elements intro_lis = introContent.get(0).select("div.base").get(0).select("ul").get(0).select("li");
			//        Map[] introArr = new Map[intro_lis.size()];
			JSONArray introArr = new JSONArray();
			for (int i = 0; i < intro_lis.size(); i++) {
				Map m = new HashMap<String, String>();
				String name = intro_lis.get(i).select("span.label").get(0).text();
				m.put("name", name);
				String introText = intro_lis.get(i).text();
				m.put("value", introText.replace("\n", "").substring(name.length()));
				//        	introArr[i] = m;
				introArr.add(m);
			}
//			System.out.println(detail);
			Elements transclis = introContent.select("div.transaction").get(0).select("ul").get(0).select("li");
			//        Map[] tranarr = new Map[transclis.size()];
			JSONArray tranarr = new JSONArray();
			for (int i = 0; i < transclis.size(); i++) {
				Map m = new HashMap<String, String>();
				String name = transclis.get(i).select("span.label").get(0).text();
				m.put("name", name);
				String introText = transclis.get(i).text();
				m.put("value", introText.replace("\n", "").substring(name.length()));
				tranarr.add(m);
			}
			//        Map<String, Map[]> basetransbody = new HashMap<String, Map[]>();
			JSONObject basetransbody = new JSONObject();
			basetransbody.put("baseinfolist", introArr);
			basetransbody.put("transaction", tranarr);
			detail.put("baseinfodetail", basetransbody);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Elements tese = du.select("div.introContent.showbasemore");
			Elements tags = tese.get(0).select("div.tags.clear");
			Elements tagsA = tags.get(0).select("div.content").get(0).select("a");
			String tagNames = "";
			for (int i = 0; i < tagsA.size(); i++) {
				tagNames += tagsA.get(i).text() + ",";
			}
			Elements baseattrOther = tese.get(0).select("div.baseattribute clear");
			//        Map[] tagNameMap = new Map[baseattrOther.size() + 1];
			JSONArray tagNameMap = new JSONArray();
			Map mt = new HashMap<String, String>();
			mt.put("name", tags.get(0).select("div.name").get(0).text());
			mt.put("content", tagNames);
			tagNameMap.add(mt);
			//        Map[] baseArr = new Map[baseattrOther.size()];
			for (int i = 0; i < baseattrOther.size(); i++) {
				Map m = new HashMap<String, String>();
				String name = baseattrOther.get(i).select("div.name").get(0).text();
				m.put("name", name);
				String introText = baseattrOther.get(i).select("div.content").get(0).text();
				m.put("content", introText);
				tagNameMap.add(m);
			}
			detail.put("tese", tagNameMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			JavascriptExecutor driver2 = (JavascriptExecutor) driver;
			driver2.executeScript("window.scrollBy(0, 700)");
			driver2.executeScript("window.scrollBy(0, 800)");
//			System.out.println("sssss" + du.select("div#daikanContainer").html());
			Thread.currentThread().sleep(100);
			Elements daikan = du.select("div#daikanContainer")
					.get(0).select("div.daikan_list")
					.get(0).select("div.item.clear");
			Elements imgDaiKan = daikan.get(0).select("span.item_img").get(0).select("img");
			Elements itemDaiKan = daikan.get(0).select("div.daikan_item_content");
			Elements itemTitle = itemDaiKan.get(0).select("div.item_title");
			Elements desc = itemDaiKan.get(0).select("div.des");
			Elements recordDaikan = itemDaiKan.get(0).select("div.daikan_item_record");
			Elements itemAgentName = itemTitle.get(0).select("span.itemAgentName");
			Elements brand_tag = itemTitle.get(0).select("span.brand_tag");
			Elements jjr_infocard = itemTitle.get(0).select("div.jjr_infocard");
			Elements phone = itemTitle.get(0).select("span.phone");
			Map<String, String> daiKanMap = new HashMap<String, String>();
			daiKanMap.put("name", itemAgentName.get(0).text());
			daiKanMap.put("brand", brand_tag.get(0).text());
			try {
				daiKanMap.put("card", jjr_infocard.get(0).attr("data-pop-img"));
			} catch (Exception e) {
//				e.printStackTrace();
			}
			daiKanMap.put("phone", phone.get(0).text());
			daiKanMap.put("desc", desc.get(0).text());
			daiKanMap.put("record", recordDaikan.get(0).text());
			daiKanMap.put("imgurl", imgDaiKan.get(0).attr("src"));
			detail.put("daikan", daiKanMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Elements agentTips = du.select("div.agent-tips");
			Elements agentFr = agentTips.get(0).select("div.fr");
			Elements hxfj_content = du.select("div.layout-wrapper").get(0).select("div.content");
			Elements imgDiv = hxfj_content.get(0).select("div.imgdiv");
			Elements hxfjDes = hxfj_content.get(0).select("div.des").get(0).select("div#infoList").get(0)
					.select("div.row");
			//        String[] descArr = new String[hxfjDes.size()];
			JSONArray descArr = new JSONArray();
			for (int i = 0; i < hxfjDes.size(); i++) {
				Elements dx = hxfjDes.get(i).select("div.col");
				String text = "";
				for (int j = 0; j < dx.size(); j++) {
					text += dx.get(j).text();
				}
				descArr.add(text);
			}
			Map<String, Object> hxfjMap = new HashMap<String, Object>();
			hxfjMap.put("nameurl", agentTips.get(0).select("div.fl").get(0).select("img").attr("src"));
			hxfjMap.put("name", agentFr.get(0).select("div.text").get(0).select("span").text());
			try {
				hxfjMap.put("card", agentFr.get(0).select("div.jjr_infocard").get(0).attr("data-pop-img"));
			} catch (Exception e) {
//				e.printStackTrace();
			}
			hxfjMap.put("phone", agentFr.get(0).select("p").get(0).text());
			hxfjMap.put("hxtu", imgDiv.get(0).attr("data-img"));
			hxfjMap.put("desc", descArr);
			detail.put("hxfj", hxfjMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Elements housepic = du.select("div.housePic").get(0).select("div.list").get(0).select("div");
			//        Map[] houseArr = new Map[housepic.size()];
			JSONArray houseArr = new JSONArray();
			for (int i = 0; i < housepic.size(); i++) {
				String dx = housepic.get(i).attr("class");
				if (dx != null && "left_fix".equals(dx)) {
					continue;
				}
				Elements imghouse = housepic.get(i).select("img");
				String src = "";
				if (imghouse != null && imghouse.size() > 0) {
					src = imghouse.get(0).attr("src");
				}
				Map m = new HashMap<String, String>();
				m.put("text", housepic.get(i).text());
				m.put("picurl", src);
				houseArr.add(m);
			}
			detail.put("pics", houseArr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Elements fyrecord = du.select("div#record");
			Elements fy_rows = fyrecord.get(0).select("div.list").get(0).select("div.content").get(0).select("div.row");
			//        Map[] fyrecordArr = new Map[fy_rows.size()];
			JSONArray fyrecordArr = new JSONArray();
			for (int i = 0; i < fy_rows.size(); i++) {
				Elements mytime = fy_rows.get(i).select("div.item mytime");
				Elements agentName = fy_rows.get(i).select("div.agentName");
				if (mytime == null || mytime.size() == 0 || agentName == null || agentName.size() == 0) {
					continue;
				}
				agentName.get(0).select("div.jjr_infocard");
				Elements agentPhone = fy_rows.get(i).select("div.phone");
				Elements agentTotal = fy_rows.get(i).select("div.item mytotal");
				Map m = new HashMap<String, String>();
				m.put("time", mytime.get(0).text());
				m.put("img", agentName.get(0).select("div.head_photo").get(0).attr("src"));
				m.put("name", agentName.get(0).select("span").get(0).text());
				m.put("brand", agentName.get(0).select("div.brand").get(0).text());
				m.put("card", agentName.get(0).select("div.brand").get(0).attr("data-pop-img"));
				m.put("phone", agentPhone.get(0).text());
				m.put("times", agentTotal.get(0).text());
				fyrecordArr.add(m);
			}
			Elements fypanel = fyrecord.get(0).select("div.panel");
			Map<String, Object> fypanelMap = new HashMap<String, Object>();
			fypanelMap.put("data", fyrecordArr);
			fypanelMap.put("last7", fypanel.get(0).select("div.panel-title").get(0).text());
			fypanelMap.put("count", fypanel.get(0).select("div.count").get(0).text());
			fypanelMap.put("totalcount", fypanel.get(0).select("div.totalCount").get(0).text());
			detail.put("fy_record", fypanelMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			JavascriptExecutor driver2 = (JavascriptExecutor) driver;
			driver2.executeScript("window.scrollBy(0, 700)");
			driver2.executeScript("window.scrollBy(0, 800)");
			driver2.executeScript("window.scrollBy(0, 900)");
//			driver2.executeScript("window.scrollBy(0, 1000)");
//			System.out.println(du.select("div#resblockCardContainer").get(0).html());
			Thread.currentThread().sleep(100);
			Elements xiaoquCard = du.select("div#resblockCardContainer")
					.get(0).select("div.xiaoquCard")
					.get(0).select("div.xiaoqu_content")
					.get(0).select("div.xiaoqu_main.fl")
					.get(0).select("div.xiaoqu_info");
			//        Map[] xqInfoArr = new Map[xiaoquCard.size()];
			JSONArray xqInfoArr = new JSONArray();
			for (int i = 0; i < xiaoquCard.size(); i++) {
				Elements label = xiaoquCard.get(i).select("label");
				Elements content = xiaoquCard.get(i).select("content");
				String con = "";
				for (int j = 0; j < content.size(); j++) {
					con += content.get(j).text() + ",";
				}
				Map m = new HashMap<String, String>();
				m.put("label", label.get(0).text());
				m.put("content", con);
				xqInfoArr.add(m);
			}
			Map<String, Object> xqInfoMap = new HashMap<String, Object>();
			xqInfoMap.put("data", xqInfoArr);
			detail.put("xq_info", xqInfoMap);
		} catch (Exception e) {
//			e.printStackTrace();
		}
		try {
			Elements xq_cjrecord = du.select("div#dealPrice").get(0).select("div#bizcircleDeal").get(0)
					.select("div.row");
			//        Map[] xqCjJlArr = new Map[xq_cjrecord.size()];
			JSONArray xqCjJlArr = new JSONArray();
			for (int i = 0; i < xq_cjrecord.size(); i++) {
				Elements house = xq_cjrecord.get(i).select("div.house");
				Elements xqarea = xq_cjrecord.get(i).select("div.area");
				Elements date = xq_cjrecord.get(i).select("div.date");
				Elements xqprice = xq_cjrecord.get(i).select("div.price");
				Elements xqunitprice = xq_cjrecord.get(i).select("div.unitPrice");
				Elements from = xq_cjrecord.get(i).select("div.from");
				Elements cjfy = house.get(0).select("a");
				Elements cjDesc = house.get(0).select("div.desc");
				Elements frame = cjDesc.get(0).select("div.frame");
				Elements floor = cjDesc.get(0).select("div.floor");
				Elements cjname = cjDesc.get(0).select("a");

				Map hMap = new HashMap<String, String>();
				hMap.put("href", cjfy.get(0).attr("href"));
				hMap.put("frame", frame.get(0).text());
				hMap.put("floor", floor.get(0).text());
				hMap.put("name", cjname.get(0).text());

				Map rs = new HashMap<String, Object>();
				rs.put("house", hMap);
				rs.put("area", xqarea.get(0).text());
				rs.put("date", date.get(0).text());
				rs.put("price", xqprice.get(0).text());
				rs.put("unitPrice", xqunitprice.get(0).text());
				rs.put("from", from.get(0).text());
				xqCjJlArr.add(rs);
			}
			detail.put("xq_cjrecord", xqCjJlArr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info(detail);
//		driver.quit();//关闭后台进程
	}
	
	private static WebDriver getOneDriver() {
		ChromeOptions options = new ChromeOptions();
        options.addArguments(Arrays.asList(new String[] {"--no-sandbox","window-size=1920x3000", "--disable-gpu", "--hide-scrollbars", "blink-settings=imagesEnabled=false", "--headless"}));
        WebDriver driver = new ChromeDriver(options); //新建一个WebDriver 的对象，但是new 的是FirefoxDriver的驱动
		return driver;
	}
}
