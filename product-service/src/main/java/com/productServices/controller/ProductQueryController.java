package com.productServices.controller;

import com.productServices.entity.Product;
import com.productServices.entity.ProductIndex;
import com.productServices.model.ApiResponse;
import com.productServices.model.product.ProductDTO;
import com.productServices.model.product.ProductResponseDTO;
import com.productServices.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

//list, get by id, search.
@RestController
@RequestMapping("/api/products")
public class ProductQueryController {

    private final ProductService productService;

    public ProductQueryController(ProductService productService) {
        this.productService = productService;
    }

    // GET /api/products
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // GET /api/products/{id}
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> getProduct(@PathVariable String productId) {
        Product product = productService.getProduct(UUID.fromString(productId));
        ProductResponseDTO response = new ProductResponseDTO(product);
        return ResponseEntity.ok(new ApiResponse<>(true, response, null));
    }

    // GET /api/products/search?query=...
    @GetMapping("/search")
    public ResponseEntity<List<ProductIndex>> search(@RequestParam String query) {
        List<ProductIndex> results = productService.search(query);
        return ResponseEntity.ok(results);
    }
}