package com.bj58.fang.codeGenerate.json2json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JSONToAtherForm {

	public static void main(String[] args) {
		test();
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
}
