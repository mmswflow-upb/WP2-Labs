package com.wad.firstmvc.services;

import com.wad.firstmvc.domain.Product;
import com.wad.firstmvc.domain.ProductSearchCriteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    List<Product> products = new ArrayList<>(List.of(
            new Product(13L, "icecream", 2.5, "Dessert"),
            new Product(25L, "car", 25000.0, "Vehicle")
    ));

    @Override
    public List<Product> findAll(){
        return products;
    }

    @Override
    public void save(Product p) {
        if(p.getId() == null)
            p.setId(new Random().nextLong());
        products.add(p);
    }

    @Override
    public List<Product> search(ProductSearchCriteria criteria) {
        return products.stream().filter(product -> {
            boolean matches = true;
            if (criteria.getCategory() != null && !criteria.getCategory().isEmpty()) {
                matches &= product.getCategory() != null &&
                        product.getCategory().equalsIgnoreCase(criteria.getCategory());
            }
            if (criteria.getMinPrice() != null) {
                matches &= product.getPrice() != null && product.getPrice() >= criteria.getMinPrice();
            }
            if (criteria.getMaxPrice() != null) {
                matches &= product.getPrice() != null && product.getPrice() <= criteria.getMaxPrice();
            }
            return matches;
        }).collect(Collectors.toList());
    }
}
