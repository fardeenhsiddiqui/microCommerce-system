package com.productServices.model.image;

import com.productServices.entity.ProductImage;

import java.util.HashMap;

public record ImageResponseDTO (
        String imageUrl,
        String label,
        String altText
) {}
