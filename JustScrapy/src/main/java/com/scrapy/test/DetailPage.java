package com.scrapy.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DetailPage {

	static {
		System.setProperty("webdriver.chrome.driver","C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");//chromedriver服务地址
	}
	
	public static void main(String[] args) {
		worker();
	}

	private static void worker() {
		WebDriver driver = getOneDriver();
		driver.get("https://bj.ke.com/ershoufang/101103599958.html");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS); 
        //开始
        Document du = Jsoup.parse(driver.getPageSource());
    	Elements elements = du.getElementsByAttributeValue("class", "sellDetailHeader");
    	Elements ele = elements.get(0).getElementsByAttributeValue("class", "title");
        Map<String, String> titlMap = new HashMap<String, String>();
        titlMap.put("main", ele.get(0).select("h1").get(0).attr("title"));
        titlMap.put("sub", ele.get(0).select("div.sub").get(0).text());
       
//        String title = du.select("div.sellDetailHeader").get(0).select("div.title").get(0).select("h1").attr("title");
//        System.out.println("------------:" + title);
        
        Elements lis = du.select("div.thumbnail").get(0).select("ul.smallpic").get(0).select("li");
        String[] imgs = new String[lis.size()];
        for(int i = 0 ; i < lis.size(); i++) {
        	imgs[i] = lis.get(i).attr("data-src");
        }
        
        Elements duu = du.select("div.overview");
        Elements price = duu.get(0).select("div.content").get(0).select("div.price");
        Map<String, String> priceMap = new HashMap<String, String>();
        priceMap.put("total", price.get(0).select("span.total").get(0).text());
        priceMap.put("unit", price.get(0).select("span.unit").get(0).text());
        priceMap.put("unitPrice", price.get(0).select("div.unitPrice").get(0).text());
        System.out.println(priceMap);
        
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
        
        Elements around = duu.get(0).select("div.aroundInfo");
        Element xiaoqu = around.get(0).select("div.communityName").get(0).select("a.info").get(0);
        String xiaoquId = xiaoqu.attr("href");
        if(xiaoquId != null && !"".equals(xiaoquId)) {
        	xiaoquId = xiaoquId.split("/")[2];
        }
        Elements areaName = around.get(0).select("div.areaName");
        Map<String, String> aroundMap = new HashMap<String, String>();
        aroundMap.put("communityName", xiaoqu.text());
        aroundMap.put("xiaoquId", xiaoquId);
        aroundMap.put("area", areaName.get(0).select("span.info").get(0).text());
        aroundMap.put("ditie", areaName.get(0).select("a.supplement").get(0).text());
        
        Elements brokerInfo = duu.get(0).select("div.brokerInfo").get(0).select("div.agentInfo");
        Elements headPhoto = brokerInfo.get(0).select("img.head_photo");
        String headHref = headPhoto.get(0).attr("src");
        Elements broker = brokerInfo.get(0).select("div.brokerName");
        Map<String, String> brokerMap = new HashMap<String, String>();
        brokerMap.put("head", headHref);
        brokerMap.put("brokerName", broker.get(0).select("span.name LOGCLICK").get(0).text());
        brokerMap.put("brand", broker.get(0).select("span.brand_tag").get(0).text());
        brokerMap.put("xinxika", broker.get(0).select("span.jjr_infocard").get(0).attr("data-pop-img"));
        brokerMap.put("zhengka", broker.get(0).select("span.jjr_license").get(0).attr("data-pop-img"));
        brokerMap.put("phone", brokerInfo.get(0).select("div.phone").get(0).select("div.phone400").get(0).text());
        
        Elements introContent = du.select("div#introduction").get(0).select("div.introContent");
        Elements intro_lis = introContent.get(0).select("div.base").get(0).select("ul").get(0).select("li");
        Map[] introArr = new Map[intro_lis.size()];
        for(int i = 0; i < intro_lis.size(); i++) {
        	Map m = new HashMap<String, String>();
        	String name = intro_lis.get(i).select("span.label").get(0).text();
        	m.put("name", name);
        	String introText = intro_lis.get(i).text();
        	m.put("value", introText.replace("\n", "").substring(name.length()));
        	introArr[i] = m;
        }
        Elements transclis = introContent.select("div.transaction").get(0).select("ul").get(0).select("li");
        Map[] tranarr = new Map[transclis.size()];
        for(int i = 0; i < transclis.size(); i++) {
        	Map m = new HashMap<String, String>();
        	String name = transclis.get(i).select("span.label").get(0).text();
        	m.put("name", name);
        	String introText = transclis.get(i).text();
        	m.put("value", introText.replace("\n", "").substring(name.length()));
        	tranarr[i] = m;
        }
        Map<String, Map[]> basetransbody = new HashMap<String, Map[]>();
        basetransbody.put("baseinfolist", introArr);
        basetransbody.put("transaction", tranarr);
        
        Elements tese = du.select("div.introContent showbasemore");
        Elements tags = tese.get(0).select("div.tags clear");
        Elements tagsA = tags.get(0).select("div.content").get(0).select("a");
        String tagNames = "";
        for(int i = 0; i < tagsA.size(); i++) {
        	tagNames += tagsA.get(i).text() + ",";
        }
        Map[] tagNameMap = new Map[transclis.size()];
        Map mt = new HashMap<String, String>();
        mt.put("name", tags.get(0).select("div.name").get(0).text());
        mt.put("content", tagNames);
        tagNameMap[0] = mt;
        Elements baseattrOther = tese.get(0).select("div.baseattribute clear");
        Map[] baseArr = new Map[baseattrOther.size()];
        for(int i = 0; i < baseattrOther.size(); i++) {
        	Map m = new HashMap<String, String>();
        	String name = baseattrOther.get(i).select("div.name").get(0).text();
        	m.put("name", name);
        	String introText = baseattrOther.get(i).select("div.content").get(0).text();
        	m.put("content", introText);
        	baseArr[i] = m;
        }
        
        Elements daikan = du.select("div#daikanContainer").get(0).select("div.daikan_list").get(0).select("div.item clear");
        Elements imgDaiKan = daikan.get(0).select("span.img");
        Elements itemDaiKan = daikan.get(0).select("div.daikan_item_content");
        Elements itemTitle = itemDaiKan.get(0).select("div.item_title");
        Elements desc = itemDaiKan.get(0).select("div.des");
        Elements recordDaikan = itemDaiKan.get(0).select("div.daikan_item_record");
        Elements itemAgentName = itemTitle.get(0).select("div.itemAgentName");
        Elements brand_tag = itemTitle.get(0).select("div.brand_tag");
        Elements jjr_infocard = itemTitle.get(0).select("div.jjr_infocard");
        Elements phone = itemTitle.get(0).select("div.phone");
        Map<String, String> daiKanMap = new HashMap<String, String>();
        daiKanMap.put("name", itemAgentName.get(0).text());
        daiKanMap.put("brand", brand_tag.get(0).text());
        daiKanMap.put("card", jjr_infocard.get(0).attr("data-pop-img"));
        daiKanMap.put("phone", phone.get(0).text());
        daiKanMap.put("desc", desc.get(0).text());
        daiKanMap.put("record", recordDaikan.get(0).text());
        daiKanMap.put("imgurl", imgDaiKan.get(0).attr("src"));
        
        Elements agentTips = du.select("div.agent-tips");
        ;
        Elements agentFr = agentTips.get(0).select("div.fr");
        
       
        
        Elements hxfj_content = du.select("div.layout-wrapper").get(0).select("div.content");
        Elements imgDiv = hxfj_content.get(0).select("div.imgdiv");
        Elements hxfjDes = hxfj_content.get(0).select("div.des").get(0).select("div#infoList").get(0).select("div.row");
        String[] descArr = new String[hxfjDes.size()];
        for(int i = 0; i < hxfjDes.size(); i++) {
        	Elements dx = hxfjDes.get(i).select("div.col");
        	String text = "";
        	for(int j = 0; j < dx.size(); j++) {
        		text += dx.get(j).text();
        	}
        	descArr[i] = text;
        }
        Map<String, Object> hxfjMap = new HashMap<String, Object>();
        hxfjMap.put("nameurl", agentTips.get(0).select("div.fl").get(0).select("img").attr("src"));
        hxfjMap.put("name", agentFr.get(0).select("div.text").get(0).select("span").text());
        hxfjMap.put("card", agentFr.get(0).select("div.jjr_infocard").get(0).attr("data-pop-img"));
        hxfjMap.put("phone", agentFr.get(0).select("p").get(0).text());
        hxfjMap.put("hxtu", imgDiv.get(0).attr("data-img"));
        hxfjMap.put("desc", descArr);
        
        Elements housepic = du.select("div.housePic").get(0).select("div.list").get(0).select("div");
        Map[] houseArr = new Map[baseattrOther.size()];
        for(int i = 0; i < housepic.size(); i++) {
        	String dx = housepic.get(i).attr("class");
        	if(dx != null && "left_fix".equals(dx)) {
        		continue;
        	}
        	Elements imghouse = housepic.get(i).select("img");
        	String src = "";
        	if(imghouse != null && imghouse.size() > 0) {
        		src = imghouse.get(0).attr("src");
        	}
        	Map m = new HashMap<String, String>();
        	m.put("text", housepic.get(i).text());
        	m.put("picurl", src);
        	houseArr[i] = m;
        }
        Elements fyrecord = du.select("div#record");
        Elements fy_rows = fyrecord.get(0).select("div.list").get(0).select("content").get(0).select("div.row");
        Map[] fyrecordArr = new Map[baseattrOther.size()];
        for(int i = 0; i < fy_rows.size(); i++) {
        	Elements mytime = fy_rows.get(i).select("div.item mytime");
        	Elements agentName = fy_rows.get(i).select("div.agentName");
        	if(mytime == null || mytime.size() == 0 || 
        			agentName == null || agentName.size() == 0) {
        		continue;
        	}
        	agentName.get(i).select("div.jjr_infocard");
        	Elements agentPhone = fy_rows.get(i).select("div.phone");
        	Elements agentTotal = fy_rows.get(i).select("div.item mytotal");
        	Map m = new HashMap<String, String>();
        	m.put("time", mytime.get(i).text());
        	m.put("img", agentName.get(i).select("div.head_photo").get(0).attr("src"));
        	m.put("name", agentName.get(i).select("span").get(i).text());
        	m.put("brand", agentName.get(i).select("div.brand").get(i).text());
        	m.put("card", agentName.get(i).select("div.brand").get(i).attr("data-pop-img"));
        	m.put("phone", agentPhone.get(i).text());
        	m.put("times", agentTotal.get(i).text());
        	fyrecordArr[i] = m;
        }
        Elements fypanel = fyrecord.get(0).select("div.panel");
        Map<String, Object> fypanelMap = new HashMap<String, Object>();
        fypanelMap.put("data", fyrecordArr);
        fypanelMap.put("last7", fypanel.get(0).select("div.panel-title").get(0).text());
        fypanelMap.put("count", fypanel.get(0).select("div.count").get(0).text());
        fypanelMap.put("totalcount", fypanel.get(0).select("div.totalCount").get(0).text());
        
        
	}
	
	private static WebDriver getOneDriver() {
		ChromeOptions options = new ChromeOptions();
        options.addArguments(Arrays.asList(new String[] {"--no-sandbox","window-size=1920x3000", "--disable-gpu", "--hide-scrollbars", "blink-settings=imagesEnabled=false", "--headless"}));
        WebDriver driver = new ChromeDriver(options); //新建一个WebDriver 的对象，但是new 的是FirefoxDriver的驱动
		return driver;
	}
}
