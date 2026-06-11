package com.notificationService.common.controller;


import com.notificationService.common.event.SendEmailEvent;
import com.notificationService.common.publisher.NotificationPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rabbit")
public class RabbitTestController {

    private final NotificationPublisher publisher;

    public RabbitTestController(NotificationPublisher publisher) {
        this.publisher = publisher;
    }

    @PostMapping("/test")
    public ResponseEntity<String> publish() {

//        publisher.publish(new SendEmailEvent("Hello RabbitMQ"));
        return ResponseEntity.ok("Message Published");
    }
}
