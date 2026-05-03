package com.productServices.controller;

import com.productServices.model.image.ImageRequest;
import com.productServices.model.image.PresignedUrlResponse;
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

    public ProductImageUploadController(S3PresignedService presignedService, ImageService imageService) {
        this.presignedService = presignedService;
        this.imageService = imageService;
    }

    @PostMapping("/presigned-url")
    public PresignedUrlResponse getUploadUrl(
            @PathVariable UUID productId,
            @RequestParam String fileName) {

        return presignedService.generateUploadUrl(productId, fileName);
    }

    @PostMapping
    public ResponseEntity<String> saveImage(
            @PathVariable UUID productId,
            @RequestBody ImageRequest request) {

        imageService.saveImage(productId, request);
        return ResponseEntity.ok("Image saved successfully");
    }
}
