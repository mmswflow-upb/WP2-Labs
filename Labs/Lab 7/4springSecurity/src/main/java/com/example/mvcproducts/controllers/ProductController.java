package com.example.mvcproducts.controllers;

import com.example.mvcproducts.domain.Product;
import com.example.mvcproducts.domain.User;
import com.example.mvcproducts.services.ProductService;
import com.example.mvcproducts.services.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Random;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final StorageService storageService;
    Logger logger = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductService productService, StorageService storageService) {
        this.productService = productService;
        this.storageService = storageService;
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
    public String addProduct(@ModelAttribute("product") Product product, @RequestParam(value = "file") MultipartFile image) {
        if (!image.isEmpty()) {
            String imageName = storageService.save(image);
            product.setImage(imageName);
        }
        productService.save(product);
        return "redirect:/products";
    }

    @GetMapping("/{pid}/image")
    public ResponseEntity<Resource> getImage(@PathVariable("pid") Long pid) {
        Product product = productService.findById(pid);
        if (product != null && product.getImage() != null) {
            Resource resource = storageService.load(product.getImage());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + product.getImage() + "\"")
                    .body(resource);
        }
        return ResponseEntity.notFound().build();
    }
}
