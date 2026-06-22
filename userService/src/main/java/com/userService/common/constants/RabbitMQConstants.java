package com.userService.common.constants;

public class RabbitMQConstants {

    private RabbitMQConstants() {
    }

    public static final String PRODUCT_RESTOCKED_QUEUE = "product.restock";

    public static final String NOTIFICATION_EXCHANGE = "notification.exchange";

    //Routing Key
    public static final String USER_REGISTERED_ROUTING_KEY = "email.user.registered";

//    public static final String EMAIL_QUEUE = "email.queue";
//
//    public static final String EMAIL_ROUTING_KEY = "email.send";
}
