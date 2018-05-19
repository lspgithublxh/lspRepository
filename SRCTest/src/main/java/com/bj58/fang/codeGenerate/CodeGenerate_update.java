package com.bj58.fang.codeGenerate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
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
public class CodeGenerate_update {

	public static String path = "D:\\test\\createClass";
	
	public static String package_path;
	static {
		package_path = CodeGenerate_update.class.getResource("").getPath();
		package_path = package_path.substring(package_path.indexOf("target/classes") + "target/classes".length()).replace("/", ".");
		package_path = package_path.substring(1, package_path.length() - 1);
	}
	
	public static void main(String[] args) throws IOException {
		String jsonTxtPath = "";//当前则不单行
		path = CodeGenerate_update.class.getResource("").getPath();
		path = path.replace("/target/classes", "/src/main/java");
		new CodeGenerate_update().test(path, jsonTxtPath);
		//加进去，反过来写
	}
	
	public void test(String targetDir, String filePath) throws IOException {
		File dir = new File(path);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		System.out.println(CodeGenerate_update.class.getResource("/").getPath());
		System.out.println(new File("").getCanonicalPath());
		System.out.println(new File("").getAbsolutePath());
		System.out.println(System.getProperty("user.dir"));
		System.out.println(this.getClass().getResource("").getPath());
		filePath = this.getClass().getResource("").getPath();
		filePath = filePath.replace("/target/classes", "/src/main/java") + "/json.txt";
//		System.out.println(this.getClass().getClassLoader().getResource("json.txt"));
//		System.out.println(System.getProperty("java.class.path"));
//		System.out.println(CodeGenerate.class.getClassLoader().getResource("json.txt"));
		String content = FileUtils.readFileToString(new File(filePath), "UTF-8");
		JSONObject object = JSONObject.parseObject(content);
		parseObject(object, "A", "a", false);
		StringBuilder builder = new StringBuilder();
		builder.append("package " + package_path + ";\r\n");
		builder.append("import java.io.IOException;\r\n");
		builder.append("public class Aconstruct{ \r\n\r\n\t public static void main(String[] args) throws IOException {\r\n");
		for(String str : buildNList) {
			builder.append(str);
		}
		for(String str : buildList) {
			builder.append(str);
		}
		builder.append("\t}\r\n}");
		File file = new File(path + "\\Aconstruct" + ".java");
		if(!file.exists()) {
			file.createNewFile();
		}
		
		FileWriter writer = new FileWriter(file);
		writer.write(builder.toString());
		
		writer.close();
	}
	
	public List<String> buildNList = new ArrayList<String>();
	public List<String> buildList = new ArrayList<String>();
	
	public void parseObject(JSONObject object, String className, String variableName, boolean isJSArray) throws IOException {
		StringBuilder builder = new StringBuilder();
		builder.append("package " + package_path + ";\r\n*_*");
		builder.append("public class " + className + " implements Serializable{\r\n");
		builder.append("\tprivate static final long serialVersionUID = 1L;\r\n");
		Map<String, JSONObject> jsMap = new TreeMap<String, JSONObject>();
		int index = 1;
		Set<String> typeImportSet = new HashSet<String>();
		Map<String,String> attrMap = new TreeMap<String,String>();
		Map<String,String> variaMap = new HashMap<String,String>();
		buildNList.add("\t\t" + className + " " + variableName + " = new " + className + "();\r\n");
		//jsonarray变量的名字
		Map<String, String[]> jsonArrayNameMap = new HashMap<String, String[]>();
		for(Entry<String, Object>  entry: object.entrySet()) {
//			System.out.println(entry.getKey() + "----" + entry.getValue().getClass());
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
//				builder.append("\tpublic A" + attrName + "\t" + entry.getKey() + ";\r\n");
				buildList.add("\t\t" + variableName + ".set" + name + "(" + attrVariaName + ");\r\n");
			}else if(vClass.endsWith(".JSONArray")) {
				attrName = className + "_" + entry.getKey();//json属性的类名
				jsMap.put(attrName, ((JSONArray)entry.getValue()).getJSONObject(0));
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
//				builder.append("\tpublic A" + attrName + "[]\t" + entry.getKey() + ";\r\n");
				jsonArrayAttrName = attrVariaName + "Arr";
				jsonArrayNameMap.put(jsonArrayAttrName + String.format(" = new %s", attrName), variaArr);
				buildList.add("\t\t" + attrName + " " + jsonArrayAttrName + " = null;\r\n");
				buildList.add("\t\t" + variableName + ".set" + name + "(" + jsonArrayAttrName + ");\r\n");
			}else {
				attrName = vClass.split(" ")[1].split("\\.")[2];
				typeImportSet.add(vClass.split(" ")[1]);
//				builder.append("\tpublic " + vClass.split(" ")[1].split("\\.")[2] + "\t" + entry.getKey() + ";\r\n");
				Object value = entry.getValue();
				if(attrName.equals("String")) {
					value = "\"" + value + "\"";
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
		for(Entry<String, String[]> entry : jsonArrayNameMap.entrySet()) {
			buildList.add("\t\t" + entry.getKey() + Arrays.asList(entry.getValue()).toString().replace("[", "{").replace("]", "};\r\n"));
		}
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
