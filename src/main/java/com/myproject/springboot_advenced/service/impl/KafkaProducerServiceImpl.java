package com.myproject.springboot_advenced.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.springboot_advenced.dto.OrderAction;
import com.myproject.springboot_advenced.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final KafkaTemplate<String, JsonNode> kafkaTemplate;
    private final ObjectMapper objectMapper;


    @Value("${uz-common-kafka.config-simulate}")
    private Boolean simulate;

    @Value("${uz-common-kafka.topics.order-create.name}")
    private String topic;

    @Override
    public <T> void send(OrderAction key, T message) {
       sendMassage(message, topic, key.name());
    }

    private <T> void sendMassage(T message, String topic, String key) {

        if (simulate == null || Boolean.TRUE.equals(simulate)) {
            log.info("Kafka producer simulation is ON");
            return;
        }

        if (topic == null || topic.isEmpty()) {
            log.warn("Message producing topic is empty or null");
            return;
        }

        if (message == null) {
            log.warn("Produced message is null");
            return;
        }

        try {
            var messageToKafka = objectMapper.convertValue(message, JsonNode.class);
            log.info("Sending message to topic [{}], {}, with key {}", topic, messageToKafka, key);
            kafkaTemplate.send(topic, key, messageToKafka);
        } catch (Exception e) {
            log.error("Error while serializing object kafka producer: {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
