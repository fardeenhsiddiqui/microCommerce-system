package com.productServices.category.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record UpdateCategoryDTO(
        @NotBlank
        String name,

        String description,

        UUID parentId
) {
}
