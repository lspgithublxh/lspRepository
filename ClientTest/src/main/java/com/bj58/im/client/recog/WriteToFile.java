package com.bj58.im.client.recog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriteToFile {
	
	public static int writeToFile(String file, byte[] r) {
		int count = 0;
		StringBuffer b = new StringBuffer();
		for(int i = 0; i < r.length; i++) {
//			System.out.print(r[i] + ",");
			b.append(r[i] + "");
			if(count++ % 200 == 0) {
				System.out.println();
				b.append("\r\n");
			}
		}
		try {
			FileOutputStream o = new FileOutputStream(new File(file));
			o.write(b.toString().getBytes());
			o.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	}
}
