package com.webhooky.services;

import com.webhooky.controllers.dtos.CreateOrderDto;
import com.webhooky.entities.Order;
import com.webhooky.repositories.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public Order createOrder(CreateOrderDto body) {
        var product = productService
                .getProduct(body.productId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        return orderRepository.save(new Order(body.userId(), body.productId(), product.getPrice()));
    }
}
