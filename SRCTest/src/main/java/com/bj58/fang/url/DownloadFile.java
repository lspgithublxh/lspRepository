package com.bj58.fang.url;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.bj58.biz.utility.crypto.Base64;

public class DownloadFile {

	public static void main(String[] args) {
//		while(true) {
//			System.out.println("hello");
//		}
		download();
	}

	private static void download() {
//		byte[] img = downloadFile("http://pic.58.com/dwater/fang/big/n_v234123ae9c9054d4194842edbfe7e3f10.jpg");
//		byte[] img = downloadFile2("http://pic.58.com/dwater/fang/big/n_v223e5fd068c9f41c082e3cbbb22524f68.jpghttp://pic.58.com/dwater/fang/big/n_v2263d2b44eb7747ac9556dd749d405186.jpg");
		byte[] img = downloadFile2("file:///D:/download/abc.jpg");
		System.out.println(Base64.encode(img));
		
	}
	
	 public static byte[] downloadFile2(String httpUrl) {
	    	URL url = null;
	    	InputStream in = null;
	    	ByteArrayOutputStream out2 = new ByteArrayOutputStream();
	    	BufferedOutputStream buffer = new BufferedOutputStream(out2);
	    	try {
	    		url = new URL(httpUrl);
				URLConnection con = url.openConnection();
				in = con.getInputStream();
				byte[] b = new byte[1024];
				int len = 0;
				while((len = in.read(b)) > 0) {
					buffer.write(b, 0, len);
				}
				buffer.flush();
				buffer.close();
				out2.flush();
				out2.close();
				in.close();
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}finally {
	    		try {
					buffer.flush();
					buffer.close();
					out2.flush();
					out2.close();
					if(in != null) {
						in.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	}
	    	return out2.toByteArray();
	    }
	
	public static byte[] downloadFile(String url) {
    	byte[] a = null;
    	try {
    		HttpClient httpClient = new HttpClient();
    		InputStream inputStream = null;
    		GetMethod getMethod = new GetMethod(url);
    		getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            int returnCode = httpClient.executeMethod(getMethod);
            if (returnCode != HttpStatus.SC_OK) {
                throw new HttpException("call url:" + url + " return [" + returnCode + "]");
            }
            inputStream = getMethod.getResponseBodyAsStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len = 0;
            while((len = inputStream.read(b)) > 0) {
            	out.write(b, 0, b.length);
            }
            return out.toByteArray();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return a;
    	
    }

}
