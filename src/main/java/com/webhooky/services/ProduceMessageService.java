package com.webhooky.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webhooky.configs.RabbitMqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProduceMessageService {
    private final Logger logger = LoggerFactory.getLogger(ProduceMessageService.class);
    private final RabbitTemplate rabbitTemplate;

    public ProduceMessageService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public <T> void produceMessage(String routingKey, T message) {
        logger.info("Sending message to: {}", routingKey);
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME, routingKey, message);
    }
}
