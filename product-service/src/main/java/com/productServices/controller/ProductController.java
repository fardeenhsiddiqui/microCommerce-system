package com.productServices.controller;

import com.productServices.entity.Product;
import com.productServices.entity.ProductIndex;
import com.productServices.model.ApiResponse;
import com.productServices.model.product.CreateProductDTO;
import com.productServices.model.product.ProductDTO;
import com.productServices.model.product.ProductResponseDTO;
//import com.inventoryService.repository.ProductSearchRepository;
import com.productServices.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/search")
    public List<ProductIndex> search(@RequestParam String query) {
        return productService.search(query);
    }

}
