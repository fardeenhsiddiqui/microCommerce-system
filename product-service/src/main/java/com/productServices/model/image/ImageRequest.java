package com.productServices.model.image;

public record ImageRequest(
        String key,
        String label,
        String altText
) {
}
