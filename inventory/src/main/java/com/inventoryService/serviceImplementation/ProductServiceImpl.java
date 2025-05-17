package com.inventoryService.serviceImplementation;

import com.inventoryService.entity.Category;
import com.inventoryService.entity.Product;
import com.inventoryService.enums.ProductStatus;
import com.inventoryService.model.product.CreateProductDTO;
import com.inventoryService.model.product.ProductDTO;
import com.inventoryService.repository.CategoryRepository;
import com.inventoryService.repository.ProductRepository;
import com.inventoryService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

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

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setCategory(category);
        product.setCreatedBy("ADMIN");
        product.setCreatedDate(LocalDate.now());
        return productRepository.save(product);
    }

    // Convert Product Entity to ProductDTO
    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }
}
