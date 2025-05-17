package com.inventoryService.controller;

import com.inventoryService.entity.Category;
import com.inventoryService.model.ApiResponse;
import com.inventoryService.model.category.CategoryResponseDTO;
import com.inventoryService.model.category.CreateCategoryDTO;
import com.inventoryService.model.category.MinimalCategoryDTO;
import com.inventoryService.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/minimal")
    public ResponseEntity<ApiResponse<List<MinimalCategoryDTO>>> getMinimalCategories() {
        List<MinimalCategoryDTO> list = categoryService.getMinimalCategories();
        return ResponseEntity.ok(new ApiResponse<>(true, list, null));
    }

    @GetMapping("/subCategory")
    public ResponseEntity<ApiResponse<List<MinimalCategoryDTO>>> getSubCategories(@RequestParam String categoryId) {
        System.out.println("1......." + categoryId);
        List<MinimalCategoryDTO> list = categoryService.getSubCategory(UUID.fromString(categoryId));
        return ResponseEntity.ok(new ApiResponse<>(true, list, null));
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
