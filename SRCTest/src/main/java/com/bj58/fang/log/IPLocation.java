package com.bj58.fang.log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;

/**
 * ip序列--mapreduce--展示定位区域分析
 * @ClassName:IPLocation
 * @Description:
 * @Author lishaoping
 * @Date 2018年8月16日
 * @Version V1.0
 * @Package com.bj58.fang.log
 */
public class IPLocation {

	public static void main(String[] args) {
//		String ips = "106.110.88.115,27;121.233.79.245,23;121.233.89.111,22;58.218.2.94,22;114.235.185.117,21;121.233.90.105,20;114.234.213.92,19;180.123.85.107,18;114.234.164.87,17;114.235.231.167,17;180.125.238.11,17;221.229.186.33,16;114.234.71.246,15;49.68.55.242,15;114.234.165.169,14;117.87.77.116,14;114.234.95.215,13;180.123.93.252,13;180.124.130.29,13;117.87.136.138,12;121.233.90.93,12;180.123.94.79,12;58.218.3.6,12;114.235.46.170,11;117.87.137.118,11;117.87.157.172,11;121.233.86.142,11;106.110.90.50,10;106.110.96.247,10;114.235.184.149,10;114.235.46.30,10;180.123.94.174,10;180.123.95.39,10;222.187.162.131,10;106.110.65.196,9;114.234.213.48,9;106.110.88.109,8;114.234.164.27,8;114.234.71.40,8;117.87.77.157,8;180.104.14.110,8;180.124.130.89,8;222.187.162.214,8;49.82.252.152,8;106.110.91.66,6;180.125.238.168,6;49.68.105.67,6;180.124.208.104,5;221.229.186.20,5;114.234.213.133,4;125.33.124.89,1";
		String ips = LogAnalyze.getIps();
		anlyzeLog(args, ips);
	}

	public static void anlyzeLog(String[] args, String ips) {
		//分析服务奠定基础
		String[] ipsa = ips.split(";");
		List<String> li = new ArrayList<>();
		for(String ip : ipsa) {
			String[] ipa = ip.split(",");
			String d = test(ipa[0]);
			for(int i = 0; i < Integer.valueOf(ipa[1]); i++) {
				li.add(d.replace(",", "-"));
			}
			System.out.println(d);
//			System.out.println(onlineGet(ip));
		}
		List<List<String>> li2 = new ArrayList<>();
		li2.add(li);
		LogAnalyze.analizeForOut(args, li2);
	}

	private static String online(String log, String lat) {
		String urlString = "http://gc.ditu.aliyun.com/regeocoding?l="+lat+","+log+"&type=010";
		try {
			URL url = new URL(urlString);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = "";
			String res = "";
			while((line = reader.readLine()) != null) {
				JSONObject obj = JSONObject.parseObject(line);
				line = obj.getJSONArray("addrList").getJSONObject(0).getString("admName");
				res += line + "\\n";
			}
			
			return res;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static String onlineGet(String ip) {
		String rs = LandLinux.get(String.format("https://www.maxmind.com/geoip/v2.1/city/%s?use-downloadable-db=1&demo=1", ip));//106.110.88.115
//		System.out.println(rs);
		return rs;
	}
	
	public static String test2(String lon, String lat) {
		String s = online(lon, lat);
		return s;
	}
	
	public static String test(String ip) {
		try {
			LookupService ls = new LookupService("D:\\download\\GeoLiteCity-2013-01-18.dat", LookupService.GEOIP_MEMORY_CACHE);
			Location loc = ls.getLocation(ip);//"144.0.9.29"很多ip定位不了
//			System.out.println(loc);
			String s = loc != null ? "area:" + loc.city + ", country:"  + loc.countryName  + ", region:" + loc.region+  "; " + loc.latitude + "," + loc.longitude : null;
			s = online(loc.longitude + "", loc.latitude + "");
			return s;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
