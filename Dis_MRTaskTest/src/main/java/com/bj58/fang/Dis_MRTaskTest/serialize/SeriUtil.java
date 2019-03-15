package com.bj58.fang.Dis_MRTaskTest.serialize;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SeriUtil {

	public static FanxuGuifan getData(byte[] data) {
		try {
			ObjectInputStream obj = new ObjectInputStream(new ByteArrayInputStream(data));
			Object rs = obj.readObject();
			return (FanxuGuifan) rs;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
