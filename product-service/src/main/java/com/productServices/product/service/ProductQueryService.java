package com.productServices.product.service;

import com.productServices.product.Product;
import com.productServices.product.ProductIndex;
import com.productServices.product.dto.PageResponse;
import com.productServices.product.dto.ProductFilter;
import com.productServices.product.dto.ProductIndexDTO;
import com.productServices.product.dto.ProductResponseDTO;
import com.productServices.product.mapper.ProductMapper;
import com.productServices.product.repo.ProductRepository;
import com.productServices.product.repo.ProductSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductQueryService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    //true - after ES enable
    @Autowired(required = false)
    private ProductSearchRepository productSearchRepository;

    public ProductQueryService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public PageResponse<ProductResponseDTO> getProducts(ProductFilter filter, Pageable pageable) {

        Specification<Product> specification =
                ProductSpecification.withFilter(filter);

        Page<ProductResponseDTO> result = productRepository
                .findAll(specification, pageable)
                .map(productMapper::toResponse);

        return new PageResponse<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isLast()
        );

    }

    public ProductResponseDTO getProduct(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toResponse(product);
    }


    public List<ProductIndexDTO> search(String keyword) {
        if (productSearchRepository == null) {
            throw new IllegalStateException("Search is disabled because Elasticsearch is not configured.");
        }

        List<ProductIndex> indexList = productSearchRepository.search(keyword);
        return indexList.stream().map(productMapper::productIndexDTO).toList();
    }

}
