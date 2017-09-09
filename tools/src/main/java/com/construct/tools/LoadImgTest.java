package com.construct.tools;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	private static Matcher m1 = null;
	private static Matcher m2 = null;
	private static String root = "D:\\download\\";
	
	public static void main(String[] args) {
		loadImg(new String[] {"D:\\download\\source.txt"});
	}
	
	public static void loadImg(String[] args){
		if(null == args || args.length == 0) {
			System.out.println("no args");
			return;
		}else {
			String file = args[0];
			String url = null;
			
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line = null;
				int i = 1;
				//名字
				long time = System.currentTimeMillis();
				File dir = new File( root + time);
				boolean create_sta = dir.mkdirs();
				//清晰文件用：
				File txt = new File(dir + "\\new.txt");
				boolean b = txt.createNewFile();
				System.out.println(b);
				if(!b) return;
				FileOutputStream out = new FileOutputStream(txt);
				while((line = reader.readLine()) != null) {
					out.write((line + "\r\n").getBytes() );
					m1 = p1.matcher(line);
					if(m1.find()) {
						m2 = p2.matcher(line);
						if(m2.find()) {
							url = m2.group(1);
							if(create_sta) {
								String type = url.substring(url.lastIndexOf("."));
								testGetFile(url, dir.getAbsolutePath(), i++ + type);
							}
						}
					}
				}
				out.flush();
				out.close();
				reader.close();
				System.out.println("success");
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
