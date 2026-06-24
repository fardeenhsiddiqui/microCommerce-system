package com.productServices.product.controller;

import com.productServices.common.dto.ApiResponse;
import com.productServices.product.dto.ProductDTO;
import com.productServices.product.dto.ProductResponseDTO;
import com.productServices.productImage.dto.ImageResponseDTO;
import com.productServices.product.Product;
import com.productServices.product.ProductIndex;
import com.productServices.product.service.IProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//list, get by id, search.
@RestController
@RequestMapping("/api/products")
public class ProductQueryController {

    private final IProductService productService;

    public ProductQueryController(IProductService productService) {
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
        ProductResponseDTO response = convertToDTO(product);
        return ResponseEntity.ok(new ApiResponse<>(true, response, null));
    }

    // GET /api/products/search?query=...
    @GetMapping("/search")
    public ResponseEntity<List<ProductIndex>> search(@RequestParam String query) {
        List<ProductIndex> results = productService.search(query);
        return ResponseEntity.ok(results);
    }

    private ProductResponseDTO convertToDTO(Product product) {

        List<ImageResponseDTO> images = product.getGalleryImages().stream()
                .map(img -> new ImageResponseDTO(
                        img.getImageUrl(),
                        img.getLabel(),
                        img.getAltText()
                ))
                .toList();

        return new ProductResponseDTO(
                product,
                images
        );
    }
}
