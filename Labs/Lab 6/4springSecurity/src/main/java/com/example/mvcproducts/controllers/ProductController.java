package com.example.mvcproducts.controllers;

import com.example.mvcproducts.domain.Product;
import com.example.mvcproducts.domain.User;
import com.example.mvcproducts.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Random;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    Logger logger = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // -populate the model with the retrieved products!
    // -select the appropriate view (navigation)
    @GetMapping
    public String viewProducts(Model model, Authentication authentication) {
        model.addAttribute("products", productService.findAll());
        User user = (User) authentication.getPrincipal();
        logger.info(user.getUsername());
        return "products";
    }

    @GetMapping("/new")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "addproducts";
    }

    @PostMapping("/new")
    public String addProduct(Product product) {
        productService.save(product);
        return "redirect:/products";
    }
}
