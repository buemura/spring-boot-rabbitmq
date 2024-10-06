package com.webhooky.events;

import com.webhooky.configs.RabbitMqConfig;
import com.webhooky.entities.Payment;
import com.webhooky.events.dtos.OrderCreatedEventDto;
import com.webhooky.events.dtos.PaymentCreatedEventDto;
import com.webhooky.services.PaymentService;
import com.webhooky.services.ProduceMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventListener {
    private final Logger logger = LoggerFactory.getLogger(PaymentEventListener.class);
    private final PaymentService paymentService;
    private final ProduceMessageService producer;

    public PaymentEventListener(PaymentService paymentService, ProduceMessageService producer) {
        this.paymentService = paymentService;
        this.producer = producer;
    }

    @RabbitListener(queues = RabbitMqConfig.ORDER_CREATED_QUEUE)
    public void handleOrderCreated(Message<OrderCreatedEventDto> message) {
        logger.info("Order created event consumed: {}", message);
        var payment = paymentService.createPayment(
                message.getPayload().order(),
                message.getPayload().order().getAmount(),
                message.getPayload().paymentMethod());

        producer.produceMessage(RabbitMqConfig.PAYMENT_CREATED_QUEUE, payment);
    }

    @RabbitListener(queues = RabbitMqConfig.PAYMENT_CREATED_QUEUE)
    public void handlePaymentCreated(Message<Payment> message) {
        logger.info("Payment created event consumed: {}", message);
        paymentService.requestPayment(message.getPayload());
    }
}
