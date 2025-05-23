package com.userService.repository;

import com.userService.entity.ProductSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductSubscriptionRepository extends JpaRepository<ProductSubscription, UUID> {
    List<ProductSubscription> findByProductId(UUID productId);
}
