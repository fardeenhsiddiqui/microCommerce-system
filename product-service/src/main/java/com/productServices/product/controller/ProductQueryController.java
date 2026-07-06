package com.productServices.product.controller;

import com.productServices.common.dto.ApiResponse;
import com.productServices.product.dto.ProductDTO;
import com.productServices.product.dto.ProductResponseDTO;
import com.productServices.product.service.ProductQueryService;
import com.productServices.productImage.dto.ImageResponseDTO;
import com.productServices.product.Product;
import com.productServices.product.ProductIndex;
import com.productServices.product.service.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//list, get by id, search.
@RestController
@RequestMapping("/api/products")
public class ProductQueryController {

    private final ProductQueryService productQueryService;

    public ProductQueryController(ProductQueryService productQueryService) {
        this.productQueryService = productQueryService;
    }

    // GET /api/products
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getAllProducts() {
        List<ProductResponseDTO> products = productQueryService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, products, null));
    }

    // GET /api/products/{id}
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> getProduct(@PathVariable String productId) {
        ProductResponseDTO response = productQueryService.getProduct(UUID.fromString(productId));
        return ResponseEntity.ok(new ApiResponse<>(true, response, null));
    }

    // GET /api/products/search?query=...
    @GetMapping("/search")
    public ResponseEntity<List<ProductIndex>> search(@RequestParam String query) {
        List<ProductIndex> results = productQueryService.search(query);
        return ResponseEntity.ok(results);
    }

}
