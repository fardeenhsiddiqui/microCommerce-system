package com.inventoryService.model.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CreateProductDTO {

    @NotNull
    @NotBlank
    private String name;
    @NotNull
    private String description;
    @NotNull
    private Double price;
    @NotNull
    private UUID categoryId;

    public CreateProductDTO(String name, String description, Double price, UUID categoryId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

}
