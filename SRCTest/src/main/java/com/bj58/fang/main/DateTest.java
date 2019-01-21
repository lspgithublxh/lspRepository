package com.bj58.fang.main;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.bj58.fang.unity.service.unitycmc.contract.entitys.UnityLocal;
import com.bj58.fang.unity.service.unitycmc.contract.ibll.IUnityLocalService;
import com.bj58.spat.scf.client.proxy.builder.ProxyFactory;

public class DateTest{
	
	public static void main(String[] args) throws Exception {
	
//			SCFInit.initScfKey("D:\\opt\\wf\\dict\\scfkey.key");
//			SCFInit.initScfKey("D:/opt/wf/fang.vip.58.com/scfkey.key");
	//		SCFInit.init("D:\\opt\\wf\\dict\\scf.config");
	//		LocalEntity area = LocalService.getInstance().GetLocalByID(1);
	//		System.out.println(area.getDirName());
			//
//			String url = "tcp://unitycmc/UnityLocalRelationService";
//			IUnityLocalRelationService service = (IUnityLocalRelationService) ProxyFactory
//					.create(IUnityLocalRelationService.class, url);
//			int cityid = 1;
//			cityid = service.getByWubaCityId(cityid).getUnityLocalId();
//			System.out.println(cityid);
			//
//			ILocalService localService = ProxyFactory.create(ILocalService.class, "tcp://cmcp/LocalService");
//			LocalEntity local = null;
//			
//			try {
//				local = localService.GetLocalByID(2);//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			System.out.println(local);
//			test();
//			urlEncode();
//		int[] s = new int[] {172,189,244,54,206,192,73,158,116,149,64,109,110,150,200,113,79,158,54,50,223,191,251,11,194,185,139,219,87,133,191,115,48,121,213,59,95,101,239,143};
		int[] s = new int[] {244,12,183,11,107,196,78,200,160,217,81,76,90,57,197,40,160,155,74,68,117,46,2,123,101,98,34,172,235,67,200,162,161,67,190,161,148,29,104,213};
		byte[] xs = new byte[s.length];
		for(int i = 0; i < s.length; i++) {
			if(s[i] > 127) {
				xs[i] = (byte) (s[i] - 256);
			}else {
				xs[i] = (byte) s[i];;
			} 
		}
		System.out.println(new String(xs));//方法是正确的
		System.out.println(new String(com.bj58.spat.scf.server.secure.Base64.encodeBase64(xs)));
//		System.out.println(authEncode("39424825471248"));
		System.out.println("----------------");
//		String url = new URLDecoder().decode("wbmain://jump/house/shareChart?params=%7B%22slogan%22%3A%22%5Cu6211%5Cu89c9%5Cu5f9758%5Cu4e0a%5Cu8fd9%5Cu5957%5Cu623f%5Cu5f88%5Cu4e0d%5Cu9519%5Cuff01%22%2C%22fullPath%22%3A%221%2C8%22%2C%22location%22%3A%22%5Cu5317%5Cu4eac%5Cu00b7%5Cu671d%5Cu9633%5Cu00b7%5Cu5927%5Cu5c71%5Cu5b50%22%2C%22title%22%3A%22%5Cu6574%5Cu79df+%5Cu4e2a%5Cu4eba%5Cu51fa%5Cu79df15%5Cu53f7%5Cu7ebf%5Cu5d14%5Cu5404%5Cu5e84%5Cu5730%5Cu94c1%5Cu65c1%5Cu9ad8%5Cu6863%5Cu697c%5Cu623f%5Cuff0c%5Cu72ec%5Cu7acb%5Cu53a8%5Cu536b%5Cuff0c%5Cu623f%5Cu79df%5Cu6708%5Cu4ed8%5Cuff01%22%2C%22huxing%22%3A%221%5Cu5ba40%5Cu53851%5Cu536b%22%2C%22area%22%3A%2228%5Cu33a1%22%2C%22price%22%3A%221200%22%2C%22priceUnit%22%3A%22%5Cu5143%5C%2F%5Cu6708%22%2C%22tips%22%3A%22%5Cu623f%5Cu6e90%5Cu6765%5Cu81ea58%5Cu540c%5Cu57ce%22%2C%22qrCodeUrl%22%3A%22https%3A%5C%2F%5C%2Fhouserentapp.58.com%5C%2Fwechat%5C%2FApi_get_detail_qrcode%3FinfoId%3D33551762570059%26listName%3Dzufang%26localName%3Dbj%26fullPath%3D1%2C8%26signature%3D170c4dfb052a73ca4bd5f990a829522b%22%2C%22houseImg%22%3A%22https%3A%5C%2F%5C%2Fpic5.58cdn.com.cn%5C%2Fanjuke_58%5C%2F38a8ba0090b8ee2e7e7498184ea5bf5d%3Fw%3D750%26h%3D562%26crop%3D1%22%7D", "ISO-8859-1");
//		System.out.println(url);
//		String rs = "941cVesOF8Vm12vVcJQuICSOojcE9ZHAaU%252FZUvYfu76ZJR1MGZwCoDOqGA";
//		String rs = "0ca5hb4yZaMzcTimhPjNtcKwnErifhB2jENQd4x7g9MEm99VDILeLXE5bg";
//		String rs = "1111rL30Ns7ASZ50lUBtbpbIcU+eNjLfv/sLwrmL21eFv3MwedU7X2Xvjw";
//		String rs = "32029Ay3C2vETsig2VFMWjnFKKCbSkR1LgJ7ZWIirOtDyKKhQ76hlB1o1Q";
		
//		String rs = "6d22z6WKOnTXxJyU7I0RXKRvzHS/dKXFJV5Z2VPTf5ZNxaQ0vp98ct5PJw";
		String rs = "427eBAD5eHcn8CCBtJWe401Thv6SHbvjLgnOy9InQWj+Pg";
		rs = "eecd8Q0MY8gl7CGNrZ2USykU%2BqN7BvqlYhLdBtDqVcU%2FRplvVSdAi7GJrQ";
		rs = "ece4nTyFU44%2BBBSKIPUz4FPiyWp6SEQ0OE%252FE0NqUN1yvUsdd6kttUjA%2BWw";
//		rs = "455eyNcPw2EHF2bmNSbMSvkuJh5aQJxzaS4Tx%252BsaA%252BenLgeOamlINrcmOg";
//		rs = authEncode("sdfsf");//39424825471246   sdfsf这个不能解密
//		System.out.println(rs + "--------xxx");
		System.out.println(authDecode(rs) + " --------");
		
//		System.out.println(authDecode("5d7bJwMo0wdLLpIpxkaQiG+lv4kQEd3KJi/Uz+WhUpu4vQ"));
//		System.out.println(Math.round(System.currentTimeMillis() / 1000));
//		System.out.println(System.currentTimeMillis());rs
//		System.out.println(System.nanoTime());
//		long mstime = System.currentTimeMillis();
//		double seconds = mstime / 1000;
//		double decimal = (mstime - (seconds * 1000)) / 1000d;
//		System.out.println(decimal + " " + seconds);
//		System.out.println("--------------------\\\\");
//		long mstime = System.currentTimeMillis();
//		long seconds = mstime / 1000 / 1000;
//		double decimal = (mstime - (seconds * 1000 * 1000)) / 1000d / 1000d;
//		System.out.println(String.format("%.8f", decimal));
//		System.out.println(String.format("%.8f", decimal) + " " + mstime / 1000);
	}//1524818927

	@Test
	public void test1() {
		String url = "tcp://unitycmc/UnityLocalRelationService";
		IUnityLocalService service = ProxyFactory.create(IUnityLocalService.class, url);
		try {
			List<UnityLocal> list = service.getCityList();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static String authEncode(String source) {
		String rs = "";
		String result = "";
		try {//455eyNcPw2EHF2bmNSbMSvkuJh5aQJxzaS4Tx%252BsaA%252BenLgeOamlINrcmOg
			rs = new URLDecoder().decode(source, "UTF-8");
			String key = md5("user");
			String keya = md5(key.substring(0, 16));
			String keyb = md5(key.substring(16, 32));
			String time = md5("");//0.11632600 1524816801
//			long mstime = System.currentTimeMillis();
//			long seconds = mstime / 1000 / 1000;
//			double decimal = (mstime - (seconds * 1000 * 1000)) / 1000d / 1000d;
//			System.out.println(String.format("%.8f", decimal));
//			String res = String.format("%.8f", decimal) + " " + mstime / 1000;
//			System.out.println(res);
//			String time = md5(res);//"0.69956401 1524825230"
			String keyc =  time.substring(time.length() - 4);//keyc本身没用
			
			String cryptkey = keya + md5(keya + keyc);
			int key_length = cryptkey.length();
			long time2 = Math.round(System.currentTimeMillis() / 1000) + 600l;
//			long time2 = 1524825230;
			String pass = md5(rs + keyb);
			rs = String.format("%s%s%s", time2, pass.substring(0, 16), rs);
//			byte[] baseRs = Base64.decode(rs.substring(4));
//			byte[] baseRs = Base64.decodeBase64(rs.substring(4).getBytes("utf-8"));
//			int[] baseRs2 = new int[baseRs.length];
//			for(int i = 0; i < baseRs.length; i++) {
//				if(baseRs[i] < 0) {
//					baseRs2[i] = 256+ baseRs[i];
//				}else {
//					baseRs2[i] = baseRs[i];
//				}
//			}
//			rs = new String(baseRs);
//			int string_length = baseRs2.length;
			int string_length = rs.length();
			int [] rndkey = new int[256];
			for(int i = 0 ; i <= 255; i++) {
				rndkey[i] = cryptkey.charAt(i % key_length);//自动ascll
			}
			int[] box = new int[256];
			for(int i = 0; i <= 255; i++) {
				box[i] = i;
			}
			for(int i = 0, j = 0; i < 256; i++) {
				j = (j + box[i] + rndkey[i]) % 256;
				int tmp = box[i];
				box[i] = box[j];
				box[j] = tmp;
			}
			//看box rs即可
			for(int a = 0, i = 0, j = 0; i < string_length; i++) {
				a = (a + 1) % 256;
				j = (j + box[a]) % 256;
				int tmp = box[a];
				box[a] = box[j];
				box[j] = tmp;
				result += (char)(rs.charAt(i) ^ (box[(box[a] + box[j]) % 256]));
			}
			byte[] xs = new byte[result.length()];
			int i = 0;
			for(char c : result.toCharArray()) {
				System.out.print(((int) c) + ",");
					if(((int) c) > 127) {
						xs[i] = (byte) (((int) c) - 256);
					}else {
						xs[i] = (byte) ((int) c);;
					} 
					i++;
				
			}
			System.out.println();
			return String.format("%s%s", keyc, new String(com.bj58.spat.scf.server.secure.Base64.encodeBase64(xs)).replace("=", ""));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public static String md5(String source) {
		String rs = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] bytes = digest.digest(source.getBytes("utf-8"));
			StringBuffer buffer = new StringBuffer(bytes.length * 2);
			char[] code = "0123456789abcdef".toCharArray();
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
	
	public static String authDecode(String source) {
		String rs = "";
		try {//455eyNcPw2EHF2bmNSbMSvkuJh5aQJxzaS4Tx%252BsaA%252BenLgeOamlINrcmOg
			System.out.println(source + "-------yyy");
			
			rs = source;
			rs = new URLDecoder().decode(source, "UTF-8");
			System.out.println(rs + "-------yyy");
//			rs = new URLDecoder().decode(rs, "UTF-8");
			System.out.println(rs + "-------yyy");
			String key = md5("user");//php:ee11cbb19052e40b07aac0ca060c23ee
			String keya = md5(key.substring(0, 16));
			String keyb = md5(key.substring(16, 32));
			String keyc = rs.substring(0, 4);
			String cryptkey = keya + md5(keya + keyc);
			int key_length = cryptkey.length();
			System.out.println(key_length);
//			byte[] baseRs = com.bj58.biz.utility.crypto.Base64.decode(rs.substring(4));
//			System.out.println(new String(baseRs));
			String sb = Base64.getMimeEncoder().encodeToString(rs.substring(4).getBytes("utf-8"));
			char[] sbx = sb.toCharArray();
			int[] ix = new int[sbx.length];
			for(int i = 0; i < sbx.length; i++) {
				ix[i] = sbx[i];
			}
			System.out.println(rs + "-------yyy");
			byte[] sx = com.bj58.spat.scf.server.secure.Base64.decodeBase64(rs.substring(4).getBytes("utf-8"));
			int[] sx2 = new int[sx.length];
			for(int i = 0; i < sx.length; i++) {
				if(sx[i] < 0) {
					sx2[i] = 256+ sx[i];
				}else {
					sx2[i] = sx[i];
				}
			}
			String s = new String(sx);		
			System.out.println(s);
			System.out.println();
			rs = s;
			System.out.println(s.length());
//			int string_length = rs.length();
			int string_length = sx2.length;
			int [] rndkey = new int[256];
			for(int i = 0 ; i <= 255; i++) {
				rndkey[i] = cryptkey.charAt(i % key_length);//自动ascll
			}
			int[] box = new int[256];
			for(int i = 0; i <= 255; i++) {
				box[i] = i;
			}
			for(int i = 0, j = 0; i < 256; i++) {
				j = (j + box[i] + rndkey[i]) % 256;
				int tmp = box[i];
				box[i] = box[j];
				box[j] = tmp;
			}
			String result = "";
			for(int a = 0, i = 0, j = 0; i < string_length; i++) {
				a = (a + 1) % 256;
				j = (j + box[a]) % 256;
				int tmp = box[a];
				box[a] = box[j];
				box[j] = tmp;
				result += (char)(sx2[i] ^ (box[(box[a] + box[j]) % 256]));
//				result += (char)(rs.charAt(i) ^ (box[(box[a] + box[j]) % 256]));
				System.out.print(sx2[i]);
				System.out.print(",");
			}
			System.out.println(result);
			long timeDiff = System.currentTimeMillis() / 1000 - Long.valueOf(result.substring(0,  10));
			if(result.length() > 26 && timeDiff < 0 && result.substring(10, 26).equals(md5(result.substring(26) + keyb).substring(0, 16))) {//前10位是加密时间，所以可以判断失效时间---从而自动失效
				System.out.println(System.currentTimeMillis() / 1000);
				System.out.println(result.substring(0, 10));
				return result.substring(26);
			}else {
				return "";
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return rs;
	}
	
	private static void urlEncode() {//455eyNcPw2EHF2bmNSbMSvkuJh5aQJxzaS4Tx%252BsaA%252BenLgeOamlINrcmOg
		String str = null;
		try {//941cVesOF8Vm12vVcJQuICSOojcE9ZHAaU%2FZUvYfu76ZJR1MGZwCoDOqGA
			str = new URLDecoder().decode("941cVesOF8Vm12vVcJQuICSOojcE9ZHAaU%252FZUvYfu76ZJR1MGZwCoDOqGA", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(str);
		String key = md5("user");
		System.out.println(key);
		String keya = md5(key.substring(0, 16));
		String keyb = md5(key.substring(16, 32));
		System.out.println(keya + "-----" + keyb);
		String keyc = str.substring(0, 4);
		String cryptkey = keya + md5(keya + keyc);
		int key_length = cryptkey.length();
		byte[] s = null;
		try {
			System.out.println("-------------");
			System.out.println(str.substring(4));
//			byte[] s = com.bj58.biz.utility.crypto.Base64.decode(str.substring(4));//
//			String s = Base64.getMimeEncoder().encodeToString(str.substring(4).getBytes("utf-8"));
//			System.out.println(s);
			s = com.alibaba.fastjson.util.Base64.decodeFast(str.substring(4));
			System.out.println(new String(s, "UTF-8"));
//			s = java.util.Base64.getDecoder().decode(str.substring(4));
//			System.out.println(new String(s));
			s = com.bj58.spat.scf.server.secure.Base64.decodeBase64(str.substring(4));//
			System.out.println(new String(s));
//			s = com.bj58.wf.mvc.utils.Base64.decode(str.substring(4));//
//			System.out.println(new String(s));
			int [] sc = new int[2];
			sc[0] = 'A';
			System.out.println(sc[0]);
			System.out.println((char)sc[0]);
			System.out.println('A' ^ 14);
			System.out.println("sdfs" + 'A');
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}



	private static void test() {
		Calendar canlendar = Calendar.getInstance();
		System.out.println(canlendar.get(canlendar.YEAR));
		canlendar.add(Calendar.DAY_OF_YEAR, 0);
		long t = canlendar.getTime().getTime();
		System.out.println(t);
		long t2 = Math.round((((double) t) / 1000));
		System.out.println(t2);
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		System.out.println(format.format(canlendar.getTime()));
		String str2 = format.format(new Date(t2));
		System.out.println(str2);
		
		System.out.println(String.format("%s----%s-------%s", "a", "b", "c"));
		System.out.println("---------------------");
//		Date d = new Date("May 11, 2017 10:54:16 PM");
//		try {
//			Date da = format.parse("May 11, 2017 10:54:16 PM");
//			System.out.println(format.format(da));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy K:m:s a",Locale.ENGLISH);
		Date da = null;
		try {
			da = sdf.parse("May 11, 2017 10:54:16 PM");
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy");
			System.out.println(format2.format(new Date()));
			System.out.println(format2.format(da));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(format.format(da));
		Calendar canlendar2 = Calendar.getInstance();
		long rs = Math.round((((double) (canlendar2.getTime().getTime() - da.getTime())) / 1000 / 60 / 60 / 24));
		System.out.println(String.format("%s", rs));
		
		//
		System.out.println(String .format("%.2f",5.666565));
		
		
		Pattern intP = Pattern.compile("^(\\d+).*");
		Pattern intP2 = Pattern.compile("^(\\d+\\.?\\d*)$");
		Matcher m = intP.matcher("3333.4单位");
		Matcher m2 = intP2.matcher("3333");
		System.out.println(m.find());
		System.out.println(m.group(1));
		System.out.println(m2.find());
		System.out.println(m2.group(1));
		Double d = Double.valueOf(m2.group(1));
		Float d2 = Float.valueOf(m2.group(1));
		System.out.println(d2);
		System.out.println(d);
		System.out.println(33749641455942l);
		JSONObject rsx = new JSONObject();
		rsx.put("action", "3434444343434");
		rsx.put("a", 3434444343434l);
		rsx.put("b", 34);
		System.out.println(rsx.getLongValue("action"));
		System.out.println(rsx.getLong("action"));
		System.out.println(rsx.getString("a"));
		System.out.println(rsx.getFloatValue("b"));
	}
}

