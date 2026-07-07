package com.productServices.category.service;

import com.productServices.category.dto.*;
import com.productServices.category.Category;
import com.productServices.category.repo.CategoryRepository;
import com.productServices.product.mapper.CategoryMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.elasticsearch.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Transactional
    public CategoryResponseDTO createCategory(CreateCategoryDTO dto) {

        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDesc());

        if (dto.getParentId() != null) {
            Category parent = categoryRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
            category.setParent(parent);
        }else {
            category.setParent(null);
        }

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(savedCategory);
    }

    public List<MinimalCategoryDTO> getMinimalCategories() {
        List<Category> categories = categoryRepository.findByDeletedDateIsNull();
        return categories.stream()
                .map(categoryMapper::toMinimal)
                .collect(Collectors.toList());
    }

    public List<MinimalCategoryDTO> getSubCategory(UUID parentId){

        List<Category> categoryList = categoryRepository.findByParentIdAndDeletedDateIsNull(parentId);
        System.out.println("Fetched categories: " + categoryList.size());

        if (categoryList.isEmpty()) {
            throw new RuntimeException("No subcategories found for parent ID: " + parentId);
        }

        return categoryList.stream()
                .map(categoryMapper::toMinimal)
                .collect(Collectors.toList());
    }

    public CategoryResponseDTO getCategory(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found"));

        return categoryMapper.toResponse(category);
    }

    @Transactional
    public CategoryResponseDTO updateCategory(UUID categoryId, UpdateCategoryDTO dto) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found"));
        category.setName(dto.name());
        category.setDescription(dto.description());

        if (dto.parentId() != null) {
            Category parent = categoryRepository.findById(dto.parentId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Parent category not found"));

            category.setParent(parent);
        } else {
            category.setParent(null);
        }

        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Transactional
    public void deleteCategory(UUID categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found"));

        category.setDeletedDate(LocalDateTime.now());
        category.setDeletedBy("SYSTEM");

        categoryRepository.save(category);
    }

    public List<CategoryTreeDTO> getTree() {

        return categoryRepository.findByParentIsNullAndDeletedDateIsNull()
                .stream()
                .map(this::buildTree)
                .toList();
    }

    private CategoryTreeDTO buildTree(Category category) {

        List<CategoryTreeDTO> children =
                category.getSubCategories()
                        .stream()
                        .filter(c -> c.getDeletedDate() == null)
                        .map(this::buildTree)
                        .toList();

        return new CategoryTreeDTO(
                category.getId(),
                category.getName(),
                children
        );
    }

}
