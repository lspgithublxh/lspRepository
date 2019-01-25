package com.bj58.pubthree.jiankong;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class Jack extends NotificationBroadcasterSupport implements JackMXBean{

	private int seq = 0;
	@Override
	public void hi() {
		sendNotification(
				new Notification("hahahah", this, ++seq, "jack"));
	}

}
