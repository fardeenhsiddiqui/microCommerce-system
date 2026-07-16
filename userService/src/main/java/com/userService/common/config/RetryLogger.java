package com.userService.common.config;

import io.github.resilience4j.retry.RetryRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class RetryLogger {

    private final RetryRegistry retryRegistry;

    public RetryLogger(RetryRegistry retryRegistry) {
        this.retryRegistry = retryRegistry;
    }

    @PostConstruct
    public void registerRetryEvent() {

        retryRegistry.retry("productService")
                .getEventPublisher()
                .onRetry(event ->
                        System.out.println("Retry Attempt : " + event.getNumberOfRetryAttempts()));
    }
}
