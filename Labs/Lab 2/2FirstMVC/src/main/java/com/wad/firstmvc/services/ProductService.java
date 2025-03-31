package com.wad.firstmvc.services;

import com.wad.firstmvc.domain.Product;
import com.wad.firstmvc.domain.ProductSearchCriteria;
import java.util.List;

public interface ProductService {
    List<Product> findAll();
    void save(Product p);
    List<Product> search(ProductSearchCriteria criteria);
}
