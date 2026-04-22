package com.productServices.serviceImplementation;

import com.productServices.entity.Category;
import com.productServices.entity.Product;
import com.productServices.entity.ProductIndex;
import com.productServices.enums.ProductStatus;
import com.productServices.gateway.publisher.RestockEventPublisher;
import com.productServices.event.ProductRestockedEvent;
import com.productServices.model.product.CreateProductDTO;
import com.productServices.model.product.ProductDTO;
import com.productServices.repository.CategoryRepository;
import com.productServices.repository.ProductRepository;
import com.productServices.repository.ProductSearchRepository;
import com.productServices.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final RestockEventPublisher eventPublisher;
    @Autowired(required = false)
    private ProductSearchRepository productSearchRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          RestockEventPublisher eventPublisher) {

        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Product createProduct(CreateProductDTO dto) {

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setProductStatus(ProductStatus.ACTIVE);
        product.setStock(dto.getStock() != null ? dto.getStock() : 0L);

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setCategory(category);
        product.setCreatedBy("ADMIN");
        product.setCreatedDate(LocalDate.now());

        // Save to Elasticsearch
        storeDataProductIndex(product);
        // Save product first
//        Product savedProduct = productRepository.save(product);
//        // Publish event if stock > 0
//        if (savedProduct.getStock() > 0) {
//            eventPublisher.publish(new ProductRestockedEvent(savedProduct.getId(), savedProduct.getName()));
//        }

        return productRepository.save(product);
    }

    // Convert Product Entity to ProductDTO
    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }

    @Override
    public Product updateStock(UUID productId, Long newStock){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        System.out.println("1......1 " + newStock);
        if (product.getStock() == 0 && newStock > 0) {
            eventPublisher.publish(new ProductRestockedEvent(productId, product.getName()));
        }
        product.setStock(newStock);
        return productRepository.save(product);
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
