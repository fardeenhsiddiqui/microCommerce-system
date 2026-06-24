package com.productServices.productImage.service;

import com.productServices.productImage.dto.ImageRequest;
import com.productServices.product.Product;
import com.productServices.productImage.ProductImage;
import com.productServices.product.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageService implements IImageService {

    private final S3Client s3Client;
    private final ProductRepository productRepository;

    @Value("${aws.s3.bucket}")
    private String bucket;

    public ImageService(S3Client s3Client, ProductRepository productRepository) {
        this.s3Client = s3Client;
        this.productRepository = productRepository;
    }

    @Transactional
    public Product uploadFile(UUID productId, MultipartFile file) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        String key = "products/" + productId + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

        // ✅ Upload to S3 (YOU WERE MISSING THIS HERE)
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(file.getContentType())
                .build();

        try {
            s3Client.putObject(request,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // ✅ Save metadata
        String imageUrl = "https://" + bucket + ".s3.amazonaws.com/" + key;

        ProductImage image = new ProductImage();
        image.setImageUrl(imageUrl);
        image.setProduct(product);

        product.getGalleryImages().add(image);

        try {
            return productRepository.save(product);
        } catch (Exception e) {
            s3Client.deleteObject(b -> b.bucket(bucket).key(key));
            throw e;
        }
    }

    @Transactional
    @Override
    public void saveImage(UUID productId, ImageRequest request) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        String imageUrl = "https://" + bucket + ".s3.amazonaws.com/" + request.key();

        ProductImage image = new ProductImage();
        image.setImageUrl(imageUrl);
        image.setLabel(request.label());
        image.setAltText(request.altText());
        image.setProduct(product);

        product.getGalleryImages().add(image);
        try {
            productRepository.save(product);
        } catch (Exception e) {
            s3Client.deleteObject(b -> b.bucket(bucket).key(request.key()));
            throw e;
        }
    }

    // Download
    @Override
    public ResponseInputStream<GetObjectResponse> downloadFile(String key) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        return s3Client.getObject(request);
    }

    @Transactional
    public void deleteImage(UUID productId, UUID imageId) {

        Product product = productRepository.findById(productId)
                .orElseThrow();

        ProductImage image = product.getGalleryImages().stream()
                .filter(i -> i.getId().equals(imageId))
                .findFirst()
                .orElseThrow();

        String key = extractKeyFromUrl(image.getImageUrl());

        s3Client.deleteObject(b -> b.bucket(bucket).key(key));

        product.getGalleryImages().remove(image);
        productRepository.save(product);
    }

    @Override
    public String extractKeyFromUrl(String url) {
        return url.substring(url.indexOf(".com/") + 5);
    }
}
