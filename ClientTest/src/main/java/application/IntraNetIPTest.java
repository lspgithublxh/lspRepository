package application;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 局域网搜索动态ip
 * 动态网络命令，其他网络命令先省略
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
			Pattern pattern  = Pattern.compile("\\s+((?:\\d+\\.){3}\\d+)\\s+(\\S+)\\s+(\\S+)\\s+");
			Process p = Runtime.getRuntime().exec("arp -a");
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("GBK")));
			String line = "";
			while((line = reader.readLine()) != null) {
				String hang = new String(line.getBytes(), Charset.forName("UTF-8"));
//				System.out.println(hang);//提取出ip
				Matcher m = pattern.matcher(hang);
				if(m.find()) {
					System.out.println(m.group(1));
					System.out.println(m.group(2));
					System.out.println(m.group(3));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
