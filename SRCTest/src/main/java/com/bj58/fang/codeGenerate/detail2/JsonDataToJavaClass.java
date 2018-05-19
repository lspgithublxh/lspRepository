package com.bj58.fang.codeGenerate.detail2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bj58.biz.utility.StringUtil;

/**
 * 下一步
 * @ClassName:CodeGenerate
 * @Description:
 * @Author lishaoping
 * @Date 2018年4月4日
 * @Version V1.0
 * @Package com.bj58.fang.codeGenerate
 */
public class JsonDataToJavaClass {
public static String path = "D:\\test\\createClass";
	
	public static String package_path;
	static {
		package_path = JsonDataToJavaClass.class.getResource("").getPath();
		package_path = package_path.substring(package_path.indexOf("target/classes") + "target/classes".length()).replace("/", ".");
		package_path = package_path.substring(1, package_path.length() - 1);
	}
	
	public static void main(String[] args) throws Exception {
		String jsonTxtPath = "";//当前则不单行
		path = JsonDataToJavaClass.class.getResource("").getPath();
		path = path.replace("/target/classes", "/src/main/java");
		new JsonDataToJavaClass().test(path, jsonTxtPath);
		//加进去，反过来写
	}
	
	public void test(String targetDir, String filePath) throws Exception {
		File dir = new File(path);
		if(!dir.exists()) {
			dir.mkdirs();
		}

		filePath = this.getClass().getResource("").getPath();
		filePath = filePath.replace("/target/classes", "/src/main/java") + "/json.txt";

		String content = FileUtils.readFileToString(new File(filePath), "UTF-8");
		JSONObject object = JSONObject.parseObject(content);
		parseObject(object, "A", "a", false);
		StringBuilder builder = new StringBuilder();
		builder.append("package " + package_path + ";\r\n");
		builder.append("import java.io.IOException;\r\nimport java.io.File;\r\nimport org.apache.commons.io.FileUtils;\r\nimport com.alibaba.fastjson.JSONObject;\r\nimport java.math.BigDecimal;\r\n");
		builder.append("public class Aconstruct{ \r\n\r\n\t public static void main(String[] args) throws IOException {\r\n");
		builder.append("\t\tnew Aconstruct().test();\r\n\t}\r\n\r\n");
		builder.append("\tpublic void test() throws IOException {\r\n");
		for(String str : buildNList) {
			builder.append(str);
		}
		for(String str : buildList) {
			builder.append(str);
		}
		//验证：
		builder.append("\t\tSystem.out.println(JSONObject.toJSONString(a).length());\r\n");
		builder.append("\t\tSystem.out.println(FileUtils.readFileToString(new File(Aconstruct.class.getResource(\"\").getPath().replace(\"/target/classes\", \"/src/main/java\") + \"/json.txt\"), \"utf-8\").length());\r\n");
		builder.append("\t}\r\n}");
		File file = new File(path + "\\Aconstruct" + ".java");
		if(!file.exists()) {
			file.createNewFile();
		}
		
		FileWriter writer = new FileWriter(file);
		writer.write(builder.toString());
		
		writer.close();
		
		//运行构造类：
		try {
			Thread.currentThread().sleep(3000);
			Class<?> aConstruct = Class.forName(package_path + ".Aconstruct");
			Method method = aConstruct.getMethod("test");
			method.invoke(aConstruct.newInstance());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public List<String> buildNList = new ArrayList<String>();
	public List<String> buildList = new ArrayList<String>();
	
	public void parseObject(JSONObject object, String className, String variableName, boolean isJSArray) throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("package " + package_path + ";\r\n*_*");
		builder.append("public class " + className + " implements Serializable{\r\n");
		builder.append("\tprivate static final long serialVersionUID = 1L;\r\n");
		Map<String, JSONObject> jsMap = new TreeMap<String, JSONObject>();
		Set<String> typeImportSet = new HashSet<String>();
		Map<String,String> attrMap = new TreeMap<String,String>();
		Map<String,String> variaMap = new HashMap<String,String>();
		buildNList.add("\t\t" + className + " " + variableName + " = new " + className + "();\r\n");
		//jsonarray变量的名字
		Map<String, Object[]> jsonArrayNameMap = new HashMap<String, Object[]>();
		for(Entry<String, Object>  entry: object.entrySet()) {
			//输出
			String vClass =	entry.getValue().getClass().toString();
			
			String attrName = null;
			String attrVariaName = null;
			String jsonArrayAttrName = null;
			String name = entry.getKey();
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
			if(vClass.endsWith(".JSONObject")) {
				attrName = className + "_" + entry.getKey();//json属性的类名
				jsMap.put(attrName, (JSONObject) entry.getValue());
				attrVariaName = variableName + "_" + entry.getKey();
				variaMap.put(attrName, attrVariaName);//第二参数为json属性在new时的变量名
				buildList.add("\t\t" + variableName + ".set" + name + "(" + attrVariaName + ");\r\n");
			}else if(vClass.endsWith(".JSONArray")) {
				//构造属性和设置变量属性
				attrName = setConstructAndGetAttrName(className, variableName, jsMap, typeImportSet, variaMap,
						jsonArrayNameMap, entry, vClass, attrName, name);
				
			}else {
				attrName = vClass.split(" ")[1].split("\\.")[2];
				//统一为long
				if(attrName.equals("Integer")) {
					attrName = "Long";
					typeImportSet.add("java.lang.Long");
				}else if(attrName.equals("BigDecimal")) {
					attrName = "Double";
				}else {
					typeImportSet.add(vClass.split(" ")[1]);
				}
				
				Object value = entry.getValue();
				if(attrName.equals("String")) {
					value = "\"" + value + "\"";
				}else if(attrName.equals("Long")) {
					value = value + "l";
				}
				buildList.add("\t\t" + variableName + ".set" + name + "(" + value + ");\r\n");
			}
			
			builder.append("\tprivate " + attrName + "\t" + entry.getKey() + ";\r\n");
			attrMap.put(entry.getKey(), attrName);
			
			
		}
		
		//next
		builder.append("\r\n");
		for(Entry<String,String> attr: attrMap.entrySet()) {
			String name = attr.getKey();
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
			builder.append("\tpublic " + attr.getValue() + " get" + name + "(){\r\n \t\treturn this." + attr.getKey() + ";\r\n\t}\r\n\r\n" );
			builder.append("\tpublic void set" + name + "( " + attr.getValue() + " " + attr.getKey() + "){\r\n \t\tthis." + attr.getKey() + "=" + attr.getKey() + ";\r\n\t}\r\n\r\n");
		}
		builder.append("}\r\n");
		System.out.println(builder.toString());
		File file = new File(path + "\\" + className + ".java");
		if(!file.exists()) {
			file.createNewFile();
		}
		String content = builder.toString();
		//import class补充
		String impClass = "import java.io.Serializable;\r\n";
		for(String cls : typeImportSet) {
			impClass += "import " + cls + ";\r\n";
		}
		content = content.replace("*_*", impClass);
		if(isJSArray) {
			//更新相关文件：同级属性综合
			String str = FileUtils.readFileToString(file, "UTF-8");
			if(!StringUtil.isNullOrEmpty(str)) {
				content = updateFile(str, content, attrMap);
			}
			
		}
		FileWriter writer = new FileWriter(file);
		writer.write(content);
		writer.close();
		//递归
		for(Entry<String, JSONObject> obj : jsMap.entrySet()) {
			String key = obj.getKey();
			if(key.contains("=xx")) {
				key = key.split("\\=xx")[0];
				parseObject(obj.getValue(), key, variaMap.get(obj.getKey()), true);
			}else if(isJSArray) {//父类为数组中对象，那么子类也是可能重复的。。其次，可以直接看是否存在---是否文件存在----级别是否存在
				parseObject(obj.getValue(), key, variaMap.get(key), true);
			}else {
				parseObject(obj.getValue(), key, variaMap.get(key), false);
			}
			
		}
		//补充json数组：
		for(Entry<String, Object[]> entry : jsonArrayNameMap.entrySet()) {
			if(entry.getValue()[0].getClass().getName().startsWith("[[")) {
				String str = "{";
				for(String[] xs : (String[][]) entry.getValue()[0]) {
					str += Arrays.asList(xs).toString().replace("[", "{").replace("]", "}") + ",";
				}
				str = str.substring(0, str.length() - 1) + "};\r\n";
				buildList.add("\t\t" + entry.getKey() + " = " + str);
//				buildList.add("\t\t" + entry.getKey() + " = " + Arrays.asList((String[])entry.getValue()[0]).toString().replace("[", "{").replace("]", "};\r\n"));	
			}else {
				buildList.add("\t\t" + entry.getKey() + " = " + Arrays.asList((String[])entry.getValue()[0]).toString().replace("[", "{").replace("]", "};\r\n"));
			}
			buildList.add((String)entry.getValue()[1]);
		}
	}

	private String setConstructAndGetAttrName(String className, String variableName, Map<String, JSONObject> jsMap,
			Set<String> typeImportSet, Map<String, String> variaMap, Map<String, Object[]> jsonArrayNameMap,
			Entry<String, Object> entry, String vClass, String attrName, String name) throws Exception {
		String attrVariaName;
		String jsonArrayAttrName;
		if(((JSONArray)entry.getValue()).size() == 0) {
			attrName = vClass.split(" ")[1].split("\\.")[2] + "[]";
			if("fastjson[]".equals(attrName)) {
				attrName = "String[]";
				typeImportSet.add("java.lang.String");
			}else {
				typeImportSet.add(vClass.split(" ")[1]);
			}
			buildList.add("\t\t" + variableName + ".set" + name + String.format("(new %s{});\r\n", attrName));
		}else {
			Object val = ( (JSONArray)entry.getValue()).get(0);
			//增加逻辑
			if(val instanceof JSONObject) {
				attrName = className + "_" + entry.getKey();//json属性的类名
				jsMap.put(attrName, ((JSONArray)entry.getValue()).getJSONObject(0));//可能为null，而出问题
				String[] variaArr = new String[((JSONArray)entry.getValue()).size()];
				//4.4新增
				for(int ie = 1; ie < ((JSONArray)entry.getValue()).size(); ie++) {
					jsMap.put(attrName + "=xx" + ie, ((JSONArray)entry.getValue()).getJSONObject(ie));
					variaMap.put(attrName + "=xx" + ie, variableName + "_" + entry.getKey() + ie);
					variaArr[ie] = variableName + "_" + entry.getKey() + ie;
				}
				attrVariaName = variableName + "_" + entry.getKey();
				variaMap.put(attrName, attrVariaName);//第二参数为json属性在new时的变量名
				variaArr[0] = attrVariaName;
				attrName = attrName + "[]";
				jsonArrayAttrName = attrVariaName + "Arr";
				Object[] objArr = {variaArr,"\t\t" + variableName + ".set" + name + "(" + jsonArrayAttrName + ");\r\n"};
				jsonArrayNameMap.put(attrName + " " + jsonArrayAttrName, objArr);
			}else if(val instanceof JSONArray) {
				//递归调用, 认为只有一层,,  直接传,,主要是为了生成新的类对象   和  Acounstruct构造属性而已...设置好jsMap, variaMap, jsonArrayNameMap
				
				Object firstV = null;
				for(int ie = 0; ie < ((JSONArray)entry.getValue()).size(); ie++) {
					JSONArray yuan = (JSONArray)((JSONArray)entry.getValue()).get(ie);
					for(int j = 0; j < yuan.size(); j++) {
						if(yuan.get(j) != null) {
							firstV = yuan.get(j);
						}
					}
				}
				if(firstV == null) {
					//无元素,是空集合
					buildList.add("\t\t" + variableName + ".set" + name + "(new Object[][]{});\r\n");
					attrName =  "Object[][]";
				}else {
					//设置值， 增加类，
					String ttype = firstV.getClass().getName().split("\\.")[2];
					if(firstV.getClass().getName().endsWith("JSONObject")) {
						attrName = className + "_" + entry.getKey();//json属性的类名
						String[][] variaArr = new String[((JSONArray)entry.getValue()).size()][];
						for(int ie = 0; ie < ((JSONArray)entry.getValue()).size(); ie++) {
							JSONArray yuan = (JSONArray)((JSONArray)entry.getValue()).get(ie);
							variaArr[ie] = new String[yuan.size()];
							for(int j = 0; j < yuan.size(); j++) {
								jsMap.put(attrName + "=xx" + ie + j, yuan.getJSONObject(j));
								variaMap.put(attrName + "=xx" + ie + j, variableName + "_" + entry.getKey() + ie + j);
								variaArr[ie][j] = variableName + "_" + entry.getKey() + ie + j;
							}
						}
						attrVariaName = variableName + "_" + entry.getKey();
						attrName = attrName + "[][]";
						jsonArrayAttrName = attrVariaName + "Arr";
						Object[] objArr = {variaArr,"\t\t" + variableName + ".set" + name + "(" + jsonArrayAttrName + ");\r\n"};
						jsonArrayNameMap.put(attrName + " " + jsonArrayAttrName, objArr);
					}else if(firstV.getClass().getName().endsWith("JSONArray")) {
						throw new Exception("cannot handle");
					}else{
						String arrStr = String.format("new %s[][] {", ttype);
						int ie = 0;
						for(; ie < ((JSONArray)entry.getValue()).size(); ie++) {
							JSONArray yuan = (JSONArray)((JSONArray)entry.getValue()).get(ie);
							String arr1 = "{";
							for(int j = 0; j < yuan.size(); j++) {
								if(ttype.equals("Long")) {
									arr1 += yuan.getLong(j) + "l,";
								}else if(ttype.equals("String")) {
									arr1 += "\"" + yuan.getString(j) + "\",";
								}else if(ttype.equals("BigDecimal")) {
									ttype = "Double";
									arr1 +=  " new Double(" + yuan.get(j) + "),";
								}else {
									arr1 += yuan.get(j) + ",";
								}
								
							}
							arr1 = arr1.substring(0, arr1.length() - 1) + "},";
							arrStr += arr1;
						}
						if(ie > 0) {
							arrStr = arrStr.substring(0, arrStr.length() - 1) + "}";
						}else {
							arrStr += "}";
						}
						typeImportSet.add(firstV.getClass().getName());
						buildList.add("\t\t" + variableName + ".set" + name + "(" + arrStr + ");\r\n");
						attrName = ttype + "[][]";
					}
					
				}
				//最后返回的是
				
			}
			else if(val instanceof String) {
//						attrName = vClass.split(" ")[1].split("\\.")[2] + "[]";
//						if("fastjson[]".equals(attrName)) {
//							attrName = "String[]";
//							typeImportSet.add("java.lang.String");
//						}else {
//							typeImportSet.add(vClass.split(" ")[1]);
//						}
				attrName = "String[]";
				typeImportSet.add("java.lang.String");
				String arrStr = "new String[] {";
				for(int ie = 0; ie < ((JSONArray)entry.getValue()).size(); ie++) {
					arrStr += "\"" + ((JSONArray)entry.getValue()).get(ie) + "\",";
				}
				arrStr = arrStr.substring(0, arrStr.length() - 1) + "}";
				buildList.add("\t\t" + variableName + ".set" + name + "(" + arrStr + ");\r\n");
			}else {
				String type = null;
				if(val instanceof Integer) {
					type = "Long";
				}else {
					type = val.getClass().getName().split("\\.")[2];
				}
				if(type.equals("BigDecimal")) {
					type = "Double";
				}
				if(val instanceof Integer) {
					attrName = "Long[]";
					typeImportSet.add("java.lang.Long");
				}else {
//					attrName = vClass.split(" ")[1].split("\\.")[2] + "[]";
					attrName = type + "[]";
//					typeImportSet.add(vClass.split(" ")[1]);
					typeImportSet.add(val.getClass().getName());
				}
				
				String arrStr = String.format("new %s[] {", type);
				if(type.equals("Long")) {
					for(int ie = 0; ie < ((JSONArray)entry.getValue()).size(); ie++) {
						arrStr += ((JSONArray)entry.getValue()).get(ie) + "l,";
					}
				}else if(type.equals("BigDecimal")){
					type = "Double";
					for(int ie = 0; ie < ((JSONArray)entry.getValue()).size(); ie++) {
						arrStr += "new Double(" + ((JSONArray)entry.getValue()).get(ie) + "),";
					}
				}else {
					for(int ie = 0; ie < ((JSONArray)entry.getValue()).size(); ie++) {
						arrStr += ((JSONArray)entry.getValue()).get(ie) + ",";
					}
				}
				arrStr = arrStr.substring(0, arrStr.length() - 1) + "}";
				buildList.add("\t\t" + variableName + ".set" + name + "(" + arrStr + ");\r\n");
			}
		}
		return attrName;
	}

	private Pattern p = Pattern.compile("private\\s+\\S+\\s+(\\S+);");
	
	private String updateFile(String str, String content, Map<String, String> attrMap) {
		Matcher m = p.matcher(str);//源文件
		List<String> oldAttrList = new ArrayList<String>();
		while(m.find()) {
			oldAttrList.add(m.group(1));
		}
		//断开为3部分
		String before = str.substring(0, str.indexOf("private"));
		String center = str.substring(str.indexOf("private"), str.lastIndexOf("}"));
		
		for(Entry<String, String> entry : attrMap.entrySet()) {
			if(!oldAttrList.contains(entry.getKey())) {
				//加属性
				before += "private " + entry.getValue() + "\t" + entry.getKey() + ";\r\n\t";
				//加方法
				String name = entry.getKey();
				name = name.substring(0, 1).toUpperCase() + name.substring(1);
				center += "\tpublic " + entry.getValue() + " get" + name + "(){\r\n \t\treturn this." + entry.getKey() + ";\r\n\t}\r\n\r\n" ;
				center += "\tpublic void set" + name + "( " + entry.getValue() + " " + entry.getKey() + "){\r\n \t\tthis." + entry.getKey() + "=" + entry.getKey() + ";\r\n\t}\r\n\r\n";
			}
		}
		return before + center + "}";
	}
	
}
