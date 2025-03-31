package com.wad.firstmvc.controllers;

import com.wad.firstmvc.domain.Product;
import com.wad.firstmvc.domain.ProductSearchCriteria;
import com.wad.firstmvc.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // List all products
    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "products"; // maps to products.html
    }

    // Show form for new product
    @GetMapping("/new")
    public String showProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "productForm"; // maps to productForm.html
    }

    // Save the product
    @PostMapping
    public String saveProduct(@ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/products";
    }

    // Show the product search form
    @GetMapping("/search")
    public String showSearchForm(Model model) {
        model.addAttribute("searchCriteria", new ProductSearchCriteria());
        return "productSearchForm"; // maps to productSearchForm.html
    }

    // Process search form and delegate search to the service
    @PostMapping("/search")
    public String searchProducts(@ModelAttribute ProductSearchCriteria searchCriteria, Model model) {
        List<Product> filteredProducts = productService.search(searchCriteria);
        model.addAttribute("products", filteredProducts);
        return "products"; // reuse products.html to display search results
    }
}
