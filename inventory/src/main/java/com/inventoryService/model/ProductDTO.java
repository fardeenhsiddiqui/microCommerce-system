package com.inventoryService.model;

import java.util.UUID;

public record ProductDTO(UUID id, String name, String description, Double price) {
}
