package com.productServices.gateway.publisher;

import com.productServices.gateway.QueueKeysName;
import com.productServices.event.ProductRestockedEvent;
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
        rabbitTemplate.convertAndSend(QueueKeysName.PRODUCT_EXCHANGE, QueueKeysName.PRODUCT_RESTOCK_KEY, event);
    }
}
