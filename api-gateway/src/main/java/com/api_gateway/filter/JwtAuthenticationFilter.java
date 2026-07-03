package com.api_gateway.filter;

import com.api_gateway.config.RouteValidator;
import com.api_gateway.util.GatewayJwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.http.MediaType;
import com.api_gateway.dto.ErrorResponse;

import java.time.LocalDateTime;


@Component
public class JwtAuthenticationFilter implements GlobalFilter {

    private final GatewayJwtService jwtService;
    private final RouteValidator routeValidator;
    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(GatewayJwtService gatewayJwtService, RouteValidator routeValidator, ObjectMapper objectMapper) {
        this.jwtService = gatewayJwtService;
        this.routeValidator = routeValidator;
        this.objectMapper = objectMapper;
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
            return writeErrorResponse(
                    exchange,
                    HttpStatus.UNAUTHORIZED,
                    "Authorization header is missing or invalid."
            );
        }

        String token = authHeader.substring(7);
        if (!jwtService.isTokenValid(token)) {
            return writeErrorResponse(
                    exchange,
                    HttpStatus.UNAUTHORIZED,
                    "Invalid or expired JWT token."
            );
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

        if (path.startsWith("/api/admin") && !"ADMIN".equals(role)) {
            return writeErrorResponse(
                    exchange,
                    HttpStatus.FORBIDDEN,
                    "You don't have permission to access this resource."
            );
        }

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
    }

    private Mono<Void> writeErrorResponse(ServerWebExchange exchange,
                                          HttpStatus status,
                                          String message) {

        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                exchange.getRequest().getPath().value()
        );

        try {

            byte[] bytes = objectMapper.writeValueAsBytes(error);

            DataBuffer buffer = exchange.getResponse()
                    .bufferFactory()
                    .wrap(bytes);

            return exchange.getResponse().writeWith(Mono.just(buffer));

        } catch (Exception ex) {
            return exchange.getResponse().setComplete();
        }
    }
}
