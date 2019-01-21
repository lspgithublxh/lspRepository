package com.bj58.jdkjinspecial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * 一些特别的设定
 * 两个新的特殊关键字符号：-> ::
 * 映射的根据：函数签名function signature
 * 引用可以精确到、精准定位到方法上----而不再只是类上、实例上
 * 告知引用和告知方法体可以同时进行-----用lamda表达式作为调用一个方法时的入参
 * @ClassName:FeatureTest
 * @Description:
 * @Author lishaoping
 * @Date 2019年1月21日
 * @Version V1.0
 * @Package com.bj58.jdkjinspecial
 */
public class FeatureTest {

	public static void main(String[] args) {
		//增强接口功能
		f1();
		//简化代码
		f2();
		//统一同参数类型和个数的方法
		f3();
		//用特性来做方法监控
	}

	private static void f3() {
		//函数式接口的方法引用
		//必须要相同的参数类型----即指明参数的类型
		//方法引用当作是一个匿名内部类，或者lamda表达式, 方法引用变成了一个只有一个方法的实例
		FuntionForm1<Integer, String> form1 = Integer::valueOf;
		System.out.println(form1.convert("123"));
		FuntionForm1<Double, String> fomr2 = Double::valueOf;
		System.out.println(fomr2.convert("3.445"));
		FuntionForm1<String, String> form3 = "hello "::concat;
		System.out.println(form3.convert("word"));
		//新的特性、构造方法引用
		FuntionForm1<String, String> form4 = String::new;
		System.out.println(form4.convert("abc"));
		FuntionForm1<Boolean, Object> form5 = Objects::isNull;
		System.out.println(form5.convert(null));
		//Integer::valueOf可以当作是一个变量的值，所以可以传给一个函数，所以可以当作一个函数的入参定义
		FuntionForm2<Integer, String> f6 = Integer::valueOf;
//		FuntionForm2<String, String> f7 = f6.trans(String::concat);//String::concat
		
	}

	/**
	 * 匿名实现函数
	 * @param 
	 * @author lishaoping
	 * @Date 2019年1月21日
	 * @Package com.bj58.jdkjinspecial
	 * @return void
	 */
	private static void f2() {
		//2.java中的lamda表达式:将匿名内部类的一个方法抽象是一个匿名函数，从而----描述输入参数-处理结构-返回参数即可
		//用接口方法参数个数来确定实现的是哪个函数
		//一个lamda表达式 就是 一个 匿名函数----形式上没有定类型--而实际指向的抽象方法都是可以确定的推断的
		//只有一个抽象方法，如果把Object方法抽象化那么这个方法不被统计进来
		List<Integer> a = new ArrayList<>();
		a.add(1);
		a.add(2);
		a.add(4);
		a.add(0);
//		Collections.sort(a, new Comparator<Integer>() {
//			@Override
//			public int compare(Integer o1, Integer o2) {
//				if(o1 > o2){
//					return 1;
//				}else if(o1 < o2) {
//					return -1;
//				}
//				return 0;
//			}
//		});
		Collections.sort(a, (d,e) -> {
			if(d > e){
				return 1;
			}else if(d < e) {
				return -1;
			}
			return 0;
		});
		System.out.println(a);
		//2.不能直接定义，必须是函数式接口，但不只是用在方法调用处----入参地方
		Comparator<Integer> cm = (c, b)->{
			return 1;
		};
		Comparator<Integer> cm2 = (c, b)-> 1;//返回值可以直接被推断，入参类型也可以被推断；返回值自取----如果只有一句的话
		//3.
		A ain = (pa1)->{
			System.out.println("正是一个函数式接口");
		};
		System.out.println(ain.equals(ain));
		ain.b(1);
		A bin = (int pa1)->{System.out.println("haha");};
		
		//4.
	}

	private static void f1() {
		//1.接口的实现类
		new B().moren();
		A.jingtai();
	}
}
