package com.webhooky.events.dtos;

import com.webhooky.entities.Order;
import com.webhooky.enums.PaymentMethod;

public record OrderCreatedEventDto(
        Order order,
        PaymentMethod paymentMethod
) {
}
