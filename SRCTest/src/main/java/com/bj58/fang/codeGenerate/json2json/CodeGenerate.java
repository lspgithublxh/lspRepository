package com.bj58.fang.codeGenerate.json2json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class CodeGenerate {

	public static void main(String[] args) {
//		String content = "{    \"title\": {        \"text\": \"用户评价\",        \"count\": 2    },    \"content\": [        {            \"date\": \"2018-5-15\",            \"content\": \"hahahahaha\",            \"satisfy\": \"超赞\",            \"satisfyUrl\": \"http://img.58cdn.com.cn/fangrs/img/fc6a30d0e6bec4d32857024af0f17a5f.png\",            \"title\": \"匿名用户\",            \"highLevelUrl\": \"\",            \"tags\": [                {                    \"content\": \"熟悉政策\",                    \"contentColor\": \"#517A99\",                    \"bgColor\": \"#517A99\"                },                {                    \"content\": \"熟悉政策\",                    \"contentColor\": \"#517A99\",                    \"bgColor\": \"#517A99\"                },                {                    \"content\": \"熟悉政策\",                    \"contentColor\": \"#517A99\",                    \"bgColor\": \"#517A99\"                }            ]        },        {            \"date\": \"2018-5-15\",            \"content\": \"hahahahaha\",            \"satisfy\": \"超赞\",            \"satisfyUrl\": \"http://img.58cdn.com.cn/fangrs/img/fc6a30d0e6bec4d32857024af0f17a5f.png\",            \"title\": \"匿名用户\",            \"highLevelUrl\": \"\",            \"tags\": [                {                    \"content\": \"熟悉政策\",                    \"contentColor\": \"#517A99\",                    \"bgColor\": \"#517A99\"                },                {                    \"content\": \"熟悉政策\",                    \"contentColor\": \"#517A99\",                    \"bgColor\": \"#517A99\"                },                {                    \"content\": \"熟悉政策\",                    \"contentColor\": \"#517A99\",                    \"bgColor\": \"#517A99\"                }            ]        }    ],    \"fullStar\": \"http://img.58cdn.com.cn/fangrs/img/fc6a30d0e6bec4d32857024af0f17a5f.png\",    \"emptyStar\": \"http://img.58cdn.com.cn/fangrs/img/8967a463d2571a98ec8eb2b5e88855dc.png\",    \"type\": \"evaluate\",    \"note\": \"Ta没有评价纪录~\"}";
//		String content = "{\"quota\": [{				\"value\": \"4.8\",				\"key\": \"房源质量\",				\"keyColor\":\"#333333\",				\"levelBgColor\":\"#F7271B\",				\"level\":\"高\",				\"content\":\"高于同行\",				\"contentValue\":\"13%\",				\"contentValueColor\":\"#F7271B\"			}, {				\"value\": \"3.8\",				\"key\": \"服务效率\",				\"levelBgColor\":\"#f97237\",				\"level\":\"平\",				\"keyColor\":\"#333333\",				\"content\":\"平于同行\",				\"contentValue\":\"\",				\"contentValueColor\":\"#f97237\"			}, {				\"value\": \"2.8\",				\"key\": \"用户评价\",				\"levelBgColor\":\"#00D984\",				\"level\":\"低\",				\"keyColor\":\"#333333\",				\"content\":\"低于同行\",				\"contentValue\":\"10%\",				\"contentValueColor\":\"#00D984\"			}]}";
//		String content = "{\"serviceTime\":[[{\"text\": \"已在58服务\",\"color\": \"#999999\"}, {\"text\": \"17\",\"color\": \"#ff542e\"}, {\"text\": \"天\",\"color\": \"#999999\"}],[{\"text\": \"带看服务\",\"color\": \"#999999\"}, {\"text\": \"17\",\"color\": \"#ff542e\"}, {\"text\": \"次\",\"color\": \"#999999\"}]]}";
//		String content = "{    \"title\": {        \"text\": \"用户评价\",        \"count\": 2    },    \"content\": [        {            \"date\": \"2018-5-15\",            \"content\": \"hahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahaha\",            \"satisfy\": \"超赞\",            \"satisfyUrl\": \"\",            \"title\": \"匿名用户\",            \"highLevelUrl\": \"\",            \"tags\": [                {                    \"content\": \"熟悉政策\",                    \"contentColor\": \"#517A99\",                    \"bgColor\": \"#EDF1F4\"                },                {                    \"content\": \"熟悉政策\",                    \"contentColor\": \"#517A99\",                    \"bgColor\": \"#EDF1F4\"                },                {                    \"content\": \"熟悉政策\",                    \"contentColor\": \"#517A99\",                    \"bgColor\": \"#EDF1F4\"                }            ]        },        {            \"date\": \"2018-5-15\",            \"content\": \"hahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahaha\",            \"satisfy\": \"超赞\",            \"satisfyUrl\": \"\",            \"title\": \"匿名用户\",            \"highLevelUrl\": \"\",            \"tags\": [                {                    \"content\": \"熟悉政策\",                    \"contentColor\": \"#517A99\",                    \"bgColor\": \"#EDF1F4\"                },                {                    \"content\": \"熟悉政策\",                    \"contentColor\": \"#517A99\",                    \"bgColor\": \"#EDF1F4\"                },                {                    \"content\": \"熟悉政策\",                    \"contentColor\": \"#517A99\",                    \"bgColor\": \"#EDF1F4\"                }            ]        }    ],    \"fullStar\": \"http://img.58cdn.com.cn/fangrs/img/fc6a30d0e6bec4d32857024af0f17a5f.png\",    \"emptyStar\": \"http://img.58cdn.com.cn/fangrs/img/8967a463d2571a98ec8eb2b5e88855dc.png\",    \"type\": \"evaluate\",    \"note\": \"Ta没有评价纪录~\"}";
//		String content = "{\"evaluateInfo\": [    {        \"value\": \"4.8\",        \"key\": \"房源质量\",        \"keyColor\": \"#333333\",        \"levelBgColor\": \"#F7271B\",        \"level\": \"高\",        \"content\": \"高于同行\",        \"contentValue\": \"13%\",        \"contentValueColor\": \"#F7271B\"    },    {        \"value\": \"3.8\",        \"key\": \"服务效率\",        \"levelBgColor\": \"#f97237\",        \"level\": \"平\",        \"keyColor\": \"#333333\",        \"content\": \"平于同行\",        \"contentValue\": \"\",        \"contentValueColor\": \"#f97237\"    },    {        \"value\": \"2.8\",        \"key\": \"用户评价\",        \"levelBgColor\": \"#00D984\",        \"level\": \"低\",        \"keyColor\": \"#333333\",        \"content\": \"低于同行\",        \"contentValue\": \"10%\",        \"contentValueColor\": \"#00D984\"    }]}";
		String content = "{\"serviceTime\": [    [        {            \"text\": \"已在58服务\",            \"color\": \"#999999\"        },        {            \"text\": \"17\",            \"color\": \"#ff542e\"        },        {            \"text\": \"天\",            \"color\": \"#999999\"        }    ],    [        {            \"text\": \"带看服务\",            \"color\": \"#999999\"        },        {            \"text\": \"17\",            \"color\": \"#ff542e\"        },        {            \"text\": \"次\",            \"color\": \"#999999\"        }    ]]}";
		generate(JSONObject.parseObject(content));
		Integer i = 0;
//		System.out.println(i.getClass().getName());
	}

	private static void generate(JSONObject json) {
		printJSON(json, "userValuate");
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
				printJSON((JSONObject)val, key);
				System.out.println(String.format("%s.put(\"%s\", %s);", name, key, key));
			}else if(type.contains("JSONArray")) {
				JSONArray arr = (JSONArray) val;
				String valuename = key + System.currentTimeMillis();
				printJSONArray(valuename, arr);//name是数组属于的json变量名
				System.out.println(String.format("%s.put(\"%s\", %s);", name, key, valuename));
			}
		}
	}

	//编程序，重要是职责分明，各个方法不要做不应该做的，否则程序本身反而复杂了   一点不清晰。。不好构造递归，，不好复合调用-多处调用。。。甚至参数选择都有要求和限制
	
	private static void printJSONArray(String key, JSONArray arr) {//key是数组变量名, 
		System.out.println(String.format("JSONArray %s = new JSONArray();",  key));
		for(int i = 0 ; i < arr.size(); i++) {
			Object a_val = arr.get(i);
			String aname = (key + i + "" + System.currentTimeMillis());
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
			String arrName = (name + System.currentTimeMillis());
			printJSONArray(arrName, (JSONArray) a_val);
			val = arrName;
		}
		return val;
	}
	
	
}
