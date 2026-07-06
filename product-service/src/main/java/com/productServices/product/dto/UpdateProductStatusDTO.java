package com.productServices.product.dto;

import com.productServices.product.enums.ProductStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateProductStatusDTO(

        @NotNull
        ProductStatus status
) {
}
