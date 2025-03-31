package com.wad.firstmvc.domain;

import lombok.Data;

@Data
public class ProductSearchCriteria {
    private String category;
    private Double minPrice;
    private Double maxPrice;
}
