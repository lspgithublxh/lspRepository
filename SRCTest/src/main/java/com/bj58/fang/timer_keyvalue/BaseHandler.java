package com.bj58.fang.timer_keyvalue;

import java.util.HashMap;
import java.util.Map;




public class BaseHandler {

	public static void main(String[] args) {
		BaseHandler b = new BaseHandler();
		for(int i = 0 ;i < 30; i ++) {
			b.test(i + 1);
		}
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0 ;i < 30; i ++) {
			b.test(i + 1);
		}
	}

	static Map<Integer, Param<Long, Integer>> map = new HashMap<Integer, Param<Long, Integer>>();
	
	private Integer test(int cityId) {
		//1.
		//2.
		if(map.containsKey(cityId)) {
			Long lastTime = map.get(cityId).getLast_update();
			if(System.currentTimeMillis() > lastTime + 3 * 1000) {
				System.out.println("重新获取");//重新设置
				//失效，重新获取cityId的状态
			}
		}else {
			//获取cityId的状态
			System.out.println("初次获取");
			map.put(cityId, new Param<>(System.currentTimeMillis(), 1));
		}
		return map.get(cityId).getValue();
	}
	
	class Param<K,T>{
		private K last_update;
		private T value;
		public K getLast_update() {
			return last_update;
		}
		public void setLast_update(K last_update) {
			this.last_update = last_update;
		}
		public T getValue() {
			return value;
		}
		public void setValue(T value) {
			this.value = value;
		}
		public Param(K last_update, T value) {
			super();
			this.last_update = last_update;
			this.value = value;
		}
		
	}
}
