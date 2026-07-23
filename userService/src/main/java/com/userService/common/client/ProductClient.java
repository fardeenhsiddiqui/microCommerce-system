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

import com.userService.common.constants.GatewayConstants;
import com.userService.common.dto.ProductResponse;
import com.userService.common.exception.ProductNotFoundException;
import com.userService.common.exception.ProductServiceUnavailableException;
import com.userService.common.response.ApiResponse;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class ProductClient {

    private final RestClient restClient;
    private static final Logger log = LoggerFactory.getLogger(ProductClient.class);

    public ProductClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("http://product-service")
                .build();
    }

    @Retry(name = "productService")
    @CircuitBreaker(name="productService", fallbackMethod = "getProductFallback"
    )
    public ProductResponse getProduct(UUID productId) {

        log.info("Calling Product Service for productId={}", productId);
        ApiResponse<ProductResponse> response = restClient.get()
                .uri("/api/products/{productId}", productId)
                .retrieve()
                .body(new ParameterizedTypeReference<ApiResponse<ProductResponse>>() {});

        if (response == null || !response.isSuccess() || response.getData() == null) {
            throw new ProductNotFoundException("Unable to fetch product.");
        }

        return response.getData();
    }

    public ProductResponse getProductFallback(UUID productId, Throwable ex) {

        if (ex instanceof CallNotPermittedException) {

            throw new RuntimeException("Circuit Breaker is OPEN");
        }
        throw new ProductServiceUnavailableException("Product Service is temporarily unavailable.");
    }

}
