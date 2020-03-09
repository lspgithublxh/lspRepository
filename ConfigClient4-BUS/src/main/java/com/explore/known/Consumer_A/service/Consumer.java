package com.explore.known.Consumer_A.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.explore.known.Consumer_A.entity.Message;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Consumer {

    private static final String MY_TOPIC = "TOPIC_LIN_LIANG";

    @KafkaListener(topics = {MY_TOPIC})
    public void consume(String message){
    	log.info(message);
    }

}
