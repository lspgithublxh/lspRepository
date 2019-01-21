package com.bj58.pubthree.jiankong;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryManagerMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.PlatformManagedObject;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.logging.LogManager;
import java.util.logging.LoggingMXBean;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;

/**
 * jvm内存监控
 * 事情之一是：
 * 反射的权限管理
 * classpath:windows上开发时一般被设置为了：jre所在路径和maven仓库所在路径----且全是具体到包名
 * 	---且可以在系统属性上设置：system.property
 * 参考：https://www.ibm.com/developerworks/cn/java/j-mxbeans/#11
 * 	https://docs.oracle.com/javase/1.5.0/docs/guide/management/security-windows.html
 * @ClassName:MxBeansTest
 * @Description:
 * @Author lishaoping
 * @Date 2018年12月30日
 * @Version V1.0
 * @Package com.bj58.pubthree.jiankong
 */
public class MxBeansTest {

	public static void main(String[] args) {
		test();
	}

	private static void test() {
		OperatingSystemMXBean opera = ManagementFactory.getOperatingSystemMXBean();
//		runAllGetMethod(opera);
		System.out.println(opera.getArch());
		System.out.println(opera.getName());
		System.out.println(opera.getVersion());
		System.out.println(opera.getAvailableProcessors());
		System.out.println(opera.getSystemLoadAverage());//cpu负载
		System.out.println(opera.getObjectName());
		System.out.println("------------------");
		
		ClassLoadingMXBean classload = ManagementFactory.getClassLoadingMXBean();
		System.out.println(classload.getLoadedClassCount());
		System.out.println(classload.getTotalLoadedClassCount());
		System.out.println(classload.getUnloadedClassCount());
		System.out.println("----------------------");
		
		List<MemoryManagerMXBean> memlist = ManagementFactory.getMemoryManagerMXBeans();
		for(MemoryManagerMXBean mmx : memlist) {
			System.out.println(mmx.getName());
			for(String m : mmx.getMemoryPoolNames()) {
				System.out.println(m);
			}
		}
		System.out.println("----------------------");
		List<MemoryPoolMXBean> mpoollist = ManagementFactory.getMemoryPoolMXBeans();
		for(MemoryPoolMXBean mpb : mpoollist) {
//			System.out.println(mpb.getCollectionUsageThreshold());
//			System.out.println(mpb.getCollectionUsageThresholdCount());
//			System.out.println(mpb.getUsageThreshold());
//			System.out.println(mpb.getUsageThresholdCount());
			System.out.println(mpb.getName());
			System.out.println(mpb.getCollectionUsage());
			System.out.println(mpb.getPeakUsage());
			System.out.println(mpb.getType());
		}
		System.out.println("---------------------dd");
		MemoryMXBean jvmmem = ManagementFactory.getMemoryMXBean();
		System.out.println(jvmmem.getHeapMemoryUsage());//非常多
		System.out.println(jvmmem.getNonHeapMemoryUsage());
		System.out.println(jvmmem.getObjectPendingFinalizationCount());
		System.out.println("----------------------");
		CompilationMXBean compile = ManagementFactory.getCompilationMXBean();
		System.out.println(compile.getTotalCompilationTime());
		System.out.println("----------------------");
		List<GarbageCollectorMXBean> gabage = ManagementFactory.getGarbageCollectorMXBeans();
		for(GarbageCollectorMXBean ga : gabage) {
			System.out.println(ga.getCollectionCount());
			System.out.println(ga.getCollectionTime());
			System.out.println(ga.getName());
			System.out.println(arrToStr(ga.getMemoryPoolNames()));
			System.out.println("**********************");
		}
		System.out.println("------------------------");
		//其他问题
		Set<Class<? extends PlatformManagedObject>> plats =	ManagementFactory.getPlatformManagementInterfaces();
		for(Class<? extends PlatformManagedObject> cls : plats) {
			System.out.println(cls.getName());
		}
		System.out.println("-----------------------");
		MBeanServer server = ManagementFactory.getPlatformMBeanServer();
		System.out.println(server.getDefaultDomain());
		System.out.println(server.getClass());
		System.out.println("---------------------");
		MBeanServer bser = MBeanServerFactory.createMBeanServer();
		RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
		System.out.println(runtime.getUptime());
		System.out.println(runtime.getStartTime());
		System.out.println(runtime.getLibraryPath());
		System.out.println(runtime.getBootClassPath());
		System.out.println(runtime.getClassPath());
		System.out.println(runtime.getInputArguments());
		System.out.println(runtime.getManagementSpecVersion());
		System.out.println(runtime.getSpecName());
		System.out.println(runtime.getSpecVendor());
		System.out.println(runtime.getSpecVersion());
		System.out.println(runtime.getVmName());
		System.out.println(runtime.getVmVendor());
		System.out.println(runtime.getVmVersion());
		System.out.println(runtime.getSystemProperties());//classpath
		System.out.println("---------------系统属性");
		ThreadMXBean mxbean = ManagementFactory.getThreadMXBean();
		System.out.println(mxbean.getCurrentThreadCpuTime());
		System.out.println(mxbean.getTotalStartedThreadCount());
		System.out.println(mxbean.getPeakThreadCount());
		System.out.println(mxbean.getCurrentThreadUserTime());
		System.out.println(mxbean.getDaemonThreadCount());
		System.out.println(mxbean.getThreadCpuTime(Thread.currentThread().getId()));
		System.out.println(mxbean.getThreadUserTime(Thread.currentThread().getId()));
		for(Long id : mxbean.getAllThreadIds()) {
			System.out.println("thread id:" + id);
		}
		System.out.println("-----------------");
		ThreadInfo[] ti = mxbean.getThreadInfo(mxbean.getAllThreadIds());
		for(ThreadInfo tfi : ti) {
			System.out.println(tfi.getLockOwnerName() + tfi.toString());
		}
		System.out.println("-------------------");
		LoggingMXBean log = LogManager.getLoggingMXBean();
		System.out.println(log.getLoggerNames());
		System.out.println("-------------------");
		
	}

	private static String arrToStr(String[] memoryPoolNames) {
		String s = "";
		for(String m : memoryPoolNames) {
			s += "," + m;
		}
		return s;
	}

	private static void runAllGetMethod(OperatingSystemMXBean opera) {
		Method[] me = opera.getClass().getDeclaredMethods();
		for(Method m : me) {
			if(m.getName().startsWith("get") && !m.toGenericString().contains(" native ")) {
				try {
					System.out.println(m.getName() + "    " + m.invoke(opera));
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//					e.printStackTrace();
				}
			}
		}
	}
}
