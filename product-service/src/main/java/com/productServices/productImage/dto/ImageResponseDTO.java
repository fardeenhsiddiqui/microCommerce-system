package com.productServices.productImage.dto;

public record ImageResponseDTO (
        String imageUrl,
        String label,
        String altText
) {}
