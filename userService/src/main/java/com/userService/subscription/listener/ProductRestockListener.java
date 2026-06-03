package com.userService.subscription.listener;

import com.userService.common.config.RabbitMQConfig;
import com.userService.subscription.dto.ProductRestockedEvent;
import com.userService.subscription.SubscriptionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        name = "rabbitmq.enabled",
        havingValue = "true"
)
public class ProductRestockListener {

    @Autowired
    private SubscriptionService subscriptionService;

    @RabbitListener(queues = RabbitMQConfig.PRODUCT_RESTOCKED_QUEUE)
    public void handleRestock(ProductRestockedEvent event){

        System.out.println("1......." + event.getProductName() + " " + event.getProductId());
//        List<ProductSubscription> subscribers = subscriptionService.getSubscribers(event.getProductId());
//
//        for(ProductSubscription subscriber : subscribers){
//            System.out.printf("📢 Notify %s: Product '%s' is now back in stock!\n",
//                    subscriber.getUserEmail(), event.getProductName());
//
//            // In production, you'd email, push, or SMS notify here.
//        }
    }
}
