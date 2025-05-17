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

    public CategoryResponseDTO(UUID id, String name, String description, UUID parentId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parentId = parentId;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
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
