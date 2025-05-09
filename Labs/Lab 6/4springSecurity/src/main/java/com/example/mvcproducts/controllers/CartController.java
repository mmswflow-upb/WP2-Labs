package com.example.mvcproducts.controllers;

import com.example.mvcproducts.domain.Cart;
import com.example.mvcproducts.domain.Order;
import com.example.mvcproducts.domain.Product;
import com.example.mvcproducts.domain.User;
import com.example.mvcproducts.repositories.OrderRepository;
import com.example.mvcproducts.services.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/cart")
@SessionAttributes("cart")
public class CartController {
    private final ProductService productService;
    private final OrderRepository orderRepository;

    public CartController(ProductService productService, OrderRepository orderRepository) {
        this.productService = productService;
        this.orderRepository = orderRepository;
    }

    @ModelAttribute("cart")
    public Cart cart() {
        return new Cart();
    }

    @GetMapping("/add")
    public String addToCart(@RequestParam Long pid, @ModelAttribute("cart") Cart cart) {
        Product product = productService.findById(pid);
        if (product != null) {
            cart.addItem(product);
        }
        return "redirect:/products";
    }

    @GetMapping
    public String viewCart(@ModelAttribute("cart") Cart cart, Model model) {
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/clear")
    public String clearCart(@ModelAttribute("cart") Cart cart) {
        cart.clear();
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateQuantities(@RequestParam Map<String, String> quantities, @ModelAttribute("cart") Cart cart) {
        quantities.forEach((key, value) -> {
            if (key.startsWith("quantity_")) {
                try {
                    Long productId = Long.parseLong(key.substring("quantity_".length()));
                    int quantity = Integer.parseInt(value);
                    cart.updateQuantity(productId, quantity);
                } catch (NumberFormatException e) {
                    // Skip invalid entries
                }
            }
        });
        return "redirect:/cart";
    }

    @GetMapping("/remove")
    public String removeItem(@RequestParam Long pid, @ModelAttribute("cart") Cart cart) {
        cart.getItems().removeIf(item -> item.getProduct().getId().equals(pid));
        return "redirect:/cart";
    }

    @PostMapping("/order")
    public String createOrder(@ModelAttribute("cart") Cart cart, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Order order = new Order(user);

        cart.getItems().forEach(item -> order.addItem(item.getProduct(), item.getQuantity()));

        orderRepository.save(order);
        cart.clear();

        return "redirect:/orders?success";
    }
}