package com.myproject.springboot_advenced.service.impl;

import com.myproject.springboot_advenced.dto.Order;
import com.myproject.springboot_advenced.dto.OrderAction;
import com.myproject.springboot_advenced.repository.OrderRepository;
import com.myproject.springboot_advenced.service.KafkaProducerService;
import com.myproject.springboot_advenced.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final KafkaProducerService kafkaProducerService;

    @Override
    public void create(Order order) {
       orderRepository.save(order);
        CompletableFuture.runAsync(() -> {kafkaProducerService.send(OrderAction.CREATE, order);});
    }
}
