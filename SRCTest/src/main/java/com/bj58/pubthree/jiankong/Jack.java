package com.bj58.pubthree.jiankong;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class Jack extends NotificationBroadcasterSupport implements JackMXBean{

	private int seq = 0;
	private String name = "";
	@Override
	public void hi() {
		sendNotification(
				new Notification("hahahah" + name, this, ++seq, "发送：真正的消息" + name));
	}
	@Override
	public void setHi(String name) {
		this.name = name;
	}
	@Override
	public String getHi() {
		return name;
	}
	@Override
	public void attr2() {
		System.out.println("shuxing 2被调用");
	}
	@Override
	public void daican(String so) {
		System.out.println("call daican" + so);
	}

}
