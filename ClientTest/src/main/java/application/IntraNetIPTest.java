package application;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * 局域网搜索动态ip
 * @ClassName:IntraNetIPTest
 * @Description:
 * @Author lishaoping
 * @Date 2018年8月6日
 * @Version V1.0
 * @Package application
 */
public class IntraNetIPTest {

	public static void main(String[] args) {
		try {
			Process p = Runtime.getRuntime().exec("arp -a");
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("GBK")));
			String line = "";
			while((line = reader.readLine()) != null) {
				System.out.println(new String(line.getBytes(), Charset.forName("UTF-8")));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
