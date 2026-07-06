package com.productServices.product.service;

import com.productServices.product.dto.CreateProductDTO;
import com.productServices.product.dto.ProductDTO;
import com.productServices.product.Product;
import com.productServices.product.ProductIndex;
import com.productServices.product.dto.ProductResponseDTO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface IProductService {

    List<ProductResponseDTO> getAllProducts();

    public Product createProduct(CreateProductDTO dto);

    public List<ProductResponseDTO> createProducts(List<CreateProductDTO> dto);

    public ProductResponseDTO getProduct(UUID productId);

    public ProductResponseDTO updateStock(UUID productId, Long newStock);

    public List<ProductIndex> search(String keyword);

    public void deleteProduct(UUID productId);

    ProductResponseDTO crateSingleProduct(@Valid CreateProductDTO dto);
}
