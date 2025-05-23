package com.inventoryService.event;

import java.util.UUID;

public class ProductRestockedEvent {

    private UUID productId;
    private String productName;

    public ProductRestockedEvent(UUID productId, String productName) {
        this.productId = productId;
        this.productName = productName;
    }

    public UUID getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }
}
