package com.notificationService.common.constants;

public class RabbitMQConstants {

    private RabbitMQConstants() {
    }

    public static final String EMAIL_EXCHANGE = "notification.exchange";

    public static final String EMAIL_QUEUE = "email.queue";

    public static final String EMAIL_ROUTING_KEY = "email.send";
}
