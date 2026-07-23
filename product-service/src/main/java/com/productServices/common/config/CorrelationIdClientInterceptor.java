package com.productServices.common.config;

import com.productServices.common.constants.GatewayConstants;
import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Component
public class CorrelationIdClientInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request,
                                        byte[] body,
                                        ClientHttpRequestExecution execution)
            throws IOException {

        String correlationId = MDC.get(GatewayConstants.MDC_CORRELATION_ID);

        if (StringUtils.hasText(correlationId)) {
            request.getHeaders()
                    .set(GatewayConstants.CORRELATION_ID, correlationId);
        }

        return execution.execute(request, body);
    }
}