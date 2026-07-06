package com.productServices.product.service;

import com.productServices.category.Category;
import com.productServices.category.repo.CategoryRepository;
import com.productServices.product.Product;
import com.productServices.product.ProductIndex;
import com.productServices.product.dto.*;
import com.productServices.product.event.ProductRestockedEvent;
import com.productServices.product.mapper.ProductMapper;
import com.productServices.product.publisher.RestockEventPublisher;
import com.productServices.product.repo.ProductRepository;
import com.productServices.product.repo.ProductSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductCommandService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final RestockEventPublisher eventPublisher;

    @Autowired(required = false)
    private ProductSearchRepository productSearchRepository;
    private final ProductMapper productMapper;

    public ProductCommandService(ProductRepository productRepository, CategoryRepository categoryRepository, RestockEventPublisher eventPublisher, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.eventPublisher = eventPublisher;
        this.productMapper = productMapper;
    }

    @Transactional
    public ProductResponseDTO createProduct(CreateProductDTO dto) {

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = productMapper.toEntity(dto, category);

        // Save product first
        Product savedProduct = productRepository.save(product);

        // Index in Elasticsearch (if enabled)
        if (productSearchRepository != null) {
            // ES enabled, just save it
            productSearchRepository.save(productMapper.toIndex(savedProduct));
        }

        //currently disabled to rabbit mq for testing
        // Optionally publish restock event
//        if (savedProduct.getStock() > 0) {
//            eventPublisher.publish(new ProductRestockedEvent(savedProduct.getId(), savedProduct.getName()));
//        }

        return productMapper.toResponse(savedProduct);
    }

    @Transactional
    public List<ProductResponseDTO> createProducts(List<CreateProductDTO> dtos) {

        Map<UUID, Category> categoryMap = categoryRepository.findAllById(
                        dtos.stream()
                                .map(CreateProductDTO::categoryId)
                                .collect(Collectors.toSet())
                ).stream().collect(Collectors.toMap(Category::getId, Function.identity()));

        List<Product> products = dtos.stream()
                .map(dto -> {
                    Category category = categoryMap.get(dto.categoryId());

                    if (category == null) {
                        throw new ResourceNotFoundException(
                                "Category not found: " + dto.categoryId());
                    }

                    Product product = productMapper.toEntity(dto, category);;
                    return product;
                })
                .toList();

        List<Product> savedProducts = productRepository.saveAll(products);

        if (productSearchRepository != null) {
            List<ProductIndex> indexes = savedProducts.stream()
                    .map(productMapper::toIndex)
                    .toList();

            productSearchRepository.saveAll(indexes);
        }

//        savedProducts.stream()
//                .filter(product -> product.getStock() > 0)
//                .forEach(product ->
//                        eventPublisher.publish(
//                                new ProductRestockedEvent(
//                                        product.getId(),
//                                        product.getName()
//                                )
//                        )
//                );

        return savedProducts.stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Transactional
    public ProductResponseDTO updateStock(UUID productId, Long newStock) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() == 0 && newStock > 0) {
            eventPublisher.publish(new ProductRestockedEvent(productId, product.getName()));
        }
        product.setStock(newStock);
        Product saveProduct = productRepository.save(product);

        return productMapper.toResponse(saveProduct);
    }

    @Transactional
    public void deleteProduct(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        product.setDeletedDate(LocalDateTime.now());
        product.setDeletedBy("Admin");
        productRepository.save(product);
    }

    @Transactional
    public ProductResponseDTO updateProduct(UUID productId, UpdateProductDTO dto) {

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        productMapper.updateEntity(product, dto, category);

        Product savedProduct = productRepository.save(product);

        if (productSearchRepository != null) {
            // ES enabled, just save it
            productSearchRepository.save(productMapper.toIndex(savedProduct));
        }

        return productMapper.toResponse(savedProduct);
    }

    @Transactional
    public ProductResponseDTO updateStatus(UUID productId, UpdateProductStatusDTO dto) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        product.setProductStatus(dto.status());
        Product saved = productRepository.save(product);
        return productMapper.toResponse(saved);
    }

}
