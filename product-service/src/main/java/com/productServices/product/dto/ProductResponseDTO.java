package com.productServices.product.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.productServices.productImage.dto.ImageResponseDTO;
import com.productServices.product.Product;

import java.util.List;
import java.util.UUID;

public class ProductResponseDTO {

    private final UUID id;
    private final String name;
    private final String desc;
    private final Double price;
    private final String categoryName;
    private final List<ImageResponseDTO> images;

    public ProductResponseDTO(Product product, List<ImageResponseDTO> images) {
        this.id = product.getId();
        this.name = product.getName();
        this.desc = product.getDescription();
        this.price = product.getPrice();
        this.categoryName = product.getCategory().getName();
        this.images = images;
    }

    @JsonCreator
    public ProductResponseDTO(

            @JsonProperty("id") UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("desc") String desc,
            @JsonProperty("price") Double price,
            @JsonProperty("categoryName") String categoryName,
            @JsonProperty("images") List<ImageResponseDTO> images
    ) {

        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.categoryName = categoryName;
        this.images = images;
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

    public List<ImageResponseDTO> getImages() {
        return images;
    }

    public Double getPrice() {
        return price;
    }

    public String getCategoryName() {
        return categoryName;
    }

}
