package com.productServices.product;


import com.productServices.category.Category;
import com.productServices.common.domain.CatalogComponent;
import com.productServices.common.entity.SoftDeleteEntity;
import com.productServices.product.enums.ProductStatus;
import com.productServices.productImage.ProductImage;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product extends SoftDeleteEntity implements CatalogComponent{

    private String name;
    private String description;
    private Double price;
    private ProductStatus productStatus;
    private Long stock;

    @OneToMany(mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<ProductImage> galleryImages = new ArrayList<>();

    @ManyToOne
    private Category category;

    public String getName() {
        return name;
    }

    @Override
    public void print(String prefix) {
        System.out.println("Product");
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

    public List<ProductImage> getGalleryImages() {
        return galleryImages;
    }

    public void setGalleryImages(List<ProductImage> galleryImages) {
        this.galleryImages = galleryImages;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }
}
