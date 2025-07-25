package com.myproject.springboot_advenced.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "payments";

    public void sendPayment(String key, String message) {
        kafkaTemplate.send(TOPIC, key, message);
        System.out.println("Sent message: " + message + " with key: " + key);
    }
}
