package com.myproject.springboot_advenced.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentConsumerService {

    @KafkaListener(topics = "payments", groupId = "payment-consumer-group")
    public void listenPayments(ConsumerRecord<String, String> record) {
        System.out.println("Received message:");
        System.out.println("Key: " + record.key());
        System.out.println("Value: " + record.value());
        System.out.println("Partition: " + record.partition());
        System.out.println("Offset: " + record.offset());
    }
}
