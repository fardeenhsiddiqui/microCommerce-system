package com.inventoryService.model.category;

import com.inventoryService.entity.Category;

import java.util.UUID;

public class CategoryResponseDTO {

    private UUID id;
    private String name;
    private String description;
    private UUID parentId;

    public CategoryResponseDTO() {
    }

    public CategoryResponseDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
        this.parentId = category.getParent().getId() != null ? category.getParent().getId() : null;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public UUID getParentId() {
        return parentId;
    }
}
