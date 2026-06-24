package com.productServices.product.service;

import com.productServices.category.Category;
import com.productServices.category.repo.CategoryRepository;
import com.productServices.product.dto.CreateProductDTO;
import com.productServices.product.dto.ProductDTO;
import com.productServices.product.Product;
import com.productServices.product.ProductIndex;
import com.productServices.product.enums.ProductStatus;
import com.productServices.product.event.ProductRestockedEvent;
import com.productServices.product.publisher.RestockEventPublisher;
import com.productServices.product.repo.ProductRepository;
import com.productServices.product.repo.ProductSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final RestockEventPublisher eventPublisher;

    @Autowired(required = false)
    private ProductSearchRepository productSearchRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          RestockEventPublisher eventPublisher) {

        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findByDeletedDateIsNull().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Product createProduct(CreateProductDTO dto) {

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setProductStatus(ProductStatus.ACTIVE);
        product.setStock(dto.getStock() != null ? dto.getStock() : 0L);
        product.setCategory(category);

        // Save product first
        Product savedProduct = productRepository.save(product);

        // Index in Elasticsearch (if enabled)
        storeDataProductIndex(savedProduct);

        // Optionally publish restock event
//        if (savedProduct.getStock() > 0) {
//            eventPublisher.publish(new ProductRestockedEvent(savedProduct.getId(), savedProduct.getName()));
//        }

        return savedProduct;
    }

    @Override
    public Product getProduct(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    @Transactional
    public Product updateStock(UUID productId, Long newStock) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() == 0 && newStock > 0) {
            eventPublisher.publish(new ProductRestockedEvent(productId, product.getName()));
        }
        product.setStock(newStock);
        return productRepository.save(product);
    }

    @Override
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

    @Override
    @Transactional
    public List<Product> createProducts(List<CreateProductDTO> dtos) {
        return dtos.stream()
                .map(this::createProduct) // reuse single-create logic
                .collect(Collectors.toList());
    }

    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }

    public void storeDataProductIndex(Product product) {
        if (productSearchRepository == null) {
            // ES disabled, just skip indexing
            return;
        }
        ProductIndex index = new ProductIndex();
        index.setId(product.getId().toString());
        index.setName(product.getName());
        index.setDescription(product.getDescription());
        index.setPrice(product.getPrice());
        productSearchRepository.save(index);
    }

    public List<ProductIndex> search(String keyword) {
        if (productSearchRepository == null) {
            throw new IllegalStateException("Search is disabled because Elasticsearch is not configured.");
        }
        return productSearchRepository.findByNameContaining(keyword);
    }
}
