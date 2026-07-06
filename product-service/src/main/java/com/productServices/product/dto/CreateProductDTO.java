package com.productServices.product.dto;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record CreateProductDTO(

        @NotBlank
        @Size(max = 100)
        String name,

        @NotBlank
        @Size(max = 500)
        String description,

        @NotNull(message = "Price is required")
        @DecimalMin("0.0")
        Double price,

        @NotNull
        UUID categoryId,

        @PositiveOrZero
        Long stock

) {

}
