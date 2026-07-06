package com.productServices.product.service;

import com.productServices.category.Category;
import com.productServices.category.repo.CategoryRepository;
import com.productServices.product.Product;
import com.productServices.product.ProductIndex;
import com.productServices.product.dto.*;
import com.productServices.product.enums.ProductStatus;
import com.productServices.product.event.ProductRestockedEvent;
import com.productServices.product.mapper.ProductMapper;
import com.productServices.product.publisher.RestockEventPublisher;
import com.productServices.product.repo.ProductRepository;
import com.productServices.product.repo.ProductSearchRepository;
import com.productServices.productImage.dto.ImageResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        storeDataProductIndex(savedProduct);

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

                    Product product = new Product();
                    product.setName(dto.name());
                    product.setDescription(dto.description());
                    product.setPrice(dto.price());
                    product.setStock(dto.stock() == null ? 0L : dto.stock());
                    product.setCategory(category);
                    product.setProductStatus(ProductStatus.ACTIVE);

                    return product;
                })
                .toList();

        List<Product> savedProducts = productRepository.saveAll(products);

        if (productSearchRepository != null) {
            List<ProductIndex> indexes = savedProducts.stream()
                    .map(this::toIndex)
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

    public void storeDataProductIndex(Product product) {
        if (productSearchRepository == null) {
            // ES disabled, just skip indexing
            return;
        }
        productSearchRepository.save(toIndex(product));
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
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setDeletedDate(java.time.LocalDateTime.now());
            product.setDeletedBy("Admin");
            productRepository.save(product);
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    public ProductResponseDTO updateProduct(UUID productId, UpdateProductDTO dto) {

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));

        Category category = categoryRepository.findById(UUID.fromString(dto.categoryId()))
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(Double.valueOf(dto.price()));
        product.setCategory(category);

        Product saved = productRepository.save(product);

        storeDataProductIndex(saved);

        return productMapper.toResponse(saved);
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

    private ProductResponseDTO mapToResponse(Product product) {

        return productMapper.toResponse(product);
    }

    private ProductIndex toIndex(Product product) {

        return productMapper.toIndex(product);
    }
}
