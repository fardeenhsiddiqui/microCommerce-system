package com.productServices.category.dto;

import java.util.List;
import java.util.UUID;

public record CategoryTreeDTO(
        UUID id,
        String name,
        List<CategoryTreeDTO> children
) {
}
