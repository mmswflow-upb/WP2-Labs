package com.example.mvcproducts.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderLineItem {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Product product;

    private int quantity;

    public OrderLineItem() {
    }

    public OrderLineItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}