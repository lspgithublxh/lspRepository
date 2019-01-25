package com.bj58.pubthree.jiankong;

import javax.management.Notification;
import javax.management.NotificationListener;

public class HelloListener implements NotificationListener{

	@Override
	public void handleNotification(Notification arg0, Object arg1) {
		if(arg1 instanceof JackMXBean) {
			JackMXBean b = (JackMXBean) arg1;
			b.setHi(arg0.getMessage());
			System.out.println("收到:" + arg0.getMessage());
			System.out.println("收到:" + b.getHi());
		}
	}

}
