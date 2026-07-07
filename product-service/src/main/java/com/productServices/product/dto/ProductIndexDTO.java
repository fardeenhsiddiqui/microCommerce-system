package com.productServices.product.dto;

public record ProductIndexDTO(

        String name,
        String description,
        String category,
        Double price
) {
}
