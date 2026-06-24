package com.productServices.productImage.service;

import com.productServices.productImage.dto.ImageRequest;
import com.productServices.product.Product;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.util.UUID;

public interface IImageService {

    public Product uploadFile(UUID productId, MultipartFile file);

    public void saveImage(UUID productId, ImageRequest request);

    public ResponseInputStream<GetObjectResponse> downloadFile(String key);

    public String extractKeyFromUrl(String imageUrl);
}
