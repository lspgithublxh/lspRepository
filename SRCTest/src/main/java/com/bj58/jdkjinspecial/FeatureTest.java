package com.bj58.jdkjinspecial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 一些特别的设定
 * 两个新的特殊关键字符号：-> ::
 * 映射的根据：函数签名function signature
 * 引用可以精确到、精准定位到方法上----而不再只是类上、实例上;而是类和实例的方法上
 * 告知引用和告知方法体可以同时进行-----用lamda表达式作为调用一个方法时的入参
 * 
 * 
 * --------------
 * 性能认识：
 * 1.一个lamda表达式实际上生成了对应的函数式接口的静态内部类---所以一个lamda表达式就会生成一个静态内部类；方法里调用的是生成的静态方法
 * 	  同时生成了一个静态方法-----该方法内容就是lamda表达式里的内容；静态方法结构自然就是函数式接口的方法定义
 *   然后用这个静态内部类的new 实例来替代原lamda表达式的位置。。:::所以本质上：lamda表达式仍然是一个对象----一个实例对象。。。。只是利用了动态编译而已。
 *   ---------所以从性能上看，和使用匿名内部类对象应该是一样的-----或者说相当于使用了一个匿名内部类对象----即相当于new了一个对象在这个位置。
 * 2.方法引用也是同样的道理
 *   --因此肯定获取不到调用方法的名称
 * 参考：https://blog.csdn.net/jiankunking/article/details/79825928
 * 
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
		//stream操作---函数式编程
		f4();
		//多重注解省略
		//javascript引擎
		//简化if-else分支：存在则消耗
		f5();
	}

	private static void f5() {
		//.optional空值操作--空则处理---非空则操作
		Optional<Integer> a = Optional.ofNullable(null);
		a.ifPresent(vv->System.out.println("xiaohao:" + vv));//如果存在则消耗
		
	}

	private static void f4() {
		//1.基本遍历操作
		List<Integer> li = new ArrayList<>();
		li.add(1);
		li.add(2);
		li.add(4);
		li.add(10);
		li.add(3);
		li.stream()
			.distinct()
			.skip(1)
			.sorted()
			.sorted((a, b)->a.intValue()>b.intValue() ? 1 : a < b ? -1 : 0)
			.filter((a)->a.intValue() > 3)
			.forEach(System.out::println);//(a)->a.intValue()
		System.out.println(li);
		//2.map类型转换
		li.stream()
			.sorted()
			.map((a)-> a + "")
			.forEach(System.out::println);
		boolean en = li.stream()
			.sorted()
			.anyMatch((a)->a.intValue()>0);
		System.out.println(en);
		//3.元素规约
		Integer value = li.stream()
			.sorted()
			.reduce((a,b)->a+b)
			.get();
		System.out.println(value);
		Optional<Integer> op = li.stream()
			.sorted()
			.reduce((a,b)->a+b);
		System.out.println(op.isPresent());
		op.ifPresent(System.out::println);
		//4.并行操作, 比如排序，性能10倍差距
		long t1 = System.nanoTime();
		List<String> li2 = new ArrayList<>();
		for(int i = 0; i < 10000; i++) {
			UUID u = UUID.randomUUID();
			li2.add(u.toString());
		}
		long t2 = System.nanoTime();
		long count = li2.stream().sorted().count();//170ms
		long deltT = TimeUnit.NANOSECONDS.toMillis(t2 - t1);
		System.out.println(count + "haoshi:" + deltT);
		long t3 = System.nanoTime();
		long count2 = li2.parallelStream().sorted().count();//15ms
		long t4 = System.nanoTime();
		System.out.println("haoshi:" + TimeUnit.NANOSECONDS.toMillis(t4 - t3));
		//5.map的有关遍历处理---遍历更方便
		Map<String, Integer> yinshe = new HashMap<>();
		yinshe.put("a", 1);
		yinshe.put("b", 2);
		yinshe.put("c", 3);
		yinshe.forEach((str, inte)->System.out.println(str + ":" + inte));
		int ret = yinshe.computeIfAbsent("a", v -> Integer.valueOf(v));
		yinshe.computeIfPresent("a", (v,k)-> v.length() + k);
		System.out.println(yinshe.get("a"));//值会改变-----就是一个取值、移除的过程
		yinshe.remove("a", 3);//键值相同才移除
		yinshe.merge("a", 1, (oldval, newval)->newval+oldval);
		System.out.println(yinshe.get("a"));
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
		//新的特性、构造方法引用-----是最特殊的实例方法引用---直接根据类名来引用
		FuntionForm1<String, String> form4 = String::new;
		System.out.println(form4.convert("abc"));
		FuntionForm1<Boolean, Object> form5 = Objects::isNull;
		System.out.println(form5.convert(null));
		//Integer::valueOf可以当作是一个变量的值，所以可以传给一个函数，所以可以当作一个函数的入参定义
		FuntionForm2<Integer, String> f6 = Integer::valueOf;
		System.out.println(f6.convert("123"));
		FuntionForm2<String, String> han = "hello "::concat;//必须静态方法才能::静态方法，实例方法必须实例才能::方法
		FuntionForm2<String, String> f7 = f6.trans(han);
		System.out.println("" + f7.convert("kk"));
		FuntionForm2<String, String> f8 = f6.trans("hello"::concat);//String::concat
		for(int i = 0; i < 10; i++) {
			FuntionForm2<String, String> f9 = f6.trans("hello"::concat);//String::concat
		}
		System.out.println(f8.convert(" world"));
		//
		
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
