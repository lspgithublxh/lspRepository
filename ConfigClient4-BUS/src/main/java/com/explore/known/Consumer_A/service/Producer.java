package com.explore.known.Consumer_A.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.explore.known.Consumer_A.entity.Message;

@Component
public class Producer {

	@Autowired
    KafkaTemplate kafkaTemplate;
    private static final String MY_TOPIC = "TOPIC_LIN_LIANG";

    public void produce(){
        Message message = new Message();
        message.setId(12L);
        message.setMsg("hello jack");
        kafkaTemplate.send(MY_TOPIC,message);
    }

}
