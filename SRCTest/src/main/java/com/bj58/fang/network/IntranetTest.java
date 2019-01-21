package com.bj58.fang.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * nbtscan
 * ip扫描太慢
 * @ClassName:IntranetTest
 * @Description:
 * @Author lishaoping
 * @Date 2018年12月29日
 * @Version V1.0
 * @Package com.bj58.fang.network
 */
public class IntranetTest {

	public static void main(String[] args) {
//		allIp();
//		System.out.println(reachAbleAndPort());
		List<String> ipL = new ArrayList<>();
		allIp2(new int[] {10, 0, 0, 0},  3, ipL);
	}

	private static boolean reachAbleAndPort() {
		String host = "10.252.62.125";
		Socket so = new Socket();
		try {
			boolean reacheAble = InetAddress.getByName(host).isReachable(50);
			System.out.println(reacheAble);
			so.connect(new InetSocketAddress(host, 16778), 50);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				so.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	private static void allIp() {
		String cmd = "arp -a";
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			InputStream in = process.getInputStream();
			InputStreamReader read = new InputStreamReader(in, Charset.forName("GBK"));
			BufferedReader buf = new BufferedReader(read);
			String line = null;
			//多网卡问题：所以会有多个ip
			String pat = "^\\s*((?:\\d+\\.)+(?:\\d+))";
			Pattern p = Pattern.compile(pat);
			List<String> ips = new ArrayList<>();
			while((line = buf.readLine()) != null) {
//				System.out.println(line);
				Matcher m = p.matcher(line);
				if(m.find()) {
					ips.add(m.group(1));
				}
			}
			System.out.println(ips.size());
			//逐个建立试探	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * p1=196,10
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月30日
	 * @Package com.bj58.fang.network
	 * @return void
	 */
	private static void allIp2(int[] iparr, int level, List<String> ipList) {
		if(level == 0) {
			return;
		}
		int curr = iparr[level];
		if(curr == 255) {
			if(--level == 0) {
				return;
			}
		}else {
			iparr[level] = ++curr;
		}
		try {
			String ip = iparr[0] + "." + iparr[1] + "." + iparr[2] + "." + iparr[3];
			boolean reacheAble = InetAddress.getByName(ip).isReachable(10);
			System.out.println(ip + " " + reacheAble);
			if(reacheAble) {
				ipList.add(ip);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		allIp2(iparr, level, ipList);
	}
}
