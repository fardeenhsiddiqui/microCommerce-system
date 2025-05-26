package com.inventoryService.service;

import com.inventoryService.entity.Product;
import com.inventoryService.model.product.CreateProductDTO;
import com.inventoryService.model.product.ProductDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<ProductDTO> getAllProducts();

    public Product createProduct(CreateProductDTO dto);

    public Product updateStock(UUID productId, Long newStock);
}
