package com.api_gateway.filter;

import com.api_gateway.constant.GatewayConstants;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class RequestIdGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String requestId = exchange.getRequest()
                .getHeaders().getFirst(GatewayConstants.REQUEST_ID);
        if(requestId == null || requestId.isBlank()) {
            requestId = UUID.randomUUID().toString();
        }

        ServerWebExchange modifiedExchange = exchange.mutate()
                .request(exchange.getRequest().mutate()
                        .header(GatewayConstants.REQUEST_ID, requestId)
                        .build())
                .build();

        modifiedExchange.getResponse()
                .getHeaders().add(GatewayConstants.REQUEST_ID, requestId);
        return chain.filter(modifiedExchange);
    }

    @Override
    public int getOrder() {
        return -2;
    }
}
