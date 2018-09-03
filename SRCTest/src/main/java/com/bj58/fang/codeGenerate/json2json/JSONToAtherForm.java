package com.bj58.fang.codeGenerate.json2json;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JSONToAtherForm {

	public static void main(String[] args) {
//		test();
//		getTeseDataMethod();
		te();
	}

	private static void maintest() {
		String js = "[     {         \"listName\": \"ershoufang\",         \"logpagetype\": \"fcapp-esfindex\",         \"filterParams\": {             \"param1077\": \"0\"         },         \"tradeline\": \"house\",         \"name\": \"二手房\",         \"actiontype\": \"zhengzu\",         \"pagetype\": \"list\",         \"logparam\": \"ershoufang\",         \"cateId\": \"12\",         \"logactiontype\": \"ershoufang-click\"     },     {         \"listName\": \"shangpucs\",         \"logpagetype\": \"fcapp-esfindex\",         \"filterParams\": {             \"filtercate\": \"shangpucs\"         },         \"tradeline\": \"house\",         \"name\": \"商铺\",         \"actiontype\": \"sptitle\",         \"pagetype\": \"list\",         \"logparam\": \"shangpu\",         \"cateId\": \"14\",         \"logactiontype\": \"shangpu-click\"     },     {         \"listName\": \"zhaozu\",         \"logpagetype\": \"fcapp-esfindex\",         \"filterParams\": {             \"param1092\": \"2\"         },         \"tradeline\": \"house\",         \"name\": \"写字楼\",         \"actiontype\": \"sychushou\",         \"pagetype\": \"list\",         \"logparam\": \"xiezilou\",         \"cateId\": \"13\",         \"logactiontype\": \"xiezilou-click\"     } ]";
		JSONArray arr = JSONArray.parseArray(js);
		JSONArray rs = new JSONArray();
		for(int i = 0 ; i < arr.size(); i++) {
			JSONObject item = arr.getJSONObject(i);
			JSONObject items16 = new JSONObject();
			items16.put("reuseId", "house-mainBusiness");
			items16.put("text", item.getString("name"));
			JSONObject jumpAction25 = new JSONObject();
			jumpAction25.put("tradeline", item.getString("tradeline"));
			JSONObject content15 = new JSONObject();
			content15.put("actiontype", item.getString("actiontype"));
			content15.put("logParam", item.getString("logparam"));
			content15.put("meta_url", item.getString("url") == null ? "" : item.getString("url"));//"https://appfang.58.com/api/list"
			content15.put("list_name", item.getString("listName"));
			content15.put("tradeline", item.getString("tradeline"));
			content15.put("cateid", item.getString("cateId"));
			content15.put("pagetype", item.getString("pagetype"));
			content15.put("action", "pagetrans");
			content15.put("title", item.getString("name"));
			jumpAction25.put("content", content15);
			jumpAction25.put("action", "pagetrans");
			items16.put("jumpAction", jumpAction25);
			items16.put("imgUrl", "https://img.58cdn.com.cn/fangrs/img/e3f57bdb386b68053bc26889ee89f988.png");
			items16.put("showActionType", item.getString("logactiontype"));
			items16.put("clickActionType", item.getString("logactiontype"));
			items16.put("type", "house-mainBusiness");
			rs.add(items16);
		}
		System.out.println("{\"data\":" + rs + "}");
	}
	
	private static void test() {
//		String js = "[     {         \"listName\": \"ershoufang\",         \"logpagetype\": \"fcapp-esfindex\",         \"filterParams\": {             \"param1077\": \"0\"         },         \"tradeline\": \"house\",         \"name\": \"二手房\",         \"actiontype\": \"zhengzu\",         \"pagetype\": \"list\",         \"logparam\": \"ershoufang\",         \"cateId\": \"12\",         \"logactiontype\": \"ershoufang-click\"     },     {         \"listName\": \"shangpucs\",         \"logpagetype\": \"fcapp-esfindex\",         \"filterParams\": {             \"filtercate\": \"shangpucs\"         },         \"tradeline\": \"house\",         \"name\": \"商铺\",         \"actiontype\": \"sptitle\",         \"pagetype\": \"list\",         \"logparam\": \"shangpu\",         \"cateId\": \"14\",         \"logactiontype\": \"shangpu-click\"     },     {         \"listName\": \"zhaozu\",         \"logpagetype\": \"fcapp-esfindex\",         \"filterParams\": {             \"param1092\": \"2\"         },         \"tradeline\": \"house\",         \"name\": \"写字楼\",         \"actiontype\": \"sychushou\",         \"pagetype\": \"list\",         \"logparam\": \"xiezilou\",         \"cateId\": \"13\",         \"logactiontype\": \"xiezilou-click\"     } ]";
		String js = "[     {         \"icon\": \"xiaoqudaquan\",         \"listName\": \"appxiaoqu\",         \"filterParams\": {},         \"actiontype\": \"xqdaquan\",         \"url\": \"https://app.58.com/dict/list\",         \"logactiontype\": \"xiaoqu-click\",         \"logpagetype\": \"fcapp-esfindex\",         \"tradeline\": \"house\",         \"name\": \"找小区\",         \"action\": \"pagetrans\",         \"pagetype\": \"list\",         \"cateId\": \"12\",         \"logparam\": \"xiaoqu\"     },     {         \"icon\": \"findmapico\",         \"name\": \"找经纪人\",         \"jumpAction\": {             \"bundleid\": \"23\",             \"title\": \"发布人信息\",             \"isfinish\": false,             \"backtoroot\": false,             \"params\": {                 \"userID\": \"a9fayT715sBblpQXoTMIK1b+k2nT6ZIaPdslPE1bzfGOycbICXez3pp+aA\",                 \"detailUrl\": \"https://appesf.58.com/app/landlord/getDetailInfo\",                 \"recomLog\": \"\",                 \"hideBar\": 0,                 \"infoID\": 0,                 \"list_name\": \"ershoufang\",                 \"full_path\": \"1,12\",                 \"houseListUrl\": \"https://appesf.58.com/app/landlord/getHouseList\",                 \"countType\": \"\",                 \"commondata\": {                     \"encryption\": \"a9fayT715sBblpQXoTMIK1b+k2nT6ZIaPdslPE1bzfGOycbICXez3pp+aA\",                     \"pageSource\": \"ershoufangHome\",                     \"cateID\": \"12\",                     \"sidDict\": \"\",                     \"jump_detail_action\": \"\"                 }             }         },         \"url\": \"\"     },     {         \"icon\": \"changfangico\",         \"listName\": \"fangchan\",         \"logpagetype\": \"fcapp-esfindex\",         \"filterParams\": {             \"param1124\": \"4\",             \"filtercate\": \"fangchan\"         },         \"tradeline\": \"house\",         \"name\": \"厂房仓库\",         \"actiontype\": \"sychangfang\",         \"pagetype\": \"list\",         \"logparam\": \"changfang\",         \"url\": \"https://appfang.58.com/api/list\",         \"cateId\": \"15\",         \"logactiontype\": \"changfang-click\"     },     {         \"icon\": \"xinpanico\",         \"listName\": \"xinpan\",         \"filterParams\": {},         \"actiontype\": \"newhousegtitlenew\",         \"url\": \"https://appfang.58.com/api/list\",         \"logactiontype\": \"xinpan-click\",         \"logpagetype\": \"fcapp-esfindex\",         \"tradeline\": \"house\",         \"name\": \"新盘\",         \"action\": \"loadpage\",         \"pagetype\": \"link\",         \"newparams\": {             \"nsource\": \"sou\",             \"key\": \"新盘\"         },         \"cateId\": \"12\",         \"logparam\": \"xinfang\"     },     {         \"icon\": \"xinfangico\",         \"listName\": \"ershoufang\",         \"logpagetype\": \"fcapp-esfindex\",         \"filterParams\": {},         \"tradeline\": \"house\",         \"name\": \"新房房源\",         \"action\": \"loadpage\",         \"pagetype\": \"link\",         \"logparam\": \"\",         \"url\": \"https://m.anjuke.com/bj/xinfang/xfy/?is_anjuke=1&from=58.app_app_home\",         \"cateId\": \"12\",         \"logactiontype\": \"xinfang-click\"     },     {         \"content\": {             \"content\": {                 \"title\": \"委托发布\",                 \"bundleid\": \"27\",                 \"isfinish\": false,                 \"pagetype\": \"RN\",                 \"params\": {                     \"cateid\": \"12\",                     \"needlogin\": true,                     \"hideBar\": 1,                     \"fullpath\": \"1,12\",                     \"listname\": \"ershoufang\"                 }             },             \"tradeline\": \"RN\",             \"action\": \"pagetrans\"         },         \"icon\": \"maifangico\",         \"listName\": \"fangchan\",         \"logpagetype\": \"fcapp-esfindex\",         \"tradeline\": \"house\",         \"name\": \"委托卖房\",         \"url\": \"\",         \"cateId\": \"12\",         \"logactiontype\": \"weituo-bj\"     },     {         \"icon\": \"fangchanwenda\",         \"listName\": \"fangchan\",         \"logpagetype\": \"new_index\",         \"filterParams\": {             \"param1124\": \"4\",             \"filtercate\": \"fangchan\"         },         \"tradeline\": \"core\",         \"name\": \"房产问答\",         \"action\": \"pagetrans\",         \"actiontype\": \"xqdaquan\",         \"pagetype\": \"link\",         \"logparam\": \"changfang\",         \"url\": \"https://wen.58.com/questionnaire/list/99996?from=new_fx_wenda_fc\",         \"logactiontype\": \"200000000988000100000010\"     },     {         \"icon\": \"new-icon\",         \"listName\": \"ershoufang\",         \"filterParams\": {             \"param12175\": \"1\",             \"tese\": \"param12175\"         },         \"actiontype\": \"ershoufang\",         \"url\": \"https://appfang.58.com/api/list\",         \"logactiontype\": \"200000000988000100000010\",         \"logpagetype\": \"new_index\",         \"tradeline\": \"house\",         \"name\": \"智能安选\",         \"action\": \"pagetrans\",         \"pagetype\": \"list\",         \"cateId\": \"12\",         \"logparam\": \"changfang\"     } ]";
		JSONArray arr = JSONArray.parseArray(js);
		JSONArray rs = new JSONArray();
		for(int i = 0 ; i < arr.size(); i++) {
			JSONObject item = arr.getJSONObject(i);
			JSONObject items16 = new JSONObject();
			items16.put("reuseId", "house-mainBusiness");
			items16.put("text", item.getString("name"));
			JSONObject jumpAction25 = new JSONObject();
			jumpAction25.put("tradeline", item.getString("tradeline"));
			JSONObject content15 = new JSONObject();
			content15.put("actiontype", item.getString("actiontype"));
			content15.put("logParam", item.getString("logparam"));
			content15.put("meta_url", item.getString("url") == null ? "" : item.getString("url"));//"https://appfang.58.com/api/list"
			content15.put("list_name", item.getString("listName"));
			content15.put("tradeline", item.getString("tradeline"));
			content15.put("cateid", item.getString("cateId"));
			content15.put("pagetype", item.getString("pagetype"));
			content15.put("action", "pagetrans");
			content15.put("title", item.getString("name"));
			jumpAction25.put("content", content15);
			jumpAction25.put("action", "pagetrans");
			items16.put("jumpAction", jumpAction25);
			items16.put("imgUrl", "https://img.58cdn.com.cn/fangrs/img/e3f57bdb386b68053bc26889ee89f988.png");
			items16.put("showActionType", item.getString("logactiontype"));
			items16.put("clickActionType", item.getString("logactiontype"));
			items16.put("type", "house-mainBusiness");
			rs.add(items16);
		}
		System.out.println("{\"data\":" + rs + "}");
	}
	
	private static void getTeseDataMethod() {
		String data = "[     {         \"listName\": \"ershoufang\",         \"logpagetype\": \"fcapp-esfindex\",         \"filterParams\": {             \"param1081\": \"0_50\"         },         \"tradeline\": \"house\",         \"name\": \"小户型\",         \"localname\": \"bj\",         \"url\": \"https://appfang.58.com/api/list\",         \"cateId\": \"12\",         \"logactiontype\": \"xiaohuxing-click\"     },     {         \"listName\": \"ershoufang\",         \"logpagetype\": \"fcapp-esfindex\",         \"filterParams\": {             \"param1084\": \"0_2\"         },         \"tradeline\": \"house\",         \"name\": \"准新房\",         \"localname\": \"bj\",         \"url\": \"https://appfang.58.com/api/list\",         \"cateId\": \"12\",         \"logactiontype\": \"zhunxinfang-click\"     },     {         \"listName\": \"ershoufang\",         \"logpagetype\": \"fcapp-esfindex\",         \"filterParams\": {             \"param1081\": \"70_90\",             \"param1574\": \"3\"         },         \"tradeline\": \"house\",         \"name\": \"小三居\",         \"localname\": \"bj\",         \"url\": \"https://appfang.58.com/api/list\",         \"cateId\": \"12\",         \"logactiontype\": \"xiaosanju-click\"     },     {         \"listName\": \"ershoufang\",         \"logpagetype\": \"fcapp-esfindex\",         \"filterParams\": {             \"param1080\": \"4\",             \"param1574\": \"2\"         },         \"tradeline\": \"house\",         \"name\": \"精装两居\",         \"localname\": \"bj\",         \"url\": \"https://appfang.58.com/api/list\",         \"cateId\": \"12\",         \"logactiontype\": \"jingzhuangliangju-click\"     },     {         \"listName\": \"ershoufang\",         \"logpagetype\": \"fcapp-esfindex\",         \"filterParams\": {             \"param1083\": \"5\"         },         \"tradeline\": \"house\",         \"name\": \"别墅\",         \"localname\": \"bj\",         \"url\": \"https://appfang.58.com/api/list\",         \"cateId\": \"12\",         \"logactiontype\": \"bieshu-click\"     },     {         \"listName\": \"ershoufang\",         \"logpagetype\": \"fcapp-esfindex\",         \"filterParams\": {             \"param1080\": \"4\"         },         \"tradeline\": \"house\",         \"name\": \"精装修\",         \"localname\": \"bj\",         \"url\": \"https://appfang.58.com/api/list\",         \"cateId\": \"12\",         \"logactiontype\": \"jingzhuangxiu-click\"     },     {         \"listName\": \"ershoufang\",         \"logpagetype\": \"fcapp-esfindex\",         \"filterParams\": {             \"param1085\": \"12_999\"         },         \"tradeline\": \"house\",         \"name\": \"带电梯\",         \"localname\": \"bj\",         \"url\": \"https://appfang.58.com/api/list\",         \"cateId\": \"12\",         \"logactiontype\": \"daidianti-click\"     },     {         \"listName\": \"ershoufang\",         \"logpagetype\": \"fcapp-esfindex\",         \"filterParams\": \"{\\\"param1078\\\":\\\"0_200\\\"}\",         \"tradeline\": \"house\",         \"name\": \"低总价\",         \"localname\": \"bj\",         \"url\": \"https://appfang.58.com/api/list\",         \"cateId\": \"12\",         \"logactiontype\": \"dizongjia-click\"     },     {         \"listName\": \"ershoufang\",         \"logpagetype\": \"fcapp-esfindex\",         \"filterParams\": {             \"param1086\": \"6\"         },         \"tradeline\": \"house\",         \"name\": \"南北通透\",         \"localname\": \"bj\",         \"url\": \"https://appfang.58.com/api/list\",         \"cateId\": \"12\",         \"logactiontype\": \"nanbeitongtou-click\"     } ]";
		JSONArray arr = JSONArray.parseArray(data);
		JSONArray rs = new JSONArray();
		for(int i = 0 ; i < arr.size(); i++) {
			JSONObject item = arr.getJSONObject(i);
			
			JSONObject items1x01 = new JSONObject();
			JSONObject jumpAction1 = new JSONObject();
//			jumpAction1.put("pagetype", "fcapp-esfindex");
			JSONObject content1 = new JSONObject();
			content1.put("pagetype", "list");
			content1.put("list_name", "ershoufang");
			content1.put("meta_url", "UrlConfig.APP_FANG_List");
			JSONObject params1 = new JSONObject();
//			JSONObject filterparams = new JSONObject();
//			filterparams.put("param1081", "0_50");// 例子
			params1.put("filterparams", item.get("filterParams"));//TODO 经纬度 是否要加上 localname或许也可以
			content1.put("params", params1);
			
			jumpAction1.put("content", content1);
			jumpAction1.put("action", "loadpage");
			jumpAction1.put("title", item.get("name"));
			jumpAction1.put("url", "UrlConfig.APP_FANG_List");
			items1x01.put("jumpAction", jumpAction1);
			items1x01.put("type", "house-image");
			items1x01.put("localname", "bj");
			items1x01.put("clickActionType", item.get("logactiontype"));
			JSONObject style1 = new JSONObject();
			style1.put("aspectRatio", 1.625);
			items1x01.put("style", style1);
			rs.add(items1x01);
//			items1x01.put("imgUrl", "https://pic3.58cdn.com.cn/nowater/fangfe/n_v2294fda8f729246e9b0f4a4c84fc071c5.png");
		} 
		System.out.println("{\"data\":" + rs + "}");
	}
	
	public static void te() {
		String r = "[  {         \"localname\": \"bj\",         \"style\": {             \"aspectRatio\": 1.625         },         \"jumpAction\": {             \"action\": \"loadpage\",             \"title\": \"小户型\",             \"url\": \"https://appfang.58.com/api/list\",             \"content\": {                 \"pagetype\": \"list\",                 \"list_name\": \"ershoufang\",                 \"params\": {                     \"filterparams\": {                         \"param1081\": \"0_50\"                     }                 },                 \"meta_url\": \"https://appfang.58.com/api/list\"             }         },         \"type\": \"house-image\",         \"clickActionType\": \"xiaohuxing-click\"     },     {         \"localname\": \"bj\",         \"style\": {             \"aspectRatio\": 1.625         },         \"jumpAction\": {             \"action\": \"loadpage\",             \"title\": \"准新房\",             \"content\": {                 \"pagetype\": \"list\",                 \"list_name\": \"ershoufang\",                 \"params\": {                     \"filterparams\": {                         \"param1084\": \"0_2\"                     }                 },                 \"meta_url\": \"https://appfang.58.com/api/list\"             },             \"url\": \"https://appfang.58.com/api/list\"         },         \"type\": \"house-image\",         \"clickActionType\": \"zhunxinfang-click\"     },     {         \"localname\": \"bj\",         \"style\": {             \"aspectRatio\": 1.625         },         \"jumpAction\": {             \"action\": \"loadpage\",             \"title\": \"小三居\",             \"content\": {                 \"pagetype\": \"list\",                 \"list_name\": \"ershoufang\",                 \"params\": {                     \"filterparams\": {                         \"param1081\": \"70_90\",                         \"param1574\": \"3\"                     }                 },                 \"meta_url\": \"https://appfang.58.com/api/list\"             },             \"url\": \"https://appfang.58.com/api/list\"         },         \"type\": \"house-image\",         \"clickActionType\": \"xiaosanju-click\"     },     {         \"localname\": \"bj\",         \"style\": {             \"aspectRatio\": 1.625         },         \"jumpAction\": {             \"action\": \"loadpage\",             \"title\": \"精装两居\",             \"url\": \"https://appfang.58.com/api/list\",             \"content\": {                 \"pagetype\": \"list\",                 \"list_name\": \"ershoufang\",                 \"params\": {                     \"filterparams\": {                         \"param1080\": \"4\",                         \"param1574\": \"2\"                     }                 },                 \"meta_url\": \"https://appfang.58.com/api/list\"             }         },         \"type\": \"house-image\",         \"clickActionType\": \"jingzhuangliangju-click\"     },     {         \"localname\": \"bj\",         \"jumpAction\": {             \"action\": \"loadpage\",             \"title\": \"别墅\",             \"content\": {                 \"pagetype\": \"list\",                 \"list_name\": \"ershoufang\",                 \"params\": {                     \"filterparams\": {                         \"param1083\": \"5\"                     }                 },                 \"meta_url\": \"https://appfang.58.com/api/list\"             },             \"url\": \"https://appfang.58.com/api/list\"         },         \"style\": {             \"aspectRatio\": 1.625         },         \"type\": \"house-image\",         \"clickActionType\": \"bieshu-click\"     },     {         \"localname\": \"bj\",         \"jumpAction\": {             \"action\": \"loadpage\",             \"title\": \"精装修\",             \"content\": {                 \"pagetype\": \"list\",                 \"list_name\": \"ershoufang\",                 \"params\": {                     \"filterparams\": {                         \"param1080\": \"4\"                     }                 },                 \"meta_url\": \"https://appfang.58.com/api/list\"             },             \"url\": \"https://appfang.58.com/api/list\"         },         \"style\": {             \"aspectRatio\": 1.625         },         \"type\": \"house-image\",         \"clickActionType\": \"jingzhuangxiu-click\"     },     {         \"localname\": \"bj\",         \"style\": {             \"aspectRatio\": 1.625         },         \"jumpAction\": {             \"action\": \"loadpage\",             \"title\": \"带电梯\",             \"content\": {                 \"pagetype\": \"list\",                 \"list_name\": \"ershoufang\",                 \"params\": {                     \"filterparams\": {                         \"param1085\": \"12_999\"                     }                 },                 \"meta_url\": \"https://appfang.58.com/api/list\"             },             \"url\": \"https://appfang.58.com/api/list\"         },         \"type\": \"house-image\",         \"clickActionType\": \"daidianti-click\"     },     {         \"localname\": \"bj\",         \"jumpAction\": {             \"action\": \"loadpage\",             \"title\": \"低总价\",             \"url\": \"https://appfang.58.com/api/list\",             \"content\": {                 \"pagetype\": \"list\",                 \"list_name\": \"ershoufang\",                 \"params\": {                     \"filterparams\": {                         \"param1078\": \"0_200\"                     }                 },                 \"meta_url\": \"https://appfang.58.com/api/list\"             }         },         \"style\": {             \"aspectRatio\": 1.625         },         \"type\": \"house-image\",         \"clickActionType\": \"dizongjia-click\"     },     {         \"localname\": \"bj\",         \"jumpAction\": {             \"action\": \"loadpage\",             \"title\": \"南北通透\",             \"url\": \"https://appfang.58.com/api/list\",             \"content\": {                 \"pagetype\": \"list\",                 \"list_name\": \"ershoufang\",                 \"params\": {                     \"filterparams\": {                         \"param1086\": \"6\"                     }                 },                 \"meta_url\": \"https://appfang.58.com/api/list\"             }         },         \"style\": {             \"aspectRatio\": 1.625         },         \"type\": \"house-image\",         \"clickActionType\": \"nanbeitongtou-click\"     } ]";
		JSONArray arr = JSONArray.parseArray(r);
		Map<String, JSONObject> ma = new HashMap();
		for(int j = 0; j < arr.size(); j++) {
			JSONObject item = arr.getJSONObject(j);
			ma.put(item.getJSONObject("jumpAction").getString("title"), item);
		}
		JSONObject rs = JSONObject.parseObject("{     \"type\": \"container-oneColumn\",     \"style\": {         \"padding\": [             0,             20,             20,             20         ]     },     \"items\": [         {             \"type\": \"houseFeatureLayout\",             \"data\": [                 {                     \"type\": \"houseFeatureTag\",                     \"title\": \"带电梯\",                     \"jumpAction\": {                         \"action\": \"loadpage\",                         \"pagetype\": \"link\",                         \"title\": \"熊猫公寓\",                         \"url\": \"https://m.58.com/bj/pinpaigongyu/857866485903683584/\"                     }                 },                 {                     \"type\": \"houseFeatureTag\",                     \"title\": \"别墅\",                     \"jumpAction\": {                         \"action\": \"loadpage\",                         \"pagetype\": \"link\",                         \"title\": \"熊猫公寓\",                         \"url\": \"https://m.58.com/bj/pinpaigongyu/857866485903683584/\"                     }                 },                 {                     \"type\": \"houseFeatureTag\",                     \"title\": \"小三居\",                     \"jumpAction\": {                         \"action\": \"loadpage\",                         \"pagetype\": \"link\",                         \"title\": \"熊猫公寓\",                         \"url\": \"https://m.58.com/bj/pinpaigongyu/857866485903683584/\"                     }                 },                 {                     \"type\": \"houseFeatureTag\",                     \"title\": \"准新房\",                     \"jumpAction\": {                         \"action\": \"loadpage\",                         \"pagetype\": \"link\",                         \"title\": \"熊猫公寓\",                         \"url\": \"https://m.58.com/bj/pinpaigongyu/857866485903683584/\"                     }                 },                 {                     \"type\": \"houseFeatureTag\",                     \"title\": \"南北通透\",                     \"jumpAction\": {                         \"action\": \"loadpage\",                         \"pagetype\": \"link\",                         \"title\": \"熊猫公寓\",                         \"url\": \"https://m.58.com/bj/pinpaigongyu/857866485903683584/\"                     }                 },                 {                     \"type\": \"houseFeatureTag\",                     \"title\": \"商住\",                     \"jumpAction\": {                         \"action\": \"loadpage\",                         \"pagetype\": \"link\",                         \"title\": \"熊猫公寓\",                         \"url\": \"https://m.58.com/bj/pinpaigongyu/857866485903683584/\"                     }                 },                 {                     \"type\": \"houseFeatureTag\",                     \"title\": \"底楼带花园\",                     \"jumpAction\": {                         \"action\": \"loadpage\",                         \"pagetype\": \"link\",                         \"title\": \"熊猫公寓\",                         \"url\": \"https://m.58.com/bj/pinpaigongyu/857866485903683584/\"                     }                 },                 {                     \"type\": \"houseFeatureTag\",                     \"title\": \"住宅\",                     \"jumpAction\": {                         \"action\": \"loadpage\",                         \"pagetype\": \"link\",                         \"title\": \"熊猫公寓\",                         \"url\": \"https://m.58.com/bj/pinpaigongyu/857866485903683584/\"                     }                 },                 {                     \"type\": \"houseFeatureTag\",                     \"title\": \"满五唯一\",                     \"jumpAction\": {                         \"action\": \"loadpage\",                         \"pagetype\": \"link\",                         \"title\": \"熊猫公寓\",                         \"url\": \"https://m.58.com/bj/pinpaigongyu/857866485903683584/\"                     }                 },                 {                     \"type\": \"houseFeatureTag\",                     \"title\": \"小产权\",                     \"jumpAction\": {                         \"action\": \"loadpage\",                         \"pagetype\": \"link\",                         \"title\": \"熊猫公寓\",                         \"url\": \"https://m.58.com/bj/pinpaigongyu/857866485903683584/\"                     }                 }             ]         }     ] }");
		JSONArray xx = rs.getJSONArray("items").getJSONObject(0).getJSONArray("data");
		for(int i = 0 ; i < xx.size(); i++) {
			JSONObject item = xx.getJSONObject(i);
			JSONObject source = ma.get(item.getString("title"));
			if(source != null) {
				item.put("jumpAction", source.get("jumpAction"));
				source.getJSONObject("jumpAction").put("pagetype", "RN");
			}else {
				JSONObject it = JSONObject.parseObject("{     \"action\": \"loadpage\",     \"title\": \"准新房\",     \"content\": {         \"pagetype\": \"list\",         \"list_name\": \"ershoufang\",         \"params\": {             \"filterparams\": {                 \"param1084\": \"0_2\"             }         },         \"meta_url\": \"https://appfang.58.com/api/list\"     },     \"url\": \"https://appfang.58.com/api/list\" }");
				it.put("title", item.getString("title"));
				item.put("jumpAction", it);
				System.out.println(item.getString("title"));
			}
		}
		System.out.println(rs.toJSONString());
	}
}
