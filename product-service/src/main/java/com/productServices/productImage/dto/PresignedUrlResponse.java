package com.productServices.productImage.dto;

public record PresignedUrlResponse(
        String uploadUrl,
        String key
) {
}
