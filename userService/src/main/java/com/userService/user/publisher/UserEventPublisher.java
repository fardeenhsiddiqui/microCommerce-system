package com.userService.user.publisher;

import com.userService.common.constants.RabbitMQConstants;
import com.userService.user.event.UserRegisteredEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserEventPublisher {

    private final RabbitTemplate rabbitTemplate;


    public UserEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishedUserRegisteredEvent(UserRegisteredEvent userRegisteredEvent) {

        rabbitTemplate.convertAndSend(
                RabbitMQConstants.NOTIFICATION_EXCHANGE,
                RabbitMQConstants.USER_REGISTERED_ROUTING_KEY,
                userRegisteredEvent
        );
    }
}
