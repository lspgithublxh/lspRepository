package com.bj58.fang.main;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bj58.fang.annotation.BaseAnnotation;

@BaseAnnotation(order=1)
public class BaseTest {

	public static void main(String[] args) throws IOException {
//		testAnnotion();
//		fileHandle();
//		elseMethod();
		testStream();
	}

	private static void testStream() throws IOException {
		FileInputStream in = new FileInputStream("D:\\new.jpg");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int l = 0;
        while((l = in.read(b)) > 0) {
        	out.write(b, 0, l);
        }
        FileOutputStream out22 = new FileOutputStream(new File("D:\\ab.txt"));
        for(byte bx : out.toByteArray()) {
        	out22.write((bx + ",").getBytes());
        }
        out22.write("\r\n".getBytes());;
        BufferedImage image = ImageIO.read(new File("D:\\new.jpg"));
        int[] rs = new int[image.getWidth() * image.getHeight()];
        rs = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getHeight());
        for(int bx : rs) {
        	out22.write((bx + ",").getBytes());
        }
        out22.write("\r\n".getBytes());
        ByteArrayOutputStream out2 = new ByteArrayOutputStream();
        ImageIO.write(image, "png", out2);
        for(byte bx : out2.toByteArray()) {
        	out22.write((bx + ",").getBytes());
        }
	}

	private static void elseMethod() {
		Double s = 23.33;
		Map<String, String> itemDefault = new HashMap<String ,String>();
		itemDefault.put("abc", "123");
		Map<String, String> itemDefault2 = new HashMap<String ,String>();
		itemDefault2.put("abc", "456");
		itemDefault.putAll(itemDefault2);
		System.out.println(itemDefault);
		
		Runnable a  = new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("--------a");
			}
		};
		Runnable b  = new Runnable() {
			
			@Override
			public void run() {
				System.out.println("--------b");
			}
		};
		a.run();//并无线程，因为只是一个借口的实现和调用而已..所以没有异步调用
		b.run();
		System.out.println("------main");
	}
	
	public static void fileHandle() throws IOException {
		String content = FileUtils.readFileToString(new File("D:\\project\\SRCTest\\src\\main\\java\\com\\bj58\\fang\\main\\json.txt"), "utf-8");
		JSONObject obj = JSONObject.parseObject(content);
		System.out.println(JSONObject.toJSONString(obj, SerializerFeature.SortField));
		String content1 = FileUtils.readFileToString(new File("D:\\project\\SRCTest\\src\\main\\java\\com\\bj58\\fang\\codeGenerate\\detail\\json.txt"), "utf-8");
		JSONObject obj2 = JSONObject.parseObject(content1);
		System.out.println(JSONObject.toJSONString(obj2, SerializerFeature.SortField));
	}
	
	public static void testAnnotion() {
		for(AnnotatedType type : BaseTest.class.getAnnotatedInterfaces()) {
			System.out.println("------1" + type.toString());
		}
		//获取注解的值---必须知道其中的方法。。而其他的方法则需要知道实例
		BaseAnnotation bc = BaseTest.class.getDeclaredAnnotation(BaseAnnotation.class);
		System.out.println(bc.order());
		
		for(Annotation a : BaseTest.class.getAnnotations()) {
			System.out.println("------2" + a.toString());
			Class<?> c  = a.annotationType();
			System.out.println("----3");
			for(Annotation n : c.getDeclaredAnnotations()) {
				System.out.println(n.annotationType() + n.toString());
				for(Method m : n.annotationType().getDeclaredMethods()) {
					System.out.println(m.getName() + m.toString());
				}
			}
			
		}
		
	}
}
