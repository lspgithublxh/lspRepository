package com.tools.kafka;

import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;



/**
 * kafka 客户端--producer和consumer
 *
 *@author lishaoping
 *ToolsTest
 *2017年10月8日
 */
public class KafkaProducer_ {

	final private Producer<String, String> producer;
	
	public final static String TOPIC = "lishaoping";  
	public KafkaProducer_() {
		Properties pro = new Properties();
//		pro.put("metadata.broker.list","127.0.0.1:9092");
//		pro.put("zk.connect", "127.0.0.1:2181");
		pro.put("bootstrap.servers", "localhost:9092");
		pro.put("retries", 0);
		pro.put("acks", "all");
		pro.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		pro.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//		pro.put("request.required.acks", "-1");//同步等待服务器返回结果
		producer = new KafkaProducer<>(pro);
	}
	
	public void producer() {
		for(int i = 0; i < 100; i++) {
			producer.send(new ProducerRecord<String,String>(TOPIC,Integer.toString(i), System.currentTimeMillis() + ""));
		}
		System.out.println("send ok");
		producer.close();
	}

	public static void main(String[] args) {
		new KafkaProducer_().producer();
	}
}
