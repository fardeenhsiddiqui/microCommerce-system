package com.productServices.product.service;

import com.productServices.product.Product;
import com.productServices.product.ProductIndex;
import com.productServices.product.dto.ProductResponseDTO;
import com.productServices.product.mapper.ProductMapper;
import com.productServices.product.repo.ProductRepository;
import com.productServices.product.repo.ProductSearchRepository;
import com.productServices.productImage.dto.ImageResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductQueryService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired(required = false)
    private ProductSearchRepository productSearchRepository;

    public ProductQueryService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public Page<ProductResponseDTO> getProducts(Pageable pageable) {

        return productRepository.findByDeletedDateIsNull(pageable)
                .map(productMapper::toResponse);
    }

    public ProductResponseDTO getProduct(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toResponse(product);
    }


    public List<ProductIndex> search(String keyword) {
        if (productSearchRepository == null) {
            throw new IllegalStateException("Search is disabled because Elasticsearch is not configured.");
        }
        return productSearchRepository.findByNameContaining(keyword);
    }

}
