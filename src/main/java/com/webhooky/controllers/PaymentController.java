package com.webhooky.controllers;

import com.webhooky.controllers.dtos.PaymentConfirmationDto;
import com.webhooky.enums.PaymentConfirmation;
import com.webhooky.enums.PaymentStatus;
import com.webhooky.services.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("webhook")
    public void paymentConfirmation(@RequestBody PaymentConfirmationDto body) {
        var paymentStatus = body.paymentConfirmation() == PaymentConfirmation.SUCCESS ? PaymentStatus.COMPLETED : PaymentStatus.FAILED;
        paymentService.updatePaymentStatus(body.paymentId(), paymentStatus);
    }
}
