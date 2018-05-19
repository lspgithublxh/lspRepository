package com.bj58.fang.main;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class EncodeTest {

	public static void main(String[] args) throws UnsupportedEncodingException {
		Map<String, Object> params = new HashMap<String, Object>();
        	params.put("hideBar", 1);
        	params.put("detailUrl", "https://appfang.58.com/app/landlord/getDetailInfo");
        	params.put("houseListUrl", "https://appfang.58.com/app/landlord/getHouseList");
        Map<String, Object> new_action = new HashMap<String, Object>();
        new_action.put("params", params);
        String action = "wbmain://jump/RN/RN?params=" + URLEncoder.encode(JSON.toJSONString(new_action), "UTF-8");
        System.out.println(action);
        System.out.println(URLDecoder.decode("wbmain://jump/RN/RN?params=%7B%22title%22%3A%22%E5%8F%91%E5%B8%83%E4%BA%BA%E4%BF%A1%E6%81%AF%22%2C%22bundleid%22%3A%2223%22%2C%22isfinish%22%3Afalse%2C%22backtoroot%22%3Afalse%2C%22params%22%3A%7B%22userID%22%3A%22%22%2C%22infoid%22%3A%2233418241094575%22%2C%22recomlog%22%3A%22%22%2C%22list_name%22%3A%22fangchan%22%2C%22jumpdetailaction%22%3A%22%22%2C%22fullpath%22%3A%221%2C15%22%2C%22countType%22%3A%22%22%2C%22commondata%22%3A%7B%22userID%22%3A%2215%22%2C%22encryption%22%3A%22%22%2C%22pageSource%22%3A%22detail%22%2C%22sidDict%22%3A%7B%22GTID%22%3A%22190856329199940856383550085%22%2C%22PGTID%22%3A%22132454903199940856366982900%22%2C%22a2%22%3A0%2C%22isTelSecret%22%3A0%2C%22onlyWeiLiao%22%3A0%2C%22userType%22%3A1%7D%2C%22jump_detail_action%22%3A%22%22%7D%7D%7D", "UTF-8"));
	}
}
