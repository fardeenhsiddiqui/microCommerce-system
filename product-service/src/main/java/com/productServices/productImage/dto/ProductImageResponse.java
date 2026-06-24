package com.productServices.productImage.dto;

import java.util.List;

public record ProductImageResponse(
        String productName,
        List<ImageResponseDTO> images
) {
}
