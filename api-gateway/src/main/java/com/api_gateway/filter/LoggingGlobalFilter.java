package com.api_gateway.filter;

import com.api_gateway.constant.GatewayConstants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingGlobalFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(LoggingGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        long startTime = System.currentTimeMillis();

        String method = exchange.getRequest().getMethod().name();
        String path = exchange.getRequest().getURI().getPath();
        String requestId = exchange.getRequest().getHeaders()
                .getFirst(GatewayConstants.REQUEST_ID);

        log.info("[{}] Incoming Request -> {} {}",requestId, method, path);

        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {

                    long executionTime = System.currentTimeMillis() - startTime;

                    int status = exchange.getResponse()
                            .getStatusCode() != null ? exchange.getResponse().getStatusCode().value() : 0;

                    log.info("[{}] Outgoing Response -> Status: {}, Execution Time: {}",
                            requestId,
                            status,
                            executionTime);
                }));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
