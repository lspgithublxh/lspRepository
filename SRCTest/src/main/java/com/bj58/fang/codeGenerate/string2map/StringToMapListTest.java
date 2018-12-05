package com.bj58.fang.codeGenerate.string2map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bj58.fang.codeGenerate.json2json.JSONCom;

public class StringToMapListTest {

	public static void main(String[] args) {
//		String str = "{    \"showActionType\": \"200000001340000100000100\",    \"style\": {        \"indicatorGap\": \"4\",        \"padding\": [            20,            20,            20,            20        ],        \"bgColor\": \"#F6F6F6\",        \"infinite\": \"true\",        \"autoScroll\": \"5000\",        \"indicatorGravity\": \"center\"    },    \"type\": \"10\",    \"items\": [        {            \"imgUrl\": \"https://pic1.58cdn.com.cn/ershoufang/landlord/n_v2833e3b2d11014bdf84d2c869e807b9a5.jpg\",            \"showActionType\": \"\",            \"jumpAction\": {                \"pagetype\": \"common\",                \"action\": \"loadpage\",                \"title\": \"智能安选\",                \"url\": \"https://fangfe.58.com/toy/active/1530005579741\"            },            \"style\": {                \"aspectRatio\": \"4.467\"            },            \"type\": \"house-image\",            \"title\": \"智能安选\",            \"clickActionType\": \"200000001079000100000010\"        },        {            \"imgUrl\": \"https://pic1.58cdn.com.cn/ershoufang/landlord/n_v2833e3b2d11014bdf84d2c869e807b9a5.jpg\",            \"showActionType\": \"\",            \"jumpAction\": {                \"pagetype\": \"common\",                \"action\": \"loadpage\",                \"title\": \"智能安选\",                \"url\": \"https://fangfe.58.com/toy/active/1530005579741\"            },            \"style\": {                \"aspectRatio\": \"4.467\"            },            \"type\": \"house-image\",            \"title\": \"智能安选\",            \"clickActionType\": \"200000001079000100000010\"        }    ]}";
		String str = "{\"showActionType\":\"200000001340000100000100\",\"style\":{\"indicatorGap\":\"4\",\"padding\":[20,20,20,20],\"bgColor\":\"#F6F6F6\",\"infinite\":\"true\",\"autoScroll\":\"5000\",\"indicatorGravity\":\"center\"},\"type\":\"10\",\"items\":[{\"imgUrl\":\"https://pic1.58cdn.com.cn/ershoufang/landlord/n_v2833e3b2d11014bdf84d2c869e807b9a5.jpg\",\"showActionType\":\"\",\"jumpAction\":{\"pagetype\":\"common\",\"action\":\"loadpage\",\"title\":\"智能安选\",\"url\":\"https://fangfe.58.com/toy/active/1530005579741\"},\"style\":{\"aspectRatio\":\"4.467\"},\"type\":\"house-image\",\"title\":\"智能安选\",\"clickActionType\":\"200000001079000100000010\"},{\"imgUrl\":\"https://pic1.58cdn.com.cn/ershoufang/landlord/n_v2833e3b2d11014bdf84d2c869e807b9a5.jpg\",\"showActionType\":\"\",\"jumpAction\":{\"pagetype\":\"common\",\"action\":\"loadpage\",\"title\":\"智能安选\",\"url\":\"https://fangfe.58.com/toy/active/1530005579741\"},\"style\":{\"aspectRatio\":\"4.467\"},\"type\":\"house-image\",\"title\":\"智能安选\",\"clickActionType\":\"200000001079000100000010\"}]}";
//		String s = "\"title\":\"智能\\\"安选\"";
//		Pattern strP = Pattern.compile("\".+?[^\\\\]\"");
//		Stack<Object> source = new Stack<>();
		//1.去掉多余空格
		//2.具有顺序性
		StringToMapListTest tt = new StringToMapListTest();
		Map<String, Object> data = new HashMap<>();
		tt.readJson(0, data, null, str.toCharArray());
//		System.out.println(data);
		//打印方法----构造出json再次
//		printJson(data);
//		JSONObject o = new JSONObject(data);
		JSONObject o = mapToJSon(data);
		System.out.println(o);
		System.out.println(JSONCom.jsonCom(JSONObject.parseObject(str), o));
		
	}
	
	private static JSONObject mapToJSon(Map<String, Object> data) {
		JSONObject rs = new JSONObject();
		for(Entry<String, Object> entry : data.entrySet()) {
			String type = entry.getValue().getClass().getSimpleName();
			if("HashMap".equals(type)) {
				rs.put(entry.getKey(), mapToJSon((Map<String, Object>) entry.getValue()));
			}else if("ArrayList".equals(type)) {
				rs.put(entry.getKey(), listToArray((List<Object>) entry.getValue()));
			}else {
				rs.put(entry.getKey(), entry.getValue());
			}
		}
		return rs;
	}
	
	private static JSONArray listToArray(List<Object> list) {
		JSONArray arr = new JSONArray();
		for(Object obj : list) {
			String type = obj.getClass().getSimpleName();
			if("HashMap".equals(type)) {
				arr.add(mapToJSon((Map<String, Object>) obj));
			}else if("ArrayList".equals(type)) {
				arr.add(listToArray((List<Object>) obj));
			}else {
				arr.add(obj);
			}
		}
		return arr;
	}

	private static void printJson(Map<String, Object> data) {
		for(Entry<String, Object> entry : data.entrySet()) {
			System.out.println();
		}
	}

	private static void printArray(List<Object> data) {
		for(Object obj : data) {
			String name = obj.getClass().getSimpleName();
			if("Map".equals(name)) {
				System.out.println("");
			}
			System.out.println(name);
		}
	}

	/**
	 * 起点{返回}
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月5日
	 * @Package com.bj58.fang.codeGenerate.string2map
	 * @return void
	 */
	public int readJson(int currPos, Map<String, Object> belongMap, List<Object> belongList, char[] cs) {
		boolean start = false;
		int i = currPos;
		String currK = null;
		while(true) {
			if(cs[i] == '{') {//开始了
				start = true;
				Object[] keyO = getK(i+1, cs);
				i = (Integer)keyO[0];
				currK = (String)keyO[1];
			}else if(cs[i] == '}'){//
				break;//结束
			}else if(cs[i] == ':') {//开始下一个值
				int type = -1;
				if(cs[i+1] == '{') {
					type = 3;
				}else if(cs[i+1] == '[') {
					type = 4;
				}else if(cs[i+1] == '"') {
					type = 2;
				}else if(cs[i+1] == 't' || cs[i+1] == 'f') {
					type = 5;
				}else if(cs[i+1] >= '0' && cs[i+1] <= '9') {
					type = 1;
				}
				if(type != -1) {
					Object[] valO = getVal(type, i+1, cs, belongMap, belongList, type == 3 ? 1:2);
					belongMap.put(currK, valO[1]);
					i = (Integer)valO[0];
				}
			}else if(cs[i] == ',') {//开始下一个键
				Object[] keyO = getK(i+1, cs);
				currK = (String) keyO[1];
				i = (Integer)keyO[0];
			}
			i++;
			if(i >= cs.length) {
				break;
			}
			System.out.println("----json"+ i + ":" + cs.length);
		}
		return i;
	}
	
	public int readArray(int currPos, Map<String, Object> belongMap, List<Object> belongList, char[] source) {
		int i = currPos;
		if(source[i] == '[') {
			i++;
		}
		while(true) {
			int type = -1;
			if(i == source.length - 1) {
				break;
			}
			System.out.println(source[i]);
			if(source[i] == '{') {
				type = 3;
			}else if(source[i] == '[') {
				type = 4;
			}else if(source[i] == '"') {
				type = 2;
			}else if(source[i] == 't' || source[i] == 'f') {
				type = 5;
			}else if(source[i] >= '0' && source[i] <= '9') {
				type = 1;
			}else if(source[i] == ',') {
			}else if(source[i] == ']') {
				break;
			}
			if(type != -1) {
				Object[] valO = getVal(type, i, source, belongMap, belongList, type == 3 ? 1:2);
				belongList.add(valO[1]);
				i = (Integer)valO[0];
			}
			i++;
			if(i >= source.length) {
				break;
			}
			System.out.println("---array" + i + ":" + source.length);
		}
		return i;
	}
	
	
	/**
	 * 第一个为位置，第二个为值
	 * 1.数字
	 * 2.字符串
	 * 3.jsonObject
	 * 4.array
	 * 5.boolean
	 * 开始是:后第一个元素开始
	 * 结束位是:值的最后一位
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月5日
	 * @Package com.bj58.fang.codeGenerate.string2map
	 * @return Object[]
	 */
	public Object[] getVal(int valType, int currPos, char[] source, Map<String, Object> belongMap, List<Object> belongList, int belongType) {//返回，第一个位置，第二个值类型
		String val = "";
		if(valType == 1) {
			for(int i = currPos; i < source.length; i++) {
				if(source[i] != ',' && source[i] != '}' && source[i] != ']') {//下一个键,或者结束
					val += source[i];
				}else {
					return new Object[] {i - 1, val.contains(".") ? Float.valueOf(val) : Integer.valueOf(val)};
				}
			}
		}else if(valType == 2) {
			Object[] get = getK(currPos, source);
			return get;
		}else if(valType == 5) {
			for(int i = currPos; i < source.length; i++) {
				if(source[i] != ',' && source[i] != '}') {//下一个键,或者结束
					val += source[i];
				}else {
					return new Object[] {i - 1, Boolean.valueOf(val)};
				}
			}
		}else if(valType == 3) {
			Map<String, Object> nextmap = new HashMap<>();
			int curr = readJson(currPos, nextmap, null, source);
			return new Object[] {curr, nextmap};
		}else if(valType == 4) {
			List<Object> nextlist = new ArrayList<>();
			int curr = readArray(currPos, null, nextlist, source);
			return new Object[] {curr, nextlist};
		}
		return null;
	}
	
	public Object[] getK(int currPos, char[] cs) {//开始是"开始, 返回也是"位置
		Character lastV = '\b';//cs[currPos-1]
		String k = "";
		boolean start = false;
		for(int i = currPos; i < cs.length; i++) {
			if(lastV != '\\') {
				if(cs[i] == '"') {
					if(start) {//代表结束
						return new Object[] {i, k};
					}else {//代表开始
					}
				}else {//代表中间
					k += cs[i];
//					lastV = cs[i];
				}
			}else {
				k += cs[i];
//				lastV = cs[i];
//				if(source[i] == '"') {//未结束
//					k += source[i];
//				}else {//转移符号，都不结束
//					k += source[i];
//				}
			}
			lastV = cs[i];
			start = true;
		}
		return null;
	}
}
