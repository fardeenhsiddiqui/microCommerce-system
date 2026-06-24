package com.productServices.productImage.controller;

import com.productServices.common.dto.ApiResponse;
import com.productServices.productImage.dto.ImageResponseDTO;
import com.productServices.productImage.dto.ProductImageResponse;
import com.productServices.product.Product;
import com.productServices.productImage.service.IImageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
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

    private final IImageService IImageService;

    public ProductImageController(IImageService IImageService) {
        this.IImageService = IImageService;
    }

    //Need to add other fields value label, altText
    @PostMapping("/upload/{productId}")
    public ResponseEntity<ApiResponse<ProductImageResponse>> upload(
            @PathVariable String productId,
            @RequestParam("file") MultipartFile file) {

        Product product = IImageService.uploadFile(UUID.fromString(productId), file);
        ProductImageResponse response = mapToImageDTO(product);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, response, null));
    }

    @GetMapping("/download/**")
    public ResponseEntity<byte[]> download(HttpServletRequest request) throws IOException {

        String fullPath = request.getRequestURI();
        // extract key after /download/
        String key = fullPath.substring(fullPath.indexOf("/download/") + 10);
        ResponseInputStream<GetObjectResponse> s3Object =
                IImageService.downloadFile(key);

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
