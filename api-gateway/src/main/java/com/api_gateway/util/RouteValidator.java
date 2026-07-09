package com.api_gateway.util;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class RouteValidator {

    public static final List<String> PUBLIC_APIS = List.of(
            "/auth/login",
            "/auth/register",
            "/auth/forgot-password",
            "/auth/reset-password",
            "/auth/verify-email"
    );

    private static final Map<String, List<String>> ROLE_BASED_APIS = Map.of(
            "/api/admin/**", List.of("ADMIN"),
            "/api/products/admin/**", List.of("ADMIN"),
            "/api/users/admin/**", List.of("ADMIN")
    );

    public boolean isSecured(ServerHttpRequest request) {
        return PUBLIC_APIS.stream()
                .noneMatch(uri -> request.getURI().getPath().startsWith(uri));
    }

    public Optional<List<String>> getRequiredRoles(String path) {

        AntPathMatcher matcher = new AntPathMatcher();

        return ROLE_BASED_APIS.entrySet()
                .stream()
                .filter(entry -> matcher.match(entry.getKey(), path))
                .map(Map.Entry::getValue)
                .findFirst();
    }
}
