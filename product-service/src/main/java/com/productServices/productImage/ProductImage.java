package com.productServices.productImage;

import com.productServices.common.entity.BaseEntity;
import com.productServices.product.Product;
import jakarta.persistence.*;


@Entity
public class ProductImage extends BaseEntity {

    private String imageUrl; // S3 / local URL
    private String label;
    private String altText;      // optional
    private boolean primaryImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
