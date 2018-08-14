package com.bj58.fang.log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class CmdToLinux {

	public static void main(String[] args) {
		go();
	}

	private static void go() {//先连接到跳板机
		
		Connection con = new Connection("relay00.58corp.com",22);//10.126.93.210 10.48.210.41
		
		try {
			con.connect();
			boolean au = con.authenticateWithPassword("lishaoping", "Lsp2019*777777");
			if(!au) {
				throw new Exception("error to connect");
			}
			con.forceKeyExchange();
			System.out.println("success");
			Session session = con.openSession();
			session.requestPTY("bash");
			session.startShell();
			InputStream out = new StreamGobbler(session.getStdout());
			InputStream err = new StreamGobbler(session.getStderr());
			BufferedReader outre = new BufferedReader(new InputStreamReader(out));
			BufferedReader errre = new BufferedReader(new InputStreamReader(err));
			BufferedReader conre = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter wr = new PrintWriter(session.getStdin());
			String temp = "";
			int count2 = 0;
			while(!temp.equals("exit")) {
				System.out.print("[lishaoping@58ganji ~]#");
				temp = conre.readLine();
				System.out.println(temp);
				wr.println(new String(temp.getBytes(Charset.forName("UTF-8")), "UTF-8"));//UTF-8 ISO-8859-1
				wr.flush();
				String line = null;
				int count = 0;
				
				while((line = outre.readLine()) != null) {//会卡在这里
					if("".equals(line)) {
						System.out.println("continue...");
						continue;
					}else {
						System.out.println(line);
						if(line.contains("input ip:")) {//请输入想要添加的ip地址
							count++;
							if(count == 2) {
								break;
							}
						}else if(line.contains("input permsion|select number:")) {//请输入你拥有的权限:
							break;
						}else if(line.contains("Password for lishaoping@58OS.ORG:")) {
							count2++;
							if(count2 == 2) {
								break;
							}
						}
					}
				}
				System.out.println("read err");
//				while(true) {
//					String line2 = errre.readLine();//也会卡
//					if(line2 == null) {
//						break;
//					}
//					System.out.println(line2);
//				}
				System.out.println("next --line");
			}
			session.close();
			con.close();
			System.out.println("talking over");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
