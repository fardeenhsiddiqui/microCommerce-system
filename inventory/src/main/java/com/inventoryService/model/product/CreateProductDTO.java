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
    private Long stock;

    public CreateProductDTO(String name, String description, Double price, UUID categoryId, Long stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.stock = stock;
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

    public Long getStock(){
        return stock;
    }

}
