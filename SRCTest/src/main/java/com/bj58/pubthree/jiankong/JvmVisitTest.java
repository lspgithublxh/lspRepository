package com.bj58.pubthree.jiankong;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * jvm远程监控
 * 并且动态设置jvm参数：setVMOption jconsole控制台可以----并且启动不需要参数：：本地可以--远程则需要配置端口了
 * 
 * jvm管理客户机
 * @ClassName:JvmVisitTest
 * @Description:
 * @Author lishaoping
 * @Date 2018年12月31日
 * @Version V1.0
 * @Package com.bj58.pubthree.jiankong
 */
public class JvmVisitTest {

	public static void main(String[] args) {
		test2();
		
	}

	private static void test() {
		try {
			JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1234/jmxrmi");
			JMXConnector connector = JMXConnectorFactory.connect(url);
			MBeanServerConnection mbean = connector.getMBeanServerConnection();
			System.out.println(mbean.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void test2() {
		try {
			Map<String, String[]> map = new HashMap<String, String[]>();
			map.put("jmx.remote.credentials", new String[] {"monitorRole","QED"});
			JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1234/jmxrmi");
			JMXConnector connector = JMXConnectorFactory.connect(url, map);
			MBeanServerConnection mbean = connector.getMBeanServerConnection();
			System.out.println(mbean.toString());
			System.out.println(mbean.getAttribute(ObjectName.getInstance(ManagementFactory.THREAD_MXBEAN_NAME),
					"ThreadCpuTimeSupported"));
			//
			System.out.println(mbean.getAttribute(ObjectName.getInstance(ManagementFactory.THREAD_MXBEAN_NAME),
					"ThreadCount"));//远程监控 成功！
			//2.调动远程上的jvm
			long[] threadids = (long[]) mbean.getAttribute(ObjectName.getInstance(ManagementFactory.THREAD_MXBEAN_NAME),
					"AllThreadIds");
			Object rs = mbean.invoke(ObjectName.getInstance(ManagementFactory.THREAD_MXBEAN_NAME),
					"getThreadInfo", new Object[] {threadids}, new String[] {"J"});
			System.out.println(rs);
			//3.订阅监听
//			MemoryNotificationInfo 
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AttributeNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedObjectNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MBeanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ReflectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
