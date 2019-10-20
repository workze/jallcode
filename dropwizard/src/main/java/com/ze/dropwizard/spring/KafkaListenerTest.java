package com.ze.dropwizard.spring;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author wangguize
 * date 2019-08-11
 */
@Component
@KafkaListener(id = "1", topics = {"123"},autoStartup = "true")
public class KafkaListenerTest {

    @KafkaHandler
    public void handle(@Payload Person mes){
        System.out.println(mes);
    }

    @KafkaHandler
    public void handle2(Integer mes){
        System.out.println(mes);
    }

}
