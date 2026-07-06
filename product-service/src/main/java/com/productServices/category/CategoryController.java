package com.productServices.category;

import com.productServices.category.dto.*;
import com.productServices.category.service.CategoryService;
import com.productServices.common.dto.ApiResponse;
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
            CategoryResponseDTO response = categoryService.createCategory(dto);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(true, response, null));
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

        List<MinimalCategoryDTO> list = categoryService.getSubCategory(UUID.fromString(categoryId));
        return ResponseEntity.ok(new ApiResponse<>(true, list, null));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> getCategory(
            @PathVariable UUID categoryId) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        categoryService.getCategory(categoryId),
                        null
                )
        );
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> updateCategory(
            @PathVariable UUID categoryId,
            @RequestBody @Valid UpdateCategoryDTO dto) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        categoryService.updateCategory(categoryId, dto),
                        null
                )
        );
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID categoryId) {

        categoryService.deleteCategory(categoryId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tree")
    public ResponseEntity<ApiResponse<List<CategoryTreeDTO>>> tree() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        categoryService.getTree(),
                        null
                )
        );
    }

}
