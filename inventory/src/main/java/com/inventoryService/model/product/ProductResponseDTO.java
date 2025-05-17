package com.inventoryService.model.product;

import com.inventoryService.entity.Category;
import com.inventoryService.entity.Product;

import java.util.UUID;

public class ProductResponseDTO {

    private final UUID id;
    private final String name;
    private final String desc;
    private final Double price;
    private final String categoryName;

    public ProductResponseDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.desc = product.getDescription();
        this.price = product.getPrice();
        this.categoryName = product.getCategory().getName();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Double getPrice() {
        return price;
    }

    public String getCategoryName() {
        return categoryName;
    }

}
