package com.productServices.controller;

import com.productServices.entity.Product;
import com.productServices.model.ApiResponse;
import com.productServices.model.image.ImageResponseDTO;
import com.productServices.model.image.ProductImageResponse;
import com.productServices.model.product.ProductResponseDTO;
import com.productServices.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
public class ProductImageController {

    private final ImageService imageService;

    public ProductImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    //Need to add other fields value label, altText
    @PostMapping("/upload/{productId}")
    public ResponseEntity<ApiResponse<ProductImageResponse>> upload(
            @PathVariable String productId,
            @RequestParam("file") MultipartFile file) {

        Product product = imageService.uploadFile(UUID.fromString(productId), file);
        ProductImageResponse response = mapToImageDTO(product);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, response, null));
    }

    //Not working
    @GetMapping("/download/{key}")
    public ResponseEntity<byte[]> download(@PathVariable String key) throws IOException {

        ResponseInputStream<GetObjectResponse> s3Object =
                imageService.downloadFile(key);

        byte[] content = s3Object.readAllBytes();

        return ResponseEntity.ok()
                .header("Content-Type", s3Object.response().contentType())
                .body(content);
    }

    public ProductImageResponse mapToImageDTO(Product product) {
        List<ImageResponseDTO> imageDTOs = product.getGalleryImages().stream()
                .map(img -> new ImageResponseDTO(
                        img.getImageUrl(),
                        img.getLabel(),
                        img.getAltText()
                ))
                .toList();

        return new ProductImageResponse(
                product.getName(),
                imageDTOs
        );
    }

}
