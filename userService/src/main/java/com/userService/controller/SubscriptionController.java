package com.userService.controller;

import com.userService.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService service;

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@RequestParam UUID userId,
                                            @RequestParam UUID productId,
                                            @RequestParam String userEmail){
        service.subscribeToProduct(userId, productId, userEmail);

        return ResponseEntity.ok("Subscribed Successfully!");
    }
}
