package com.bj58.pubthree.jiankong;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

public class HelloAgent {

	public static void main(String[] args) {
		MBeanServer server = ManagementFactory.getPlatformMBeanServer();
		try {
			ObjectName helloName = new ObjectName("jmxBean:name=hello");//jmxBean都是命名的，name，hello也是命名的。在管理界面当作key一样的存在
			HelloMXBean hello = new HelloMBeanImpl();
			server.registerMBean(hello, helloName);
			
			Jack jack = new Jack();
			server.registerMBean(jack, new ObjectName("jack:name=enen"));
			jack.addNotificationListener(new HelloListener(), null, jack);
			
			Thread.sleep(60 * 60 * 1000);
		} catch (MalformedObjectNameException | NullPointerException | InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
