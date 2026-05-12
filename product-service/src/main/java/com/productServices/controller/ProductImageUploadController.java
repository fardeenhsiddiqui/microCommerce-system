package com.productServices.controller;

import com.productServices.entity.Product;
import com.productServices.entity.ProductImage;
import com.productServices.model.image.ImageRequest;
import com.productServices.model.image.PresignedUrlResponse;
import com.productServices.repository.ProductRepository;
import com.productServices.service.ImageService;
import com.productServices.serviceImplementation.S3PresignedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/products/{productId}/images")
public class ProductImageUploadController {

    private final S3PresignedService presignedService;
    private final ImageService imageService;
    private final ProductRepository productRepository;

    public ProductImageUploadController(S3PresignedService presignedService, ImageService imageService, ProductRepository productRepository) {
        this.presignedService = presignedService;
        this.imageService = imageService;
        this.productRepository = productRepository;
    }

    @PostMapping("/presigned-url")
    public PresignedUrlResponse getUploadUrl(
            @PathVariable UUID productId,
            @RequestParam String fileName) {

        return presignedService.generateUploadUrl(productId, fileName);
    }

    @GetMapping("/{imageId}/download-url")
    public String getDownloadUrl(@PathVariable UUID productId,
                                 @PathVariable UUID imageId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ProductImage image = product.getGalleryImages().stream()
                .filter(img -> img.getId().equals(imageId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Image not found"));

        String key = imageService.extractKeyFromUrl(image.getImageUrl());

        return presignedService.generateDownloadUrl(key);
    }

    @PostMapping("/saving")
    public ResponseEntity<String> saveImage(
            @PathVariable UUID productId,
            @RequestBody ImageRequest request) {

        imageService.saveImage(productId, request);
        return ResponseEntity.ok("Image saved successfully");
    }

}
