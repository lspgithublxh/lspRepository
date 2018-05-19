package com.bj58.fang.url;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class GetFile {

	public static void main(String[] args) {
		try {
//			URL url = new URL("http://wiki.58corp.com/images/3/32/WMB%E5%B9%B3%E5%8F%B0%E4%BD%BF%E7%94%A8%E6%89%8B%E5%86%8Cv0.1.pdf");
//			URL url = new URL("http://wiki.58corp.com/images/b/bd/WMB%E5%AE%A2%E6%88%B7%E7%AB%AF0.0.9%E7%89%88%E6%9C%ACJava_API%E6%96%87%E6%A1%A3.pdf");
//			URL url = new URL("http://wiki.58corp.com/images/0/03/IMC%E6%9F%A5%E8%AF%A2%E6%9D%A1%E4%BB%B6pdf.pdf");
			URL url = new URL("http://wiki.58corp.com/images/4/4f/Yun%E5%B9%B3%E5%8F%B0%E4%BD%BF%E7%94%A8%E6%89%8B%E5%86%8C.pdf");
			URLConnection con = url.openConnection();
			InputStream in = con.getInputStream();
			byte[] b = new byte[1024];
			File file = new File("D:\\私有云.pdf");
			file.createNewFile();
			int len = 0;
			OutputStream out = new FileOutputStream(file);
			while((len = in.read(b)) > 0) {
				out.write(b, 0, len);
			}
			out.flush();
			out.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
