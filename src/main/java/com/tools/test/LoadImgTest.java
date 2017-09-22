package com.tools.test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

/**
 * 
 *java -jar xx.class 参数1 参数2 参数3
 *参数之间没有空格
 * @author lishaoping
 *tools
 *2017年9月9日
 */
public class LoadImgTest {

	private static Pattern p1 = Pattern.compile("<img.*?(>|\\r\\n)", Pattern.CASE_INSENSITIVE);
	private static Pattern p2 = Pattern.compile("src=\"(.*?)\"", Pattern.CASE_INSENSITIVE);
	private static Pattern p3 = Pattern.compile("<img.*?src=\"(.*?)\".*?(>|\\r\\n)", Pattern.CASE_INSENSITIVE);
	private static Pattern p4 = Pattern.compile("(href=\".*?\")", Pattern.CASE_INSENSITIVE);
	private static Pattern p5 = Pattern.compile("((\\s)+(\\r\\n))", Pattern.MULTILINE);
	
	private static Matcher m1 = null;
	private static Matcher m2 = null;
	private static Matcher m4 = null;
	private static String root = "D:\\download\\";
	private static String root2 = "D:\\project\\electronic-business\\web\\src\\main\\webapp\\global\\";
	
	public static void main(String[] args) {
		loadImg(new String[] {"D:\\download\\source.txt", "../../global/img-footer/"});
	}
	
	public static void loadImg(String[] args){
		if(null == args || args.length == 0) {
			System.out.println("no args");
			return;
		}else {
			String file = args[0];
			String imgDir = args[1];
			String url = null;
			
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line = null;
				int i = 1;
				//名字
				long time = System.currentTimeMillis();
				File dir = new File( root + time);
				boolean create_sta = dir.mkdirs();
				//直接在项目中创建文件
				File dir2 = new File( root2 + imgDir.substring(imgDir.indexOf("img-"), imgDir.length() - 1));
				boolean create_sta2 = dir2.mkdirs();
				//清洗文件用：
				File txt = new File(dir + "\\new.txt");
				boolean b = txt.createNewFile();
				System.out.println(b);
				if(!b) return;
				FileOutputStream out = new FileOutputStream(txt);
				String outline = "";
				while((line = reader.readLine()) != null) {
					outline = line;
					m1 = p1.matcher(line);
					if(m1.find()) {
						m2 = p2.matcher(line);
						if(m2.find()) {
							url = m2.group(1);
							String type = url.substring(url.lastIndexOf("."));
							outline = line.replace(url,  imgDir + i + type);
							if(url.startsWith("//")) {
								url = "https:" + url;
							}
							if(create_sta) {
//								testGetFile(url, dir.getAbsolutePath(), i++ + type);
								testGetFile(url, dir2.getAbsolutePath(), i++ + type);
								
							}
							
						}
					}
					m4 = p4.matcher(line);
					if(m4.find()) {
						String href = m4.group(1);
						outline = line.replace(href, " ");
					}
					out.write((outline + "\r\n").getBytes() );
				}
				out.flush();
				out.close();
				reader.close();
				System.out.println("success");
//				String ct = FileUtils.readFileToString(txt, "UTF-8").replaceAll("(\\s)+(\\r\\n)", "\\r\\n");
//				FileUtils.writeStringToFile(txt, ct, "UTF-8", false);
//				System.out.println(ct);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	public static void testGetFile(String url, String dir, String name) {
		try {
			URL u = new URL(url);
			URLConnection con = u.openConnection();
			InputStream in = con.getInputStream();
//			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] cache = new byte[1024];
			int len = 0;
			File file = new File(dir + "\\" + name);
			boolean b = file.createNewFile();
			if(!b) return;
			FileOutputStream out = new FileOutputStream(file);
			while((len = in.read(cache)) > 0) {
//				out.write(cache, 0, len);
				out.write(cache, 0, len);
			}
			out.flush();
			out.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

