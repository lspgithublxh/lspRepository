package com.bj58.fang.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntranetTest {

	public static void main(String[] args) {
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
				System.out.println(line);
				Matcher m = p.matcher(line);
				if(m.find()) {
					ips.add(m.group(1));
				}
			}
			System.out.println(ips);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
