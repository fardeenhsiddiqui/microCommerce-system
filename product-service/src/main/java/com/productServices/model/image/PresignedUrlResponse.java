package com.productServices.model.image;

public record PresignedUrlResponse(
        String uploadUrl,
        String key
) {
}
