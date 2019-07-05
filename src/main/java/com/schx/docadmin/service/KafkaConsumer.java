package com.schx.docadmin.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @description: consumer
 * @author: xutao
 * @create: 2019-07-04 17:26
 **/
@Service
public class KafkaConsumer {

    @KafkaListener(topics = {"duanjt_test"})
    public void listen(ConsumerRecord<?, ?> record) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            System.out.println("收到消息："+message.toString());
        }

    }
}
