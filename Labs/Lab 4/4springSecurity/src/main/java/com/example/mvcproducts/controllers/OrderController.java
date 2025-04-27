package com.example.mvcproducts.controllers;

import com.example.mvcproducts.domain.Order;
import com.example.mvcproducts.domain.User;
import com.example.mvcproducts.repositories.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderRepository orderRepository;
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public String viewOrders(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<Order> orders = orderRepository.findByUser(user);
        logger.info("Found {} orders for user {}", orders.size(), user.getUsername());
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/{id}")
    public String viewOrderDetails(@PathVariable Long id, Model model, Authentication authentication) {
        logger.info("Viewing order details for order ID: {}", id);
        User user = (User) authentication.getPrincipal();
        Order order = orderRepository.findById(id).orElse(null);

        if (order == null) {
            logger.warn("Order not found with ID: {}", id);
            return "redirect:/orders";
        }

        if (!order.getUser().equals(user)) {
            logger.warn("User {} attempted to access order {} belonging to user {}",
                    user.getUsername(), id, order.getUser().getUsername());
            return "redirect:/orders";
        }

        logger.info("Successfully retrieved order {} for user {}", id, user.getUsername());
        model.addAttribute("order", order);
        return "orderDetails";
    }
}