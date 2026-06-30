package com.api_gateway.filter;

import com.api_gateway.config.RouteValidator;
import com.api_gateway.util.GatewayJwtService;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class JwtAuthenticationFilter implements GlobalFilter {

    private final GatewayJwtService jwtService;
    private final RouteValidator routeValidator;

    public JwtAuthenticationFilter(GatewayJwtService gatewayJwtService, RouteValidator routeValidator) {
        this.jwtService = gatewayJwtService;
        this.routeValidator = routeValidator;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        if (!routeValidator.isSecured(exchange.getRequest())) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);
        if (!jwtService.isTokenValid(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String username = jwtService.extractUsername(token);
        String email = jwtService.extractClaim(token,
                claims -> claims.get("email", String.class)
        );
        String role = jwtService.extractClaim(token,
                claims -> claims.get("role", String.class)
        );
        String userId = jwtService.extractClaim(token,
                claims -> claims.get("userId", String.class)
        );

        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(
                        exchange.getRequest()
                                .mutate()
                                .header("X-User-Id", userId)
                                .header("X-User-Email", email)
                                .header("X-User-Role", role)
                                .build()
                )
                .build();

        return chain.filter(mutatedExchange);
//        return chain.filter(exchange);
    }
}
