package com.productServices.product.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateProductDTO(

        @NotBlank
        @Size(max = 100)
        String name,

        @NotBlank
        @Size(max = 500)
        String description,

        @NotBlank
        @DecimalMin("0.0")
        String price,

        @NotBlank
        String categoryId
) {
}
