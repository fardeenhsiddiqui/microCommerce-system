package com.userService.common.publisher;

import com.userService.common.constants.GatewayConstants;
import com.userService.common.constants.RabbitMQConstants;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public EventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(String routingKey,Object event) {

        rabbitTemplate.convertAndSend(
                RabbitMQConstants.NOTIFICATION_EXCHANGE,
                routingKey,
                event,

                /*
                RabbitMQ Correlation Propagation
                correlation ID to the AMQP message header
                 */
                message -> {
                    String correlationId = MDC.get(GatewayConstants.MDC_CORRELATION_ID);

                    if(StringUtils.hasText(correlationId)) {
                        message.getMessageProperties()
                                .setHeader(GatewayConstants.CORRELATION_ID, correlationId);

                    }
                    return message;
                }
        );
    }
}
