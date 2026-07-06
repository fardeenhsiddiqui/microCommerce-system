package com.productServices.product.mapper;

import com.productServices.category.Category;
import com.productServices.category.dto.CategoryResponseDTO;
import com.productServices.category.dto.MinimalCategoryDTO;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponseDTO toResponse(Category category) {
        return new CategoryResponseDTO(category);
    }

    public MinimalCategoryDTO toMinimal(Category category){

        MinimalCategoryDTO dto = new MinimalCategoryDTO();

        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());

        return dto;
    }
}
