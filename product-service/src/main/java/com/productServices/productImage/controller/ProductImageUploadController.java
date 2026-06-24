package com.productServices.productImage.controller;

import com.productServices.productImage.dto.ImageRequest;
import com.productServices.productImage.dto.PresignedUrlResponse;
import com.productServices.product.Product;
import com.productServices.productImage.ProductImage;
import com.productServices.product.repo.ProductRepository;
import com.productServices.productImage.service.IImageService;
import com.productServices.productImage.service.S3PresignedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/products/{productId}/images")
public class ProductImageUploadController {

    private final S3PresignedService presignedService;
    private final IImageService IImageService;
    private final ProductRepository productRepository;

    public ProductImageUploadController(S3PresignedService presignedService, IImageService IImageService, ProductRepository productRepository) {
        this.presignedService = presignedService;
        this.IImageService = IImageService;
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

        String key = IImageService.extractKeyFromUrl(image.getImageUrl());

        return presignedService.generateDownloadUrl(key);
    }

    @PostMapping("/saving")
    public ResponseEntity<String> saveImage(
            @PathVariable UUID productId,
            @RequestBody ImageRequest request) {

        IImageService.saveImage(productId, request);
        return ResponseEntity.ok("Image saved successfully");
    }

}
