package com.inventoryService.model.category;

import com.inventoryService.entity.Category;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CreateCategoryDTO {

    @NotNull
    private String name;
    private String desc;
    private UUID parentId;

    public CreateCategoryDTO(Category category) {
        this.name = category.getName();
        this.desc = category.getDescription();
        this.parentId = category.getParent() != null ? category.getParent().getId() : null;
    }

    public String getName(){
        return name;
    }

    public String getDesc(){
        return desc;
    }

    public UUID getParentId(){
        return parentId;
    }

    public CreateCategoryDTO() {}

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

}
