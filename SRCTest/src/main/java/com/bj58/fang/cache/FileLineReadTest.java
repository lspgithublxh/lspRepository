package com.bj58.fang.cache;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.Scanner;

/**
 * 读取文件行，而不是所有行一起读进来
 * @ClassName:FileLineReadTest
 * @Description:
 * @Author lishaoping
 * @Date 2018年11月24日
 * @Version V1.0
 * @Package com.bj58.fang.cache
 */
public class FileLineReadTest {

	public static void main(String[] args) {
		Scanner scanner = null;
		try {
			System.gc();
			long m1 = Runtime.getRuntime().freeMemory();
			scanner = new Scanner(new FileInputStream("/log/haha2.log"));
			while(scanner.hasNextLine()) {
				System.out.println(scanner.nextLine());
			}
			System.gc();
			long m2 = Runtime.getRuntime().freeMemory();
			System.out.println((m2 - m1) / 1024 / 1024);//M
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			if(scanner != null) {
				scanner.close();
			}
		}
		
		
		try {
			LineNumberReader lnr = new LineNumberReader(new FileReader(""));
//			lnr.skip(arg0)
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  
	}
}
