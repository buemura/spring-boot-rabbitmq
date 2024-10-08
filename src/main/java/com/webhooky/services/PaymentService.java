package com.webhooky.services;

import com.webhooky.entities.Order;
import com.webhooky.entities.Payment;
import com.webhooky.enums.PaymentConfirmation;
import com.webhooky.enums.PaymentMethod;
import com.webhooky.enums.PaymentStatus;
import com.webhooky.repositories.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PaymentService {

    private final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment createPayment(Order order, double amount, PaymentMethod paymentMethod) {
        logger.info("Creating payment for order: {}", order.getId());
        Payment payment = new Payment(order, amount, paymentMethod);
        return paymentRepository.save(payment);
    }

    public void requestPayment(Payment payment) {
        logger.info("Requesting payment for paymentId: {}", payment.getId());
        logger.info("Payment requested to external Service: {}", payment.getId());
    }

    public Payment updatePaymentStatus(String id, PaymentStatus paymentStatus) {
        var payment = paymentRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found"));

        payment.setPaymentStatus(paymentStatus);
        return paymentRepository.save(payment);
    }
}
