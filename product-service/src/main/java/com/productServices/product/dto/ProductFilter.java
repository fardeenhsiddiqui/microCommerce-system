package com.productServices.product.dto;

import com.productServices.product.enums.ProductStatus;

import java.util.UUID;

public record ProductFilter (

        UUID categoryId,
        ProductStatus status,
        Double minPrice,
        Double maxPrice
) {
}
