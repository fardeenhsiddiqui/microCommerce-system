package com.productServices.product.controller;

import com.productServices.common.dto.ApiResponse;
import com.productServices.product.dto.CreateProductDTO;
import com.productServices.product.dto.ProductResponseDTO;
import com.productServices.product.dto.UpdateProductDTO;
import com.productServices.product.dto.UpdateProductStatusDTO;
import com.productServices.product.service.ProductCommandService;
import com.productServices.productImage.dto.ImageResponseDTO;
import com.productServices.product.Product;
import com.productServices.product.service.IProductService;
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

    private final ProductCommandService productService;

    public ProductCommandController(ProductCommandService productService) {
        this.productService = productService;
    }

    // POST /api/products  (single create)
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDTO>> addProduct(
            @Valid @RequestBody CreateProductDTO dto) {

        ProductResponseDTO product = productService.createProduct(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, product, null));
    }

    // POST /api/products/bulk  (multiple create)
    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> createProducts(
            @Valid @RequestBody List<@Valid CreateProductDTO> dtos) {

        List<ProductResponseDTO> result = productService.createProducts(dtos);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, result, null));
    }

    @PostMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> updateProduct(
            @PathVariable UUID productId,
            @Valid @RequestBody UpdateProductDTO dto) {

        ProductResponseDTO result = productService.updateProduct(productId, dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, result, null));
    }

    // PATCH /api/products/{id}/stock?stock=10
    @PatchMapping("/{productId}/stock")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> stockUpdate(
            @PathVariable String productId,
            @RequestParam Long stock) {

        ProductResponseDTO product = productService.updateStock(UUID.fromString(productId), stock);
        return ResponseEntity.ok(new ApiResponse<>(true, product, null));
    }

    // DELETE /api/products/{id}  (soft delete)
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable String productId) {
        productService.deleteProduct(UUID.fromString(productId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ApiResponse<>(true, null, null));
    }

    @PatchMapping("/{productId}/status")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> updateStatus(
            @PathVariable UUID productId,
            @RequestBody @Valid UpdateProductStatusDTO dto) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        productService.updateStatus(productId, dto),
                        null
                )
        );
    }

}
