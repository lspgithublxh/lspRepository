package com.yunhai.main;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App {
	private static final String SPRING_CONTEXT = "spring-core.xml";
	private static ApplicationContext CONTEXT;
	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
	static{
		CONTEXT = new ClassPathXmlApplicationContext(SPRING_CONTEXT);
	}
    public static void main( String[] args )
    {
    	LOGGER.error("start");
    	Bean bean = CONTEXT.getBean(Bean.class);
    	System.out.println(bean.getName());
    	bean.method1();
    	try {
    		Class model = Class.forName("com.yunhai.main.Bean");
			Object obj = model.newInstance();
			//类型转换方式执行方法
			Bean b = Bean.class.cast(obj);
			b.getName();
			//反射方式执行方法--读取Pergment Space里面的关于类描述的字节码
			Method method = model.getMethod("getName");
			method.invoke(obj);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
    	LOGGER.info("end");
    }
    
    public <T> Object getObject(T param){
    	System.out.println("fanxing");
    	return new Object();
    }
    
    public <T> T getObject(Object param, Class<T> cla){
    	System.out.println("cast a instance to a special instance");
    	return cla.cast(param);
    }
}
