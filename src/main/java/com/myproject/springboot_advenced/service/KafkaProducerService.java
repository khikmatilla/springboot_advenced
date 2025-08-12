package com.myproject.springboot_advenced.service;

import com.myproject.springboot_advenced.dto.OrderAction;

public interface KafkaProducerService {
    <T> void send(OrderAction key, T message);

}
