package com.userService.common.client;
/*
Responsibilities:

Only communicate with Product Service.
No business logic.
No validation.
No database access.
No controller code.

Think of it as an adapter between your service and another microservice.
 */

import com.userService.common.dto.ProductResponse;
import com.userService.common.response.ApiResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class ProductClient {

    private final RestClient restClient;

    public ProductClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("http://product-service")
                .build();
    }

    @CircuitBreaker(name="productService", fallbackMethod = "getProductFallback"
    )
    public ProductResponse getProduct(UUID productId) {
        ApiResponse<ProductResponse> response = restClient.get()
                .uri("/api/products/{productId}", productId)
                .retrieve()
                .body(new ParameterizedTypeReference<ApiResponse<ProductResponse>>() {});

        if (response == null || !response.isSuccess() || response.getData() == null) {
            throw new RuntimeException("Unable to fetch product.");
        }

        return response.getData();
    }

    public ProductResponse getProductFallback(UUID productId, Throwable ex) {

        throw new RuntimeException("Product Service is temporarily unavailable.", ex);
    }

}
