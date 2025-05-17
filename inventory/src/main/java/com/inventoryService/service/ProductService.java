package com.inventoryService.service;

import com.inventoryService.entity.Product;
import com.inventoryService.model.product.CreateProductDTO;
import com.inventoryService.model.product.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getAllProducts();

    public Product createProduct(CreateProductDTO dto);
}
