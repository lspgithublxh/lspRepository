package com.bj58.fang.hugopenapi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bj58.fang.hugopenapi.client.Entity.ZFEntity;
import com.bj58.fang.hugopenapi.client.enumn.pub.UserIdType;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Test {

	public static void main(String[] args) {
//		fileTest();
//		goujian();
//		sameBean(ESFEntity.class, HouseEntity.class);//broker only:brokerid, plats, houseNumFromCo
//		enumCreate();
//		System.out.println(JString.getPinyin("我"));
//		shifouMap();
		s();
	}

	public static Object s () {
		try {
			Method m = Test.class.getMethod("s");
			System.out.println(m.getReturnType().getName());
			System.out.println(UserIdType.jingjigongsiUser.getClass().getName());
			Object ie = UserIdType.jingjigongsiUser;
			Class cls = UserIdType.jingjigongsiUser.getClass();
			Method[] me = cls.getDeclaredMethods();
			Method mee = cls.getDeclaredMethod("getValue");
			System.out.println(mee.invoke(ie));
			for(Method met : me) {
				if("getValue".equals(met.getName())) {
					Object rr = met.invoke(ie);
					System.out.println(rr.toString());
				}
			}
			
//			Enum e = (Enum) ie;
//			System.out.println(e.toString());
//			System.out.println(e.name());
//			System.out.println(e.ordinal());
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 1;
	}

	private static void shifouMap() {
		BufferedReader reader;
		try {Integer x = (Integer) 2;
			List<String> alist = getAlist(ZFEntity.class);
//			List<String> alist2 = getAlist(c2);
			
			reader = new BufferedReader(new FileReader(new File("C:\\Users\\lishaoping\\Desktop\\bean.txt")));
			String line = null;
			Pattern p = Pattern.compile("（");
			System.out.println("Map<String, Boolean> keyNeedMap = new HashMap<String, Boolean>();");
			while((line = reader.readLine()) != null) {
				String[] data = line.split("\t");
				Pattern mei = Pattern.compile("(\\d+\\.?\\d*)+");
				if(!alist.contains(data[0])) {
					continue;
				}
				if(data.length > 3) {
					String shifou = data[3];
					String sf = shifou.trim().substring(0, 1);
					if("是".equals(sf)) {
//						String defaultv = "int".equals(data[1]) ? "1" : "long".equals(data[1]) ? "1l" : 
//							 "double".equals(data[1]) ? "1.0d" :  "string".equals(data[1]) ? "\"value\"" : "\"elsevalue\"";
						System.out.println(String.format("keyNeedMap.put(\"%s\", %s);", data[0], true));
					}else{
//						String defaultv = "int".equals(data[1]) ? "1" : "long".equals(data[1]) ? "1l" : 
//							 "double".equals(data[1]) ? "1.0d" :  "string".equals(data[1]) ? "\"value\"" : "\"elsevalue\"";
						System.out.println(String.format("keyNeedMap.put(\"%s\", %s);", data[0], false));
					}
				}
//				System.out.println(String.format("private %s %s;//%s", data[1], data[0], data[2]));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private static void enumCreate() {
		//
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(new File("C:\\Users\\lishaoping\\Desktop\\bean.txt")));
			String line = null;
			Pattern p = Pattern.compile("（");
			while((line = reader.readLine()) != null) {
				String[] data = line.split("\t");
				Pattern mei = Pattern.compile("(\\d+\\.?\\d*)+");
				if(data.length > 4) {
					String meiju = data[4];
					meiju = meiju.replaceAll("[（\\(].+?[）\\)]", "");
					try {
						Piny.testPinyin(data[0], meiju, "config/");
					} catch (BadHanyuPinyinOutputFormatCombination e) {
						e.printStackTrace();
					}
//					Matcher meix = mei.matcher(meiju);
//					String meijuz = "";
//					while(meix.find()) {
//						meijuz += meix.group(1) + ",";
//					}
//					if(meijuz.endsWith(",")) {
//						meijuz = meijuz.substring(0, meijuz.length() - 1);
//					}
				}
//				System.out.println(String.format("private %s %s;//%s", data[1], data[0], data[2]));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private static void sameBean(Class c1, Class c2) {
		List<String> alist = getAlist(c1);
		List<String> alist2 = getAlist(c2);
//		alist2.retainAll(alist);
//		Field[] fs = c2.getDeclaredFields();
//		for(Field f : fs) {
//			if(alist2.contains(f.getName())) {
//				Class<?> tt = f.getType();
//				System.out.println("private " + tt.getSimpleName() + " " + f.getName() + ";");
//			}
//		}
		System.out.println("--------------");
		
		alist.removeAll(alist2);
		System.out.println(c1.getName() + " contains:" + alist);
//		alist2.removeAll(alist);
//		System.out.println(c2.getName() + " contains:" + alist2);
	}

	private static List<String> getAlist(Class c1) {
		List<String> alist = new ArrayList<String>();
		Field[] fs = c1.getFields();
		fs = c1.getDeclaredFields();
		
		for(Field f : fs) {
			alist.add(f.getName());
		}
		return alist;
	}

	private static void fileTest() {
		//
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(new File("C:\\Users\\lishaoping\\Desktop\\bean.txt")));
			String line = null;
			
			while((line = reader.readLine()) != null) {
				String[] data = line.split("\\s+");
				System.out.println(String.format("private %s %s;//%s", data[1], data[0], data[2]));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void goujian() {
		//
		BufferedReader reader;
		try {
			Pattern p = Pattern.compile("(<(?:\\=)?)(\\d+\\.?\\d*)");
			Pattern px = Pattern.compile("(>(?:\\=)?)(\\d+\\.?\\d*)");
			Pattern pn = Pattern.compile("\\!(?:\\=)(\\d+\\.?\\d*)");
			Pattern pf = Pattern.compile("([\\[\\(])(\\d+\\.?\\d*)[-,](\\d+\\.?\\d*)([\\]\\)])");
			Pattern mei = Pattern.compile("(\\d+\\.?\\d*)+");
			reader = new BufferedReader(new FileReader(new File("C:\\Users\\lishaoping\\Desktop\\bean.txt")));
			String line = null;
			System.out.println("StringBuilder builder = new StringBuilder();\r\n");
			while((line = reader.readLine()) != null) {
				String[] data = line.split("\t");
				String name = data[0];
				String type = data[1];
				String vali = data[2];
				String must = data[3];
				String valx = "";
				int lent = valx.length();
				Matcher mat = p.matcher(vali);
				Matcher matx = px.matcher(vali);
				Matcher matn = pn.matcher(vali);
				Matcher matf = pf.matcher(vali);
				String xiaxian = "0";
				String shangxian = "99877";
				String notEqual = "99877";
				boolean equalLess = true;
				boolean equalMore = true;
				if(mat.find()) {//上限
					equalLess = "<".equals(mat.group(1)) ? false : true;
					shangxian = mat.group(2);
				}
				if(matx.find()) {
					equalMore = ">".equals(matx.group(1)) ? false : true;
					xiaxian = matx.group(2);
				}
				if(matn.find()) {
					notEqual = matn.group(1);
				}
				if(matf.find()) {
					equalLess = "(".equals(matf.group(1)) ? false : true;
					equalMore = ")".equals(matf.group(4)) ? false : true;
					xiaxian = matf.group(2);
					shangxian = matf.group(3);
				}
				if("int".equals(type.toLowerCase())) {
					valx += "new ValidateKV(ValidateType.IS_INT, \"1\"),";
					//整数范围,
					valx = fanweiset(valx, xiaxian, shangxian, notEqual, equalMore, equalLess);
				}else if("long".equals(type.toLowerCase())) {
					valx += "new ValidateKV(ValidateType.longType, \"1\"),";
					valx = fanweiset(valx, xiaxian, shangxian, notEqual, equalMore, equalLess);
				}else if("double".equals(type.toLowerCase())) {
					valx += "new ValidateKV(ValidateType.doubleType, \"1\"),";
					valx = fanweiset(valx, xiaxian, shangxian, notEqual, equalMore, equalLess);
				}else if("string".equals(type.toLowerCase())) {
					if(!shangxian.equals("99877")) {
						String between = equalLess ? equalMore ? "Between" : "Betweenl" : equalMore ? "Betweenr" : "Betweenlr";
						valx += String.format("new ValidateKV(ValidateType.%s, \"%s,%s\"),", between, xiaxian, shangxian);
					}
				}
				//枚举
				if(data.length > 4) {
					String meiju = data[4];
					Matcher meix = mei.matcher(meiju);
					String meijuz = "";
					while(meix.find()) {
						meijuz += meix.group(1) + ",";
					}
					if(meijuz.endsWith(",")) {
						meijuz = meijuz.substring(0, meijuz.length() - 1);
						valx += String.format("new ValidateKV(ValidateType.enumType, \"%s\"),", meijuz);
					}
				}
				String strToLen = "";
				if(valx.endsWith(",")) {
					valx = " ," + valx.substring(0, valx.length() - 1);
					if("string".equals(type.toLowerCase())) {
						strToLen = ".length()";
					}
				}
				//其他暂时不支持
				String str = String.format("String %s = ValidateUtil.getInstance().validate(\"%s\", entity.get%s()%s, %s%s);\r\n",
						name, name, name.substring(0, 1).toUpperCase() + name.substring(1), strToLen,"是".equals(must) ? false : true, valx);
				StringBuilder bu = new StringBuilder();
				bu.append(str);
				bu.append(String.format("builder.append(%s);\r\n", name));
				System.out.println(bu.toString());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String fanweiset(String valx, String xiaxian, String shangxian, String notEqual, boolean equalMore, boolean equalLess) {
		if(shangxian != "99877") {
			valx += String.format(" new ValidateKV(ValidateType.%s, \"%s\"),", equalLess ? "equalOrLess" : "less", shangxian);
		}
		if(xiaxian != "0") {
			valx += String.format(" new ValidateKV(ValidateType.%s, \"%s\"),", equalMore ? "equalOrGreater" : "greater", xiaxian);
		}
		if(notEqual != "99877") {
			valx += String.format(" new ValidateKV(ValidateType.NOTequal, \"%s\"),", notEqual);
		}
		return valx;
	}
}
