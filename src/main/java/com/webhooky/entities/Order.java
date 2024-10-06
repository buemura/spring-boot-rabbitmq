package com.webhooky.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
public class Order {
    @Id
    String id;

    @Column(name = "user_id")
    String userId;

    @Column(name = "product_id")
    String productId;

    @Column
    double amount;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Payment payment;

    public Order(String userId, String productId, double amount) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.productId = productId;
        this.amount = amount;
    }

    public Order() {}
}
