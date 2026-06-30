package com.api_gateway.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RouteValidator {

    public static final List<String> PUBLIC_APIS = List.of(
            "/auth/login",
            "/auth/register",
            "/auth/forgot-password",
            "/auth/reset-password",
            "/auth/verify-email"
    );

    public boolean isSecured(ServerHttpRequest request) {
        return PUBLIC_APIS.stream()
                .noneMatch(uri -> request.getURI().getPath().startsWith(uri));
    }
}
