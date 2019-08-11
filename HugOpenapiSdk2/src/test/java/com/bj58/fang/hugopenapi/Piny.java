package com.bj58.fang.hugopenapi;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Piny {

	 public void testPinyinx(String enumType, String meiju) throws BadHanyuPinyinOutputFormatCombination {
	        String name = "这个拼音工具还可以ddd";
	        char[] charArray = name.toCharArray();
	        StringBuilder pinyin = new StringBuilder();
	        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
	        //设置大小写格式
	        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
	        //设置声调格式：
	        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	        for (int i = 0; i < charArray.length; i++) {
	            //匹配中文,非中文转换会转换成null
	            if (Character.toString(charArray[i]).matches("[\\u4E00-\\u9FA5]+")) {
	                 String[] hanyuPinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(charArray[i],defaultFormat);
	                String string =hanyuPinyinStringArray[0];
	                pinyin.append(string);
	            } else {
	                pinyin.append(charArray[i]);
	            }
	        }
	        System.err.println(pinyin);
	    }
	 
	 public static void testPinyin(String enumType, String source, String pack) throws BadHanyuPinyinOutputFormatCombination {
//	        String name = "这个拼音工具还可以ddd";
	        char[] charArray = source.toCharArray();
	        StringBuilder pinyin = new StringBuilder();
	        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
	        //设置大小写格式
	        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
	        //设置声调格式：
	        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	        Map<String, String> kv = new HashMap<String, String>();
	        String k = "";
	        String v = "";
	        boolean start = false;
	        for (int i = 0; i < charArray.length; i++) {
	            //匹配中文,非中文转换会转换成null
	            if (Character.toString(charArray[i]).matches("[\\u4E00-\\u9FA5]+")) {
	                String[] hanyuPinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(charArray[i], defaultFormat);
	                String string = hanyuPinyinStringArray[0];
	                string = string.replace("u:", "v");
	                pinyin.append(string);
	                v += string;
	                start = true;
	            } else {
	            	if(start) {
	            		kv.put(k, v);
	            		k = "";
	            		v = "";
	            		start = false;
	                }
	            	k += charArray[i];
	                pinyin.append(charArray[i]);
	            }
	        }
	        if(!"".equals(k) && !v.trim().equals("")) {
	        	kv.put(k, v);
	        }
	        String entype = enumType.substring(0, 1).toUpperCase() + enumType.substring(1);//"Vali" + defaultFormat.hashCode();
	        StringBuilder b = new StringBuilder("package com.bj58.fang.hugopenapi." + pack + ";\r\n public enum " + entype + "{\r\n");
	        for(Entry<String, String> entry : kv.entrySet()) {
	        	b.append(entry.getValue() + "(" + entry.getKey() + "),\r\n");
	        }
	        if(b.length() > 0) {
	        	b = b.replace(b.length()-3, b.length(), ";\r\n");
	        	b.append(" private int value;\r\n");
	        	b.append(String.format(" private %s(int value){\r\nthis.value = value;\r\n} \r\n public int getValue() {\r\n return value;}\r\n}", entype));
	        }
//	        System.err.println(pinyin);
	        String path = Piny.class.getResource("").getPath();
	        String esf = path.replace("/target/test-classes", "/src/test/java") + pack + "/";
	        String old = path.replace("/target/test-classes", "/src/main/java") + "e/";
			path = path.replace("/target/test-classes", "/src/test/java") + "e/";
	        System.out.println(b.toString());
	        File file = new File(path + "\\" + entype + ".java");
	        File oldf = new File(old + "\\" + entype + ".java");
//			if(!file.exists() && !oldf.exists()) {
//				try {
//					file = new File(esf + "\\" + entype + ".java");
//					file.createNewFile();
//					FileWriter writer = new FileWriter(file);
//					writer.write(b.toString());
//					writer.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
			
			
	 }
	 
	 public static void testPinyin2(String enumType, String source, String pack) throws BadHanyuPinyinOutputFormatCombination {
//	        String name = "这个拼音工具还可以ddd";
	        char[] charArray = source.toCharArray();
	        StringBuilder pinyin = new StringBuilder();
	        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
	        //设置大小写格式
	        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
	        //设置声调格式：
	        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	        Map<String, String> kv = new HashMap<String, String>();
	        Map<String, String> kv2 = new HashMap<String, String>();
	        String k = "";
	        String v = "";
	        String v2 = "";
	        boolean start = false;
	        for (int i = 0; i < charArray.length; i++) {
	            //匹配中文,非中文转换会转换成null
	            if (Character.toString(charArray[i]).matches("[\\u4E00-\\u9FA5]+")) {
	                String[] hanyuPinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(charArray[i], defaultFormat);
	                String string = hanyuPinyinStringArray[0];
	                string = string.replace("u:", "v");
	                pinyin.append(string);
	                v += string;
	                start = true;
	                v2 += charArray[i] + "";
	                kv2.put(k, charArray[i] + "");
	            } else {
	            	if(start) {
	            		kv.put(k, v);
	            		kv2.put(k, v2);
	            		k = "";
	            		v = "";
	            		v2 = "";
	            		start = false;
	                }
	            	k += charArray[i];
	                pinyin.append(charArray[i]);
	            }
	        }
	        if(!"".equals(k) && !v.trim().equals("")) {
	        	kv.put(k, v);
	        	kv2.put(k, v2);
	        }
	        String entype = enumType.substring(0, 1).toUpperCase() + enumType.substring(1);//"Vali" + defaultFormat.hashCode();
	        StringBuilder b = new StringBuilder("package com.bj58.fang.hugopenapi." + pack + ";\r\n public enum " + entype + "{\r\n");
	        for(Entry<String, String> entry : kv.entrySet()) {
	        	b.append(entry.getValue() + "(" + entry.getKey() + ",\"" + kv2.get(entry.getKey()) + "\"),\r\n");
	        }
	        if(b.length() > 0) {
	        	b = b.replace(b.length()-3, b.length(), ";\r\n");
	        	b.append(" private int value;\r\nprivate String name;\r\n");
	        	b.append(String.format(" private %s(int value, String name){\r\nthis.value = value;\r\nthis.name = name;\r\n} \r\n public int getValue() {\r\n return value;}\r\n public String getName(){\r\n return name;}\r\n}", entype));
	        }
//	        System.err.println(pinyin);
	        String path = Piny.class.getResource("").getPath();
	        String esf = path.replace("/target/test-classes", "/src/test/java") + pack + "/";
	        String old = path.replace("/target/test-classes", "/src/main/java") + "e/";
			path = path.replace("/target/test-classes", "/src/test/java") + "e/";
	        System.out.println(b.toString());
	        File file = new File(path + "\\" + entype + ".java");
	        File oldf = new File(old + "\\" + entype + ".java");
//			if(!file.exists() && !oldf.exists()) {
//				try {
//					file = new File(esf + "\\" + entype + ".java");
//					file.createNewFile();
//					FileWriter writer = new FileWriter(file);
//					writer.write(b.toString());
//					writer.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
			
			
	 }
}
