package com.myproject.springboot_advenced.controller;

import com.myproject.springboot_advenced.service.PaymentProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentProducerService producerService;

    @PostMapping
    public String sendPayment(@RequestParam String key, @RequestParam String message) {
        producerService.sendPayment(key, message);
        return "Message sent to Kafka";
    }
}
