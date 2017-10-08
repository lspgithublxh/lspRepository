package com.tools.kafka;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class KafkaConsumer_ {

	private Consumer<String, String> consumer;
	
	private KafkaConsumer_() {
		Properties pro = new Properties();
		pro.put("bootstrap.servers", "localhost:9092");
		pro.put("group.id", "test");
		pro.put("enable.auto.commit", "true");
		pro.put("auto.commit.interval.ms", "1000");
		pro.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		pro.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		consumer = new KafkaConsumer<>(pro);
	}

	public void consum() {
		consumer.subscribe(Arrays.asList("lishaoping","test"));
		while(true) {
			System.out.println("start consume:");
			ConsumerRecords<String, String> records = consumer.poll(100);
			for(ConsumerRecord<String, String> record : records) {
				System.out.println("----offset:" + record.offset() +"----consume: " + record.key() + ", " + record.value());
			}
			consumer.commitSync();
		}
	}
	
	public static void main(String[] args) {
		new KafkaConsumer_().consum();
	}
	
}
