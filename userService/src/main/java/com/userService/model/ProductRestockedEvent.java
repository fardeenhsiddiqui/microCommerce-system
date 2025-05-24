package com.userService.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class ProductRestockedEvent {

    private UUID productId;
    private String productName;

    public ProductRestockedEvent() {} // Required for Jackson

    public ProductRestockedEvent(UUID productId, String productName) {
        this.productId = productId;
        this.productName = productName;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
