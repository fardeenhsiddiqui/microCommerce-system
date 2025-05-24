package com.userService.listener;

import com.userService.config.RabbitMQConfig;
import com.userService.entity.ProductSubscription;
import com.userService.model.ProductRestockedEvent;
import com.userService.service.SubscriptionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
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
