package com.li.shao.ping.AA;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * TODO 
 * @author lishaoping
 * @date 2019年10月15日
 * @file DESUtil
 */
public class AESUtil {

	private static final String KEY_ALGORITHM = "AES";          
	 
	private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";    
	public static final String SIGN_ALGORITHMS = "SHA1PRNG";
	 //密钥
    private static byte[] key = "4ebuyproauthinfo".getBytes();  
    private static SecretKey secretKey = null; 
	
	static {
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		    KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM, "BC"); 
		    SecureRandom random = SecureRandom.getInstance(SIGN_ALGORITHMS);
            random.setSeed(key);//"4ebuyproauthinfo".getBytes(ENCODING)
		    kg.init(128, random);
			secretKey = new SecretKeySpec(key,KEY_ALGORITHM);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String encrypt(String str) {
	   try {
		   Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC"); 
		   cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		   byte[] data = cipher.doFinal(str.getBytes());
		   return URLEncoder.encode(new String(Base64.encodeBase64(data)),"UTF-8"); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	   return null;
	}
	
	public static String decrypt(String str) {
       Cipher cipher;
		try {
			byte[] decoData = Base64.decodeBase64(URLDecoder.decode(str, "UTF-8"));
			cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
			cipher.init(Cipher.DECRYPT_MODE, secretKey); 
	        return new String(cipher.doFinal(decoData));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "error";
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException{ 

//       
//		System.out.println(encrypt(args[0]));
        System.out.println(encrypt(args[0]));
//        System.out.println(decrypt("patbc68yDaGi8NW5mjddfg%3D%3D"));
      } 
}
		 
