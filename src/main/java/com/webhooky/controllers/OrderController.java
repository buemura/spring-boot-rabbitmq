package com.webhooky.controllers;

import com.webhooky.configs.RabbitMqConfig;
import com.webhooky.controllers.dtos.CreateOrderDto;
import com.webhooky.events.dtos.OrderCreatedEventDto;
import com.webhooky.services.OrderService;
import com.webhooky.services.ProduceMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "orders")
public class OrderController {

    private final OrderService orderService;
    private final ProduceMessageService producer;

    public OrderController(OrderService orderService, ProduceMessageService producer) {
        this.orderService = orderService;
        this.producer = producer;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestBody CreateOrderDto body) {
        var order = orderService.createOrder(body);
        producer.produceMessage(RabbitMqConfig.ORDER_CREATED_QUEUE, new OrderCreatedEventDto(order, body.paymentMethod()));
    }
}
