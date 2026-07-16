package com.userService.common.client;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CircuitBreakerLogger {

    private final CircuitBreakerRegistry registry;
    private static final Logger log = LoggerFactory.getLogger(CircuitBreakerLogger.class);

    public CircuitBreakerLogger(CircuitBreakerRegistry registry) {
        this.registry = registry;
    }

    @EventListener
    public void onStateTransition(
            CircuitBreakerOnStateTransitionEvent event) {

        log.info("====================================");
        log.info("CircuitBreaker : {}", event.getCircuitBreakerName());
        log.info("Transition     : {}", event.getStateTransition());
        log.info("Current State  : {}", event.getStateTransition().getToState());
        log.info("====================================");
    }

    @PostConstruct
    public void init() {

        CircuitBreaker cb = registry.circuitBreaker("productService");
        cb.getEventPublisher()
                .onStateTransition(event ->
                        System.out.println(
                                "STATE : " +
                                        event.getStateTransition()))

                .onSuccess(event ->
                        System.out.println(
                                "SUCCESS"))

                .onError(event ->
                        System.out.println(
                                "ERROR"))

                .onCallNotPermitted(event ->
                        System.out.println(
                                "OPEN - CALL BLOCKED"));
    }

}
