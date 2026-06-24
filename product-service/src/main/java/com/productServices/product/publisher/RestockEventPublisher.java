package com.productServices.product.publisher;

import com.productServices.common.constants.RabbitMQConstants;
import com.productServices.product.event.ProductRestockedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RestockEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public RestockEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(ProductRestockedEvent event){
        System.out.println("1........2" + event.getProductName());
        rabbitTemplate.convertAndSend(RabbitMQConstants.PRODUCT_EXCHANGE, RabbitMQConstants.PRODUCT_RESTOCK_KEY, event);
    }
}
