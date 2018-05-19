package com.bj58.fang.codeGe.jsonObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import com.bj58.fang.codeGenerate.JsonDataToJavaClass;

/**
 * 
 * @ClassName:JsonDataToJSONObject
 * @Description:
 * @Author lishaoping
 * @Date 2018年4月9日
 * @Version V1.0
 * @Package com.bj58.fang.codeGe.jsonObject
 */
public class JsonDataToJSONObject {

	public static String package_path;
	static {
		package_path = JsonDataToJSONObject.class.getResource("").getPath();
		package_path = package_path.substring(package_path.indexOf("target/classes") + "target/classes".length()).replace("/", ".");
		package_path = package_path.substring(1, package_path.length() - 1);
	}
	
	public static void main(String[] args) throws IOException {
		String path = JsonDataToJSONObject.class.getResource("").getPath();
		path = path.replace("/target/classes", "/src/main/java") + "/json.txt";
		String content = FileUtils.readFileToString(new File(path), "utf-8");
		BufferedReader reader = new BufferedReader(new StringReader(content));
		String line = null;
//		Pattern pa = Pattern.compile("\\s+\"(\\S+)\"\\s*\\=>\\s*(\\S+?)[,(]");
		Pattern pa = Pattern.compile("\\s*\"(\\S+)\"\\s*\\=>\\s*(\\S+)");
		StringBuilder builder = new StringBuilder();
		builder.append("package " + package_path + ";\r\n");
		builder.append("import java.io.IOException;\r\nimport com.alibaba.fastjson.JSONObject;\r\n");
		builder.append("public class Construct {\r\n\tpublic static void main(String[] args) throws IOException {\r\n");
		builder.append("\t\tJSONObject a = new JSONObject();\r\n");
		String currJson = "a";
		Stack<String> stack = new Stack<String>();
		while((line = reader.readLine()) != null) {
			Matcher m = pa.matcher(line);
			if (m.find()) {
				String key = m.group(1);
				String value = m.group(2);
				if(value.contains(",")) {
					value = value.substring(0, value.indexOf(","));
				}
				if(value.contains("(")) {
					value = value.substring(0, value.indexOf("("));
				}
//				System.out.println(key + value);
				if(value.equals("array")) {
					builder.append("\t\t" + String.format("JSONObject %s = new JSONObject();\r\n", currJson + "_" + key));
					builder.append("\t\t" + currJson + String.format(".put(\"%s\", %s);\r\n", key, currJson + "_" + key));
					stack.push(currJson);
					currJson += "_" + key;
				}else {
					String type = value.getClass().getName();
					if(type.contains("String")) {
						builder.append("\t\t" + currJson + ".put(\"" +key + "\", " + value + ");\r\n");
					}else if(type.contains("Long")) {
						builder.append("\t\t" + currJson + ".put(\"" +key + "\"," + value + "l);\r\n");
					}else {
						builder.append("\t\t" + currJson + ".put(\"" +key + "\"," + value + ");\r\n");
					}
				}
			}else if(line.trim().equals("),")){
				currJson = stack.pop();
			}
		}
		builder.append("}};");
		System.out.println(builder.toString());
		String file = JsonDataToJSONObject.class.getResource("").getPath().replace("/target/classes", "/src/main/java") + "/Construct.java";
		File f = new File(file);
		if(!f.exists()) {
			f.createNewFile();
		}
		FileWriter writer = new FileWriter(f);
		writer.write(builder.toString());
		writer.close();
	}
}
