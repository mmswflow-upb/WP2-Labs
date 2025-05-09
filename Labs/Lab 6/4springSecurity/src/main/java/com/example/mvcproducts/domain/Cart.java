package com.example.mvcproducts.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Cart {
    private List<OrderLineItem> items = new ArrayList<>();
    private double total;

    public void addItem(Product product) {
        for (OrderLineItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + 1);
                updateTotal();
                return;
            }
        }
        OrderLineItem newItem = new OrderLineItem(product, 1);
        items.add(newItem);
        updateTotal();
    }

    public void clear() {
        items.clear();
        updateTotal();
    }

    public void updateQuantity(Long productId, int quantity) {
        items.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    updateTotal();
                });
    }

    public void updateTotal() {
        total = items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }
}