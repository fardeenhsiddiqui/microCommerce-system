package com.productServices.product.controller;

import com.productServices.common.dto.ApiResponse;
import com.productServices.product.dto.PageResponse;
import com.productServices.product.dto.ProductFilter;
import com.productServices.product.dto.ProductIndexDTO;
import com.productServices.product.dto.ProductResponseDTO;
import com.productServices.product.service.ProductQueryService;
import com.productServices.product.ProductIndex;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<ApiResponse<PageResponse<ProductResponseDTO>>> getProducts(
            ProductFilter filter,
            @PageableDefault(page = 0, size = 10, sort = "createdDate", direction = Sort.Direction.DESC)
            Pageable pageable
            ) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, productQueryService.getProducts(filter, pageable), null));
    }

    // GET /api/products/{id}
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> getProduct(@PathVariable String productId) {
        ProductResponseDTO response = productQueryService.getProduct(UUID.fromString(productId));
        return ResponseEntity.ok(new ApiResponse<>(true, response, null));
    }

    // GET /api/products/search?query=...
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ProductIndexDTO>>> search(@RequestParam String query) {
        List<ProductIndexDTO> results = productQueryService.search(query);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, results, null));
    }

}
