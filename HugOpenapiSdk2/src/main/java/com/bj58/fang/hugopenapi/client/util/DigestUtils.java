package com.bj58.fang.hugopenapi.client.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestUtils {

	private static DigestUtils util = new DigestUtils();
	private static char[] code = "0123456789abcdef".toCharArray();
	
	public static DigestUtils getInstance() {
		return util;
	}
	
	public String sha1Hex(String text) {
		return digest(text, "SHA1");
	}
	
	public String md5Hex(String text) {
		return digest(text, "MD5");
	}
	
	public String digest(String source, String type) {
		String rs = null;
		try {
			MessageDigest digest = MessageDigest.getInstance(type);
			byte[] bytes = digest.digest(source.getBytes("utf-8"));
			StringBuffer buffer = new StringBuffer(bytes.length * 2);
			for(int i = 0; i < bytes.length; i++) {
				buffer.append(code[bytes[i] >> 4 & 0x0f]);
				buffer.append(code[bytes[i] & 0x0f]);
			}
			rs = buffer.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
