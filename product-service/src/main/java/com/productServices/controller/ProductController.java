package com.productServices.controller;

import com.productServices.entity.Product;
import com.productServices.entity.ProductIndex;
import com.productServices.model.ApiResponse;
import com.productServices.model.product.CreateProductDTO;
import com.productServices.model.product.ProductDTO;
import com.productServices.model.product.ProductResponseDTO;
import com.productServices.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

//    private final ProductService productService;

//    public ProductController(ProductService productService) {
//        this.productService = productService;
//    }

//    @GetMapping
//    public List<ProductDTO> getAllProducts(){
//        return productService.getAllProducts();
//    }



//    // POST /api/products (single create)
//    @PostMapping
//    public ResponseEntity<ApiResponse<ProductResponseDTO>> addProduct(
//            @Valid @RequestBody CreateProductDTO dto) {
//        try {
//            Product product = productService.createProduct(dto);
//            ProductResponseDTO response = mapToResponseDTO(product);
//            return ResponseEntity.status(HttpStatus.CREATED)
//                    .body(new ApiResponse<>(true, response, null));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new ApiResponse<>(false, null, e.getMessage()));
//        }
//    }

    // PATCH /api/products/{id}/stock?stock=10
//    @PatchMapping("/{productId}/stock")
//    public ResponseEntity<ApiResponse<ProductResponseDTO>> stockUpdate(
//            @PathVariable String productId,
//            @RequestParam Long stock) {
//        try {
//            Product product = productService.updateStock(UUID.fromString(productId), stock);
//            ProductResponseDTO response = mapToResponseDTO(product);
//            return ResponseEntity.status(HttpStatus.OK)
//                    .body(new ApiResponse<>(true, response, null));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new ApiResponse<>(false, null, e.getMessage()));
//        }
//    }

//    // GET /api/products/search?query=...
//    @GetMapping("/search")
//    public ResponseEntity<List<ProductIndex>> search(@RequestParam String query) {
//        List<ProductIndex> results = productService.search(query);
//        return ResponseEntity.ok(results);
//    }
//
//    // Mapping helper
//    private ProductResponseDTO mapToResponseDTO(Product product) {
//        return new ProductResponseDTO(product);
//    }
}