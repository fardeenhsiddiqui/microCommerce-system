package com.productServices.service;

import com.productServices.entity.Product;
import com.productServices.model.image.ImageRequest;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.util.UUID;

public interface ImageService {

    public Product uploadFile(UUID productId, MultipartFile file);

    public void saveImage(UUID productId, ImageRequest request);

    public ResponseInputStream<GetObjectResponse> downloadFile(String key);

}
