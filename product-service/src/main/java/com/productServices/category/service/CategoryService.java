package com.productServices.category.service;

import com.productServices.category.dto.CreateCategoryDTO;
import com.productServices.category.dto.MinimalCategoryDTO;
import com.productServices.category.Category;
import com.productServices.category.repo.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(CreateCategoryDTO dto) {

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

        return categoryRepository.save(category);
    }

    public List<MinimalCategoryDTO> getMinimalCategories() {
        List<Category> categories = categoryRepository.findByDeletedDateIsNull();
        return categories.stream()
                .map(c -> {
                    MinimalCategoryDTO dto = new MinimalCategoryDTO();
                    dto.setId(c.getId());
                    dto.setName(c.getName());
                    dto.setDescription(c.getDescription());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<MinimalCategoryDTO> getSubCategory(UUID parentId){

        List<Category> categoryList = categoryRepository.findByParentIdAndDeletedDateIsNull(parentId);
        System.out.println("Fetched categories: " + categoryList.size());

        if (categoryList.isEmpty()) {
            throw new RuntimeException("No subcategories found for parent ID: " + parentId);
        }

        System.out.println("1.2.....categoryList: " + categoryList.toString());
        return categoryList.stream()
                .map( s -> {
                    MinimalCategoryDTO dto = new MinimalCategoryDTO();
                    dto.setId(s.getId());
                    dto.setName(s.getName());
                    dto.setDescription(s.getDescription());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
