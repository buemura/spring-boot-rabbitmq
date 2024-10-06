package com.webhooky.dtos;

import com.webhooky.enums.PaymentMethod;

public record CreateOrderDto(
        String userId,
        String productId,
        PaymentMethod paymentMethod
) {
}
