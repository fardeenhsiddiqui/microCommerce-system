package com.inventoryService.service;

import com.inventoryService.model.product.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getAllProducts();
}
