package com.userService.common.client;

import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CircuitBreakerLogger {

    @EventListener
    public void onStateTransition(CircuitBreakerOnStateTransitionEvent event) {

        System.out.println("Time : " + LocalDateTime.now());
        System.out.println("STATE : " + event.getStateTransition());
    }

}
