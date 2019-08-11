package com.bj58.jdkjinspecial.atomic;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import sun.misc.Unsafe;

/**
 * 实际工程中非常重要
 * 
 * -------------何时需要使用？----只有在并行的多个线程进行对同一个数据的“先读后写”操作时，需要原子操作。如果只是多个线程读---一个线程写一个数据，那么没必要对这个数据进行同步。
 * 				------即：如果每个线程都是只读、或者只写，不是一个序列(如读改写)的操作；则不会有问题。
 * @ClassName:AtomHandleTest
 * @Description:
 * @Author lishaoping
 * @Date 2019年4月24日
 * @Version V1.0
 * @Package com.bj58.jdkjinspecial.atomic
 */
public class AtomHandleTest {

	private Map<String, Entity> map = new ConcurrentHashMap<>();
	
	public static void main(String[] args) {
		AtomHandleTest test = new AtomHandleTest();
//		test.test();
		test.atomic();
	}

	private void atomic() {
		AtomicReference<Entity> atomic = new AtomicReference<Entity>(new Entity("name"));
		System.out.println(atomic.get());
		AtomicReference<Entity> atomic2 = new AtomicReference<Entity>(new Entity("lsp"));
		boolean suc = atomic.compareAndSet(atomic.get(), atomic2.get());
		if(suc) {
			System.out.println("update success!!");
			System.out.println(atomic.get());
		}
		
		
//		try {
//			Unsafe safe = Unsafe.getUnsafe();
//			safe.compareAndSwapObject(this, safe.objectFieldOffset(AtomicReference.class.getDeclaredField("map")), atomic, atomic2);
//		} catch (NoSuchFieldException | SecurityException e) {
//			e.printStackTrace();
//		}
	}

	/**
	 * 本身是原子操作，其实不会读取到null的情形---已经做过测试 20s 100个线程读，100个线程写。未读到null
	 * @param 
	 * @author lishaoping
	 * @Date 2019年4月24日
	 * @Package com.bj58.jdkjinspecial.atomic
	 * @return void
	 */
	private void test() {
		for(int i = 0; i < 10; i++) {
			new Thread(()->{
				for(int j = 0; j < 10; j++) {
					map.put(j + "", new Entity(j + ""));
				}
			}).start();
		}
		new Thread(()-> {
			 long t1 = System.currentTimeMillis();
				for(int i = 0; i < 100; i++) {
					new Thread(()->{
						while(true) {
							for(int j = 0; j < 10; j++) {
								Entity e = map.get(j + "");
								if(e == null) {
									System.out.println("error:" + j);
								}
//								System.out.println(j + " : " + e);;
							}
							long t2 = System.currentTimeMillis();
							if(t2 - t1 > 1000 * 20) {
								break;
							}
						}
					}).start();
				}
			
		}).start();
		
		new Thread(()-> {
			 long t1 = System.currentTimeMillis();
			for(int i = 0; i < 100; i++) {
				new Thread(()->{
					while(true) {
						for(int j = 0; j < 10; j++) {
							map.put(j + "", new Entity(j + "" + Math.random()));
						}
						long t2 = System.currentTimeMillis();
						if(t2 - t1 > 1000 * 20) {
							break;
						}
					}
				}).start();
			}
		}).start();
	}
}
