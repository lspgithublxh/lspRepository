package com.bj58.fang.codeGenerate.json2json;

import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class CodeGenerate2 {

	public static void main(String[] args) {
//		String content = "{    \"type\": \"container-fix\",    \"items\": [        {            \"needLogin\": true,            \"jumpAction\": \"\",            \"imgUrl\": \"http://pic1.58cdn.com.cn/gongyu/n_v2743f6cefe8f44051acbbf8525adcc71d_7292eb4ecb025567.png\",            \"sumShowLimit\": 10000,            \"dayShowLimit\": 1,            \"img\": \"http://pic1.58cdn.com.cn/gongyu/n_v2743f6cefe8f44051acbbf8525adcc71d_7292eb4ecb025567.png\",            \"metaUrl\": \"\",            \"adKey\": \"zf_dialogAd_272580fca6f52bb5eee78162436b312b\",            \"type\": \"house-dialogAd\",            \"style\": {                \"aspectRatio\": \"0.7\"            }        }    ]}";
//		String content = "{    \"area\": \"高新区-神仙树\",    \"buildTimeK\": \"建筑年代\",    \"buildTimeV\": \"2006年建\",    \"detailaction\": {        \"action\": \"pagetrans\"    },    \"distanceDict\": {        \"local_address\": \"高新区-神仙树\",        \"subway_desc\": \"距离地铁7号线神仙树站917米\"    },    \"flag\": \"1\",    \"infoID\": 8915,    \"name\": \"神仙树大院三期\",    \"picUrl\": \"http://pic6.58cdn.com.cn/anjuke_58/a740cb9d16d29488ec3b58cc088ba8e6?w=280&h=210\",    \"priceDict\": {        \"p\": \"28751\",        \"p_color\": \"#FF552E\",        \"u\": \"元/㎡\",        \"u_color\": \"#666666\"    },    \"priceK\": \"二手房均价\",    \"priceUnit\": \"元/㎡\",    \"priceV\": \"28751\",    \"subTitleKeys\": [        \"area\",        \"buildTimeV\"    ]}";
		String content = "{    \"title\": {        \"value\": \"安选房源\"    },    \"subTitle\": {        \"value\": \"副标题\",        \"leftIcon\": {            \"url\": \"https://pic6.58cdn.com.cn/nowater/fangfe/n_v2925db4a6fe9242e59cfc22af3dd12415.png\",            \"width\": 10,            \"height\": 10,            \"top\": 0,            \"left\": 0,            \"bottom\": 0,            \"right\": 5        }    },    \"button\": {        \"value\": \"设置\",        \"rightIcon\": {            \"url\": \"\",            \"width\": 13,            \"height\": 13,            \"top\": -2,            \"left\": 20,            \"bottom\": 0,            \"right\": 10        }    },    \"type\": \"house-itemIconTitle\"}";
		generate(JSONObject.parseObject(content));
//		Integer i = 0;
//		JSONObject s = new JSONObject();
//		s.put("a", "b");
//		JSONObject sx = (JSONObject) s.clone();//是继承的关系，赋予的关系..没有共享
//		sx.put("ss", "xx");
//		JSONObject s2 = new JSONObject();
//		System.out.println(sx.toJSONString());
//		System.out.println(s.toJSONString());
//		System.out.println(s.hashCode());
//		System.out.println(s2.hashCode());
//		System.out.println(i.getClass().getName());
	}

	private static void generate(JSONObject json) {
		printJSON(json, "data");//
	}
	
	/**
	 * 建立观点：打印角度
	 * @param 
	 * @author lishaoping
	 * @Date 2018年5月18日
	 * @Package com.bj58.fang.codeGenerate.json2json
	 * @return void
	 */
	private static void printJSON(JSONObject target, String name) {
		System.out.println(String.format("JSONObject %s = new JSONObject();",  name));
		for(String key : target.keySet()) {
			Object val = target.get(key);
			String type = val.getClass().getName();
			if(type.contains("Integer") || type.contains("Double") || type.contains("Decimal")
					|| type.contains("Long")) {
				System.out.println(String.format("%s.put(\"%s\", %s);", name, key, val));
			}else if(type.contains("String")) {
				System.out.println(String.format("%s.put(\"%s\", \"" +"%s\");",name, key, val));
//				System.out.println(name + ".put(" + key + ",\"" + val + "\");");
			}else if(type.contains("JSONObject")) {
				String variblename = key + getName(key);
				printJSON((JSONObject)val, variblename);
				System.out.println(String.format("%s.put(\"%s\", %s);", name, key, variblename));
			}else if(type.contains("JSONArray")) {
				JSONArray arr = (JSONArray) val;
				String valuename = key + getName(key);
				printJSONArray(valuename, arr);//name是数组属于的json变量名
				System.out.println(String.format("%s.put(\"%s\", %s);", name, key, valuename));
			}
		}
	}
	
	static Map<String, Integer> config = new HashedMap();
	
	private static Integer getName(String key) {
		if(config.containsKey(key)) {
			config.put(key, config.get(key) + 1);
		}else {
			config.put(key, 1);
		}
		return config.get(key);
	}
	
	//编程序，重要是职责分明，各个方法不要做不应该做的，否则程序本身反而复杂了   一点不清晰。。不好构造递归，，不好复合调用-多处调用。。。甚至参数选择都有要求和限制
	
	private static void printJSONArray(String key, JSONArray arr) {//key是数组变量名, 
		System.out.println(String.format("JSONArray %s = new JSONArray();",  key));
		for(int i = 0 ; i < arr.size(); i++) {
			Object a_val = arr.get(i);
			String aname = (key + i + "" + getName(key));
			Object t_val = keyvalueHandle(a_val, aname);//第一个是值，第二个是试着给那个值赋予变量名称， 
			System.out.println(String.format("%s.add(%s);", key, t_val));
		}
	}

	/**
	 * 
	 * @param 
	 * @author lishaoping
	 * @Date 2018年5月18日
	 * @Package com.bj58.fang.codeGenerate.json2json
	 * @return void
	 */
	private static Object keyvalueHandle(Object a_val, String name) {//name是这个值的变量名
		String type = a_val.getClass().getName();
		Object val = null;
		if(type.contains("Integer") || type.contains("Double") || type.contains("Decimal")
				|| type.contains("Long")) {
			val = a_val;
		}else if(type.contains("String")) {
			val = String.format("\"%s\"", a_val);
		}else if(type.contains("JSONObject")) {
			printJSON((JSONObject) a_val, name);
			val = name;
		}else if(type.contains("JSONArray")) {
			String arrName = (name + getName(name));
			printJSONArray(arrName, (JSONArray) a_val);
			val = arrName;
		}
		return val;
	}
	
	
}
