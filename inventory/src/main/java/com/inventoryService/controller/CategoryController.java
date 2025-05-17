package com.inventoryService.controller;

import com.inventoryService.entity.Category;
import com.inventoryService.model.ApiResponse;
import com.inventoryService.model.category.CategoryResponseDTO;
import com.inventoryService.model.category.CreateCategoryDTO;
import com.inventoryService.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category/")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("addCategory")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> addCategory(@Valid @RequestBody CreateCategoryDTO dto) {
        try {
            Category category = categoryService.createCategory(dto);
            CategoryResponseDTO response = mapToResponseDTO(category);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, response, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, null, e.getMessage()));
        }
    }

    private CategoryResponseDTO mapToResponseDTO(Category category) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        if (category.getParent() != null) {
            dto.setParentId(category.getParent().getId());
        }
        return dto;
    }
}
