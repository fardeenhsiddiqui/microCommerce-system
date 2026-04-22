package com.productServices.service;

import com.productServices.entity.Product;
import com.productServices.entity.ProductIndex;
import com.productServices.model.product.CreateProductDTO;
import com.productServices.model.product.ProductDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<ProductDTO> getAllProducts();

    public Product createProduct(CreateProductDTO dto);

    public Product updateStock(UUID productId, Long newStock);

    public List<ProductIndex> search(String keyword);
}
