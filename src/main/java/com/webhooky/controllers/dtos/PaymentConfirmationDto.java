package com.webhooky.controllers.dtos;

import com.webhooky.enums.PaymentConfirmation;

public record PaymentConfirmationDto(
        String paymentId,
        PaymentConfirmation paymentConfirmation
) {
}
