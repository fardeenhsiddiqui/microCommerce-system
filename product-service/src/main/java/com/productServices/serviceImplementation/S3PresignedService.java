package com.productServices.serviceImplementation;

import com.productServices.model.image.PresignedUrlResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.UUID;

@Service
public class S3PresignedService {

    private final S3Presigner presigner;

    @Value("${aws.s3.bucket}")
    private String bucket;

    public S3PresignedService() {
        this.presigner = S3Presigner.builder()
                .region(Region.AP_SOUTH_1)
                .build();
    }

    public PresignedUrlResponse generateUploadUrl(UUID productId, String fileName) {

        String key = "products/" + productId + "/" + UUID.randomUUID() + "_" + fileName;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType("image/jpeg") // improve later
                .build();

        PutObjectPresignRequest presignRequest =
                PutObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(10))
                        .putObjectRequest(putObjectRequest)
                        .build();

        PresignedPutObjectRequest presignedRequest =
                presigner.presignPutObject(presignRequest);

        return new PresignedUrlResponse(
                presignedRequest.url().toString(),
                key
        );
    }
}
