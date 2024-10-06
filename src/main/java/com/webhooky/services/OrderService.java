package com.webhooky.services;

import com.webhooky.dtos.CreateOrderDto;
import com.webhooky.entities.Order;
import com.webhooky.entities.Product;
import com.webhooky.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductService productService;

    @Autowired
    PaymentService paymentService;

    public void createOrder(CreateOrderDto body) {
        System.out.println("@@@@ " + body);

        Product product = productService.getProduct(body.productId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        Order order = new Order(body.userId(), body.productId(), product.getPrice());
        orderRepository.save(order);

        paymentService.createPayment(order, product.getPrice(), body.paymentMethod());
    }
}
