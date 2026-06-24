package com.productServices.product.service;

import com.productServices.product.dto.CreateProductDTO;
import com.productServices.product.dto.ProductDTO;
import com.productServices.product.Product;
import com.productServices.product.ProductIndex;

import java.util.List;
import java.util.UUID;

public interface IProductService {

    List<ProductDTO> getAllProducts();

    public Product createProduct(CreateProductDTO dto);

    public List<Product> createProducts(List<CreateProductDTO> dto);

    public Product getProduct(UUID productId);

    public Product updateStock(UUID productId, Long newStock);

    public List<ProductIndex> search(String keyword);

    public void deleteProduct(UUID productId);
}
