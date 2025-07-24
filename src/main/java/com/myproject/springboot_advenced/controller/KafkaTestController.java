package com.myproject.springboot_advenced.controller;

import com.myproject.springboot_advenced.MyKafkaProducer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
public class KafkaTestController {

    private final MyKafkaProducer producer;

    public KafkaTestController(MyKafkaProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/send")
    public String send(@RequestParam String msg) {
        producer.sendMessage(msg);
        return "Yuborildi: " + msg;
    }
}
