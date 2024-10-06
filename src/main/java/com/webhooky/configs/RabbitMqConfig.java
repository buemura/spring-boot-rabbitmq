package com.webhooky.configs;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    public static final String EXCHANGE_NAME = "myTopicExchange";
    public static final String ORDER_CREATED_QUEUE = "order.created";
    public static final String PAYMENT_CREATED_QUEUE = "payment.created";
    public static final String ORDER_DLQ = "order.created.dlq";
    public static final String PAYMENT_DLQ = "payment.created.dlq";
    public static final String ORDER_ROUTING_KEY = "order.created";
    public static final String PAYMENT_ROUTING_KEY = "payment.created";

    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    Queue orderCreatedQueue() {
        return QueueBuilder.durable(ORDER_CREATED_QUEUE)
                .withArgument("x-dead-letter-exchange", EXCHANGE_NAME)
                .withArgument("x-dead-letter-routing-key", ORDER_ROUTING_KEY + ".dlq")
                .build();
    }

    @Bean
    Queue paymentCreatedQueue() {
        return QueueBuilder.durable(PAYMENT_CREATED_QUEUE)
                .withArgument("x-dead-letter-exchange", EXCHANGE_NAME)
                .withArgument("x-dead-letter-routing-key", PAYMENT_ROUTING_KEY + ".dlq")
                .build();
    }

    @Bean
    Queue orderDlq() {
        return new Queue(ORDER_DLQ, true);
    }

    @Bean
    Queue paymentDlq() {
        return new Queue(PAYMENT_DLQ, true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding orderBinding(Queue orderCreatedQueue) {
        return BindingBuilder.bind(orderCreatedQueue).to(exchange()).with(ORDER_ROUTING_KEY);
    }

    @Bean
    Binding paymentBinding(Queue paymentCreatedQueue) {
        return BindingBuilder.bind(paymentCreatedQueue).to(exchange()).with(PAYMENT_ROUTING_KEY);
    }

    @Bean
    Binding orderDlqBinding(Queue orderDlq, TopicExchange exchange) {
        return BindingBuilder.bind(orderDlq).to(exchange).with(ORDER_ROUTING_KEY + ".dlq");
    }

    @Bean
    Binding paymentDlqBinding(Queue paymentDlq, TopicExchange exchange) {
        return BindingBuilder.bind(paymentDlq).to(exchange).with(PAYMENT_ROUTING_KEY + ".dlq");
    }
}