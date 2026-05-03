package com.productServices.model.image;

import java.util.List;

public record ProductImageResponse(
        String productName,
        List<ImageResponseDTO> images
) {
}
