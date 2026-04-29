package com.productServices.controller;

import com.productServices.entity.Product;
import com.productServices.model.ApiResponse;
import com.productServices.model.product.CreateProductDTO;
import com.productServices.model.product.ProductResponseDTO;
import com.productServices.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

//Only writes: create, bulk create, stock update, delete.
@RestController
@RequestMapping("/api/products")
public class ProductCommandController {

    private final ProductService productService;

    public ProductCommandController(ProductService productService) {
        this.productService = productService;
    }

    // POST /api/products  (single create)
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDTO>> addProduct(
            @Valid @RequestBody CreateProductDTO dto) {

        Product product = productService.createProduct(dto);
        ProductResponseDTO response = new ProductResponseDTO(product);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, response, null));
    }

    // POST /api/products/bulk  (multiple create)
    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> createProducts(
            @Valid @RequestBody List<@Valid CreateProductDTO> dtos) {

        List<Product> products = productService.createProducts(dtos);
        List<ProductResponseDTO> result = products.stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, result, null));
    }

    // PATCH /api/products/{id}/stock?stock=10
    @PatchMapping("/{productId}/stock")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> stockUpdate(
            @PathVariable String productId,
            @RequestParam Long stock) {

        Product product = productService.updateStock(UUID.fromString(productId), stock);
        ProductResponseDTO response = new ProductResponseDTO(product);
        return ResponseEntity.ok(new ApiResponse<>(true, response, null));
    }

    // DELETE /api/products/{id}  (soft delete)
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable String productId) {
        productService.deleteProduct(UUID.fromString(productId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ApiResponse<>(true, null, null));
    }
}