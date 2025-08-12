package com.myproject.springboot_advenced.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.springboot_advenced.dto.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/consumer")
//@Service
@RequiredArgsConstructor
public class NotificationServiceController {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "order-create", groupId = "order-group")
    @PostMapping("")
    public void listen(JsonNode message){
        Order order = objectMapper.convertValue(message, Order.class);
        System.out.println("🔔 New Order Received: " + order.getName() + ", Amount: $" + order.getAmount());
    }
}