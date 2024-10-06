package com.webhooky.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "payments")
public class PaymentController {
    @PostMapping("webhook")
    public void paymentConfirmation() {
    }
}
