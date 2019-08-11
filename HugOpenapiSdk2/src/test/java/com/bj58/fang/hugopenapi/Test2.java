package com.bj58.fang.hugopenapi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bj58.fang.hugopenapi.client.Entity.BrokerZFEntity;
import com.bj58.fang.hugopenapi.client.Entity.HouseEntity;
import com.bj58.fang.hugopenapi.client.Entity.RentedDetailsEntity;
import com.bj58.fang.hugopenapi.client.Entity.ZFEntity;
import com.bj58.fang.hugopenapi.client.Entity.pub.ToKen;
import com.bj58.fang.hugopenapi.client.annotations.Request;
import com.bj58.fang.hugopenapi.client.example.TestClient;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Test2 {

	public static void main(String[] args) {
//		fileTest();
//		initEntity();
//		goujian();
//		buchongZhushi();
//		sameBean(ZFEntity.class, HouseEntity.class);//broker only:brokerid, plats, houseNumFromCo
		enumCreate();
//		System.out.println(JString.getPinyin("我"));
//		shifouMap();
//		s();
		
//		xuliehua();
		
//		test();
//		printMapMethod();
	}

	private static void printMapMethod() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(new File("C:\\Users\\lishaoping\\Desktop\\bean.txt")));
			String line = null;
			System.out.println("StringBuilder builder = new StringBuilder();\r\n");
			List<String> attrList = getAlist(BrokerZFEntity.class);
			StringBuilder setget = new StringBuilder();
			boolean has = false;
			while((line = reader.readLine()) != null) {
				if(!has && line.contains("GET ")) {
					String url = line.substring(line.indexOf("GET") + 4);
					setget.append(String.format("public static final String BRO_ESF_UPDATE = \"%s\";", url));
					has = true;
				}
				if(has) {
					String[] d = line.split("\t");
					
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void test() {
		int id = (int) Math.round(Math.random() * (5 - 1));
		System.out.println(id);
		
	}

	private static void xuliehua() {
		//
		ToKen token = new ToKen("b6ab5dc63efb2ea7c7de1317bd9a9d58", "180def1ba07798ba4447790830358be3", System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 90);
		token.setToken("1f9508dc7f72048afee9d9c61824c690");
		System.out.println(token.toString());
		System.out.println(new File(TestClient.class.getResource("").getFile()).getPath());
		String path = TestClient.class.getResource("").toString();
		String file = path.substring(0, path.indexOf("/com/"));//path.replace("target/classes", "");
		System.out.println(file.substring(file.indexOf("/") + 1).replace("/", "\\"));
		System.out.println(new File(file.substring(file.indexOf("/") + 1).replace("/", "\\")).isDirectory());
		System.out.println(new File(TestClient.class.getResource("").getFile()).exists());;
//		File to = new File("D:\\project\\workspace\\HugOpenapiSdk\\src\\main\\resources\\token");
		//		File[] files = new File("D:\\project\\workspace\\HugOpenapiSdk\\target\\classes").listFiles();//TestClient.class.getResource("").getFile()
//		for(File f : files) {
//			System.out.println(f.getAbsolutePath());
//		}
		String to = new TestClient().getClass().getClassLoader().getResource("").getPath();
		to = to + File.separator + "token";
		System.out.println(new File(to).exists());
		System.out.println();
		to = "D:\\project\\workspace\\HugOpenapiSdk\\src\\main\\resources\\token";
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(to));
			out.writeObject(token);
			out.flush();
			out.close();
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(to));
			Object tok = in.readObject();
			token = (ToKen) tok;
			System.out.println(token.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void buchongZhushi() {
		System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
		System.out.println(Test2.class.getClassLoader().getResource(""));//范围偏大
		System.out.println(System.getProperty("user.dir"));
		System.out.println(Test2.class.getResource(""));//范围更小
		String path = RentedDetailsEntity.class.getResource("").toString();
		path = path.replace("target/classes", "src/main/java");
		System.out.println(path);
		path = path.substring(path.indexOf("/") + 1);
		
		String outentity = JString.class.getResource("").toString();
		outentity = outentity.replace("target/test-classes", "src/test/java") + "outentity/";
		outentity = outentity.substring(outentity.indexOf("/") + 1);
		
		File f = new File(path);
		System.out.println();
		String name = "public void setJiage(Long jiage)";
		if(f.isDirectory()) {
			File[] files = f.listFiles();
			for(File sub : files) {
				try {
					File outf = new File(outentity + sub.getName());
					if(!outf.exists()) {
						outf.createNewFile();
					}
					FileWriter writer = new FileWriter(outf);
					FileReader reader = new FileReader(sub);
					BufferedReader buff = new BufferedReader(reader);
					String line = "";
					Map<String, String> attMap = new HashMap<String, String>();
					StringBuilder buld = new StringBuilder();
					while((line = buff.readLine()) != null) {
						line = line.replace("com.bj58.fang.hugopenapi.client.annoEntity", "com.bj58.fang.hugopenapi.outentity");
						if(line.contains("private")) {
							String left = line.substring(0, line.indexOf("//"));
							String right = line.substring(line.indexOf("//") + 2);
							String[] lefp = left.trim().split("\\s+");
							attMap.put(lefp[1] + " " + lefp[2].substring(0, lefp[2].length() - 1), right);
							System.out.println(line);
							buld.append(line + "\r\n");
							continue;
						}
						boolean hasPrint = false;
						for(String k : attMap.keySet()) {
							if(line.contains(k)) {
								hasPrint = true;
								System.out.println(String.format("/**\r\n*%s\r\n  */\r\n%s", attMap.get(k), line));
								buld.append(String.format("/**\r\n*%s\r\n  */\r\n%s", attMap.get(k), line) + "\r\n");
								break;
							}
						}
						if(!hasPrint) {
							buld.append(line + "\r\n");
							System.out.println(line);
						}
					}
//					new com.bj58.fang.hugopenapi.outentity.BrokerZFEntity().setBianhao("");
					writer.write(buld.toString());
					writer.flush();
					writer.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
//				System.out.println(sub.getAbsolutePath());
//				System.out.println(sub.getName());
//				String tname = sub.getName();
//				try {
//					Class<?> cl = Class.forName("com.bj58.fang.hugopenapi.client.annoEntity." + tname.substring(0, tname.indexOf(".")));
//					
//				} catch (ClassNotFoundException e) {
//					e.printStackTrace();
//				}
			}
		}
//		String path = System.getProperty("user.dir");
//		String path = 
//		path += "";
	}

	private static void initEntity() {
		Class<?> cu = com.bj58.fang.hugopenapi.client.Entity.BrokerZFEntity.class;
		System.out.println(String.format("%s entity = new %s();\r\n", cu.getSimpleName(), cu.getSimpleName()));
		String vn = "";
		while(!"java.lang.Object".equals(cu.getName())) {
			Field[] fs = cu.getDeclaredFields();
			for(Field f : fs) {
				f.setAccessible(true);
				Request r = f.getAnnotation(Request.class);
				if(r == null || !r.needVali()) {
					continue;
				}
				String name = f.getName();
				name = name.substring(0, 1).toUpperCase() + name.substring(1);
				Object val = null;
				String type = f.getType().getSimpleName();
//				System.out.println(type);
				if("mianji".equals(f.getName())) {
					System.out.println();
				}
				if("String".equals(type)) {
					System.out.println(String.format("entity.set%s(\"%s\");", name, ""));
				}else if("int".equals(type) || "Integer".equals(type)) {
					System.out.println(String.format("entity.set%s(%s);", name, 1));
				}else if("long".equals(type) || "Long".equals(type)) {
					System.out.println(String.format("entity.set%s(%sl);", name, 1l));
				}else if("boolean".equals(type) || "Boolean".equals(type)) {
					System.out.println(String.format("entity.set%s(%s);", name, true));
				}else if("double".equals(type) || "Double".equals(type)) {
					System.out.println(String.format("entity.set%s(%sd);", name, 0.0d));
				}
			}
			cu = cu.getSuperclass();
		}
	}

	public Object s () {
		try {
			Method m = Test2.class.getMethod("s");
			System.out.println(m.getReturnType().getName());
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
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

	private static void enum2() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(new File("C:\\Users\\lishaoping\\Desktop\\bean.txt")));
			String line = null;
			while((line = reader.readLine()) != null) {
				String[] data = line.split("\t");
			}
		}catch (Exception e) {
			// TODO: handle exception
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
				data = new String[] {data[0], data[1], data[2], data[3], data[2]};
				Pattern mei = Pattern.compile("(\\d+\\.?\\d*)+");
				if(data.length > 4) {
					String meiju = data[4];
					meiju = meiju.replaceAll("[（\\(].+?[）\\)]", "");
					try {
						Piny.testPinyin2(data[0], meiju, "config");
					} catch (BadHanyuPinyinOutputFormatCombination e) {
						e.printStackTrace();
					}catch (Exception e) {
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
		
//		alist.removeAll(alist2);
		System.out.println(c1.getName() + " contains:" + alist);
		alist2.removeAll(alist);
		System.out.println(c2.getName() + " contains:" + alist2);
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
			List<String> zf1 = getAlist(HouseEntity.class);
			List<String> zf2 = getAlist(ZFEntity.class);
			List<String> zf3 = getAlist(BrokerZFEntity.class);
			while((line = reader.readLine()) != null) {
				String[] data = line.split("\\s+");
				if(zf1.contains(data[0]) || zf2.contains(data[0])
						|| zf3.contains(data[0])) {
					continue;
				}
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
			Pattern p = Pattern.compile("(<(?:\\=)?)((?:-)?\\d+\\.?\\d*)");
			Pattern px = Pattern.compile("(>(?:\\=)?)((?:-)?\\d+\\.?\\d*)");
			Pattern pn = Pattern.compile("\\!(?:\\=)(\\d+\\.?\\d*)");
			Pattern pf = Pattern.compile("([\\[\\(])(\\d+\\.?\\d*)[-,](\\d+\\.?\\d*)([\\]\\)])");
			Pattern mei = Pattern.compile("(\\d+\\.?\\d*)+");
			Pattern xiaP = Pattern.compile("≥(\\d+(?:\\.)?\\d+)");
			Pattern shaP = Pattern.compile("≤(\\d+(?:\\.)?\\d+)");
			Pattern noBian = Pattern.compile("((?:-)?\\d+(?:\\.)?\\d*)[-,]((?:-)?\\d+(?:\\.)?\\d*)");
			
			reader = new BufferedReader(new FileReader(new File("C:\\Users\\lishaoping\\Desktop\\bean.txt")));
			String line = null;
			System.out.println("StringBuilder builder = new StringBuilder();\r\n");
			List<String> attrList = getAlist(BrokerZFEntity.class);
			StringBuilder setget = new StringBuilder();
			while((line = reader.readLine()) != null) {
				String[] data = line.split("\t");
				String name = data[0];
				String type = data[1];
				String vali = data[2];
				String must = data[3];
				String valx = "";
				Matcher mat = p.matcher(vali);
				Matcher matx = px.matcher(vali);
				Matcher matn = pn.matcher(vali);
				Matcher matf = pf.matcher(vali);
//				if(!attrList.contains(name)) {
//					if(name.contains("(")) {
//						name = name.substring(0, name.indexOf("("));
//						data[0] = name;
//						
//					}
//					if(!attrList.contains(name)) {
//						continue;
//					}
//				}
				String xiaxian = "-99877";
				String shangxian = "99877";
				String notEqual = "99877";
				String equalLess = "[";
				String equalMore = "]";
//				if("loudongdanwei".equals(name)) {
//					System.out.println();
//				}
				if(mat.find()) {//上限
					equalLess = "<".equals(mat.group(1)) ? "(" : "[";
					shangxian = mat.group(2);
				}
				if(matx.find()) {
					equalMore = ">".equals(matx.group(1)) ? ")" : "]";
					xiaxian = matx.group(2);
				}
				if(matn.find()) {
					notEqual = matn.group(1);
				}
				Matcher xiam = xiaP.matcher(vali);
				Matcher sham = shaP.matcher(vali);
				if(xiam.find()) {
					xiaxian = xiam.group(1);
				}
				if(sham.find()) {
					shangxian = sham.group(1);
				}
				if(matf.find()) {
					equalLess = matf.group(1);
					equalMore = matf.group(4);
					xiaxian = matf.group(2);
					shangxian = matf.group(3);
				}
				//额外
				Matcher notE = noBian.matcher(vali);
				if(notE.find() && "-99877".equals(xiaxian) && "99877".equals(shangxian)) {
					xiaxian = notE.group(1);
					shangxian = notE.group(2);
				}
				//验证
				valx += String.format(" needVali=%s,", "是".equals(must.trim()) ? "true" : "false");
				String typeName = "";
				if("int".equals(type.toLowerCase())) {
					//整数范围,
					typeName = "Integer";
					valx = fanweiset(valx, xiaxian, shangxian, notEqual, equalMore, equalLess, "numBetween");
				}else if("long".equals(type.toLowerCase())) {
					typeName = "Long";
					valx = fanweiset(valx, xiaxian, shangxian, notEqual, equalMore, equalLess, "numBetween");
				}else if("double".equals(type.toLowerCase())) {
					typeName = "Double";
					valx = fanweiset(valx, xiaxian, shangxian, notEqual, equalMore, equalLess, "numBetween");
				}else if("string".equals(type.toLowerCase())) {
					typeName = "String";
					valx = fanweiset(valx, xiaxian, shangxian, notEqual, equalMore, equalLess, "strLenth");
					valx = valx.replace("-99877", "0");
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
						valx += String.format(" enumVal=\"%s\",", meijuz);
					}
				}
				if(valx.length() > 0) {
					valx = String.format("@Request(%s)", valx.substring(0, valx.length() - 1));
					System.out.println(valx);
				}
				//其他暂时不支持
				System.out.println(String.format("private %s %s;//%s", typeName, data[0], data[2]));
				String aname = data[0];
				aname = aname.substring(0, 1).toUpperCase() + aname.substring(1);
				setget.append(String.format("/**\r\n*%s\r\n*%s\r\n */\r\npublic void set%s(%s %s) {this.%s = %s;}\r\n public %s get%s() {return %s;}\r\n", 
						vali, data.length > 4 ? data[4] : "", aname,typeName, name, name, name, typeName, aname, name));
			}
			System.out.println(setget.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String fanweiset(String valx, String xiaxian, String shangxian, String notEqual, String equalMore, String equalLess, String attr) {
		if(!xiaxian.equals("-99877") || !shangxian.equals("99877")) {
			valx += String.format(" %s=\"%s%s,%s%s\",", attr, equalLess, xiaxian, shangxian,  equalMore);
		}
		if(!notEqual.equals("99877")) {
			valx += String.format(" notEqual=\"%s\",", notEqual);
		}
		return valx;
	}
}
