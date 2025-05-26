package com.inventoryService.controller;

import com.inventoryService.entity.Product;
import com.inventoryService.model.ApiResponse;
import com.inventoryService.model.product.CreateProductDTO;
import com.inventoryService.model.product.ProductDTO;
import com.inventoryService.model.product.ProductResponseDTO;
import com.inventoryService.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Reader;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/product/")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDTO> getAllProducts(){
        return productService.getAllProducts();
    }

    @PostMapping("addProduct")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> addProduct(@Valid @RequestBody CreateProductDTO dto){
        try{
            Product product = productService.createProduct(dto);
            ProductResponseDTO response = mapToResponseDTO(product);
            return ResponseEntity.status(HttpStatus.OK).
                    body(new ApiResponse<>(true, response,null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, null, e.getMessage()));
        }
    }

    @PostMapping("stockUpdate")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> stockUpdate(@Valid @RequestParam String productId, @RequestParam Long stock){
        try{
            Product product = productService.updateStock(UUID.fromString(productId), stock);
            ProductResponseDTO response = mapToResponseDTO(product);
            return ResponseEntity.status(HttpStatus.OK).
                    body(new ApiResponse<>(true, response,null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, null, e.getMessage()));
        }
    }

    private ProductResponseDTO mapToResponseDTO(Product product){
        return new ProductResponseDTO(product);
    }
}
