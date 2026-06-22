package com.notificationService.common.constants;

public class RabbitMQConstants {

    private RabbitMQConstants() {
    }

    public static final String EMAIL_EXCHANGE = "notification.exchange";

    //QUEUE
    public static final String EMAIL_QUEUE = "email.queue";

    public static final String EMAIL_SEND_KEY = "email.send";

    //Routing Key
    public static final String EMAIL_ROUTING_KEY = "email.send";

    //Registration Queue, routing key
    public static final String USER_REGISTERED_QUEUE = "user.registered.queue";
    public static final String USER_REGISTERED_ROUTING_KEY = "email.user.registered";
}
