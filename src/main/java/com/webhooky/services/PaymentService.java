package com.webhooky.services;

import com.webhooky.entities.Order;
import com.webhooky.entities.Payment;
import com.webhooky.enums.PaymentMethod;
import com.webhooky.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    public void createPayment(Order order, double amount, PaymentMethod paymentMethod) {
        Payment payment = new Payment(order, amount, paymentMethod);
        paymentRepository.save(payment);
    }

}
