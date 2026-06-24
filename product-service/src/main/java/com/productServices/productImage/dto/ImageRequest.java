package com.productServices.productImage.dto;

public record ImageRequest(
        String key,
        String label,
        String altText
) {
}
