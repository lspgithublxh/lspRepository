package com.scrapy.test;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisTest {

	static JedisPool pool;
	static ShardedJedisPool shardedJedisPool;
	static Jedis jedis;
	static ShardedJedis sharedJedis;
	static {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(20);
		config.setMaxWaitMillis(1000 * 3);
		config.setTestOnBorrow(false);
		config.setMaxIdle(5);
		pool = new JedisPool(config, "localhost", 6379);
		initialShardedPool();
		jedis = pool.getResource();
		sharedJedis = shardedJedisPool.getResource();
	}
	
	public static Jedis getJedis() {
		return jedis;
	}

	public static void main(String[] args) {
		test();
	}

	private static void test() {
		baseTest();	
	}
	
	private static void baseTest() {
		jedis.lpush("java", "1");
		jedis.lpush("java", "2");
		
		String pop = jedis.rpop("java");
		System.out.println(pop);
	}

	public static void initialShardedPool() 
    { 
        // 池基本配置 
        JedisPoolConfig config = new JedisPoolConfig(); 
        config.setMaxTotal(20); 
        config.setMaxIdle(5); 
        config.setMaxWaitMillis(1000l); 
        config.setTestOnBorrow(false); 
        // slave链接 
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>(); 
        shards.add(new JedisShardInfo("localhost", 6379, "master")); 
        
        // 构造池 
        shardedJedisPool = new ShardedJedisPool(config, shards); 
        
    } 
}
