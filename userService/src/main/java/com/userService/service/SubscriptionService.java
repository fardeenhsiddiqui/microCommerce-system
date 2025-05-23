package com.userService.service;

import com.userService.entity.ProductSubscription;
import com.userService.repository.ProductSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SubscriptionService {

    @Autowired
    private ProductSubscriptionRepository repository;

    public void subscribeToProduct(UUID userId, UUID productId, String userEmail) {
        ProductSubscription subscription = new ProductSubscription();
        subscription.setProductId(productId);
        subscription.setUserId(userId);
        subscription.setUserEmail(userEmail);
        subscription.setSubscribedAt(LocalDateTime.now());
        repository.save(subscription);
    }

    public List<ProductSubscription> getSubscribers(UUID productId) {
        return repository.findByProductId(productId);
    }
}
