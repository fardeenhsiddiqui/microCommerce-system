package com.inventoryService.entity;


import com.inventoryService.enums.ProductStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID Id;

    private String name;
    private String description;
    private Double price;
    private ProductStatus productStatus;

    private String mainImageUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> galleryImages = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Category> categoryList;

    private LocalDate createdDate;
    private String createdBy;
    private LocalDate deletedDate;
    private String deletedBy;

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public List<ProductImage> getGalleryImages() {
        return galleryImages;
    }

    public void setGalleryImages(List<ProductImage> galleryImages) {
        this.galleryImages = galleryImages;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDate deletedDate) {
        this.deletedDate = deletedDate;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }
}
