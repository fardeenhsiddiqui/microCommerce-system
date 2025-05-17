package com.inventoryService.service;

import com.inventoryService.entity.Category;
import com.inventoryService.model.category.CategoryResponseDTO;
import com.inventoryService.model.category.CreateCategoryDTO;
import com.inventoryService.model.category.MinimalCategoryDTO;
import com.inventoryService.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Category createCategory(CreateCategoryDTO dto) {

        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDesc());

        if (dto.getParentId() != null) {
            Category parent = categoryRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
            category.setParent(parent);
        }

        return categoryRepository.save(category);
    }

    public List<MinimalCategoryDTO> getMinimalCategories() {
        List<Category> categories = categoryRepository.findAll();
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

        List<Category> categoryList = categoryRepository.findByParentId(parentId);
        System.out.println("Fetched categories: " + categoryList.size());

        if (categoryList.isEmpty()) {
            throw new RuntimeException("No subcategories found for parent ID: " + parentId);
        }

        System.out.println("1......categoryList: " + categoryList.toString());
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
