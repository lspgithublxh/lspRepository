package com.bj58.pubthree.jiankong;

import javax.management.Notification;
import javax.management.NotificationListener;

public class HelloListener implements NotificationListener{

	@Override
	public void handleNotification(Notification arg0, Object arg1) {
		if(arg1 instanceof HelloMXBean) {
			HelloMXBean b = (HelloMXBean) arg1;
			b.setName(arg0.getMessage());
			System.out.println("ssss" + b.getName());
		}
	}

}
