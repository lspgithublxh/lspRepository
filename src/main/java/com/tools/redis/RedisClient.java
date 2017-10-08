package com.tools.redis;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Transaction;

public class RedisClient {

	public static void main(String[] args) {
		
	}
	
	Jedis jedis = new Jedis("localhost", 6379);
	
	
	
	@Test
	public void client() {
		//同步方式
		System.out.println("=----start connect ----");
		jedis.connect();
		
//		System.out.println(jedis.exists("list-lishaoping"));
//		System.out.println(jedis.asking());
//		System.out.println(jedis.isConnected());
		System.out.println("=---- connect success----");
		lpush("listwechat");
		jedis.disconnect();
		jedis.close();
	}

	private void lpush(String key) {
		for(int i = 0; i < 100; i++) {
			jedis.lpush(key, i + "--");
			System.out.println("----send to redis----");
		}
	}
	
	/**
	 * 此方式目前不可行
	 *@author lishaoping
	 *ToolsTest
	 *2017年10月8日
	 * @throws IOException 
	 */
	@Test
	public void transact() throws IOException {
		jedis.connect();
		System.out.println("trans start");
		Transaction trans = jedis.multi();
		lpush("wechat");
		List<Object> list = trans.exec();
		System.out.println(list);
		trans.close();
		jedis.close();
	}
	
	@Test
	public void pipeline() throws IOException {
		jedis.connect();
//		jedis.auth(password)
		Pipeline p = jedis.pipelined();
		lpush("pipe");
		List<Object> list = p.syncAndReturnAll();
		System.out.println(list);
		p.close();
		jedis.disconnect();
	}
	
	@Test
	public void get() {
		jedis.connect();
		while(true) {
			if(jedis.exists("pipe")) {
				System.out.println("get : " + jedis.lpop("pipe"));
			}
			
		}
	}
	
	@Test
	public void publish() {
		jedis.connect();
		jedis.publish("renminribao", "today is 2.17.10.8");
	}
	
	@Test
	public void subsribe() {
		jedis.connect();
		
		jedis.subscribe(new JedisPubSub() {
			@Override
			public void onMessage(String channel, String message) {
				System.out.println("on messeage:" + message + "  from " + channel);
				super.onMessage(channel, message);
			}
			
			@Override
			public void onSubscribe(String channel, int subscribedChannels) {
				// TODO Auto-generated method stub
				super.onSubscribe(channel, subscribedChannels);
			}
			
			@Override
			public void onPSubscribe(String pattern, int subscribedChannels) {
				System.out.println("on psubscribe ");
				super.onPSubscribe(pattern, subscribedChannels);
			}
			
			@Override
			public void onUnsubscribe(String channel, int subscribedChannels) {
				// TODO Auto-generated method stub
				super.onUnsubscribe(channel, subscribedChannels);
			}
		
		}, "renminribao");
		
		
		
	}
}
