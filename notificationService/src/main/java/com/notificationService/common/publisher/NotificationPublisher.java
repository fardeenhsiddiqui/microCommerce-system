package com.notificationService.common.publisher;

import com.notificationService.common.constants.RabbitMQConstants;
import com.notificationService.common.event.TestEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationPublisher {

    private final RabbitTemplate rabbitTemplate;

    public NotificationPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(TestEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConstants.EMAIL_EXCHANGE,
                RabbitMQConstants.EMAIL_ROUTING_KEY,
                event
        );
    }
}
