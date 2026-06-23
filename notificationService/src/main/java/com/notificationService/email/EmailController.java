package com.notificationService.email;

import com.notificationService.common.event.SendEmailEvent;
import com.notificationService.common.publisher.NotificationPublisher;
import com.notificationService.email.dto.EmailRequest;
import com.notificationService.email.service.IEmailService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

    private final IEmailService emailService;
    private final NotificationPublisher publisher;

    public EmailController(IEmailService emailService, NotificationPublisher publisher) {
        this.emailService = emailService;
        this.publisher = publisher;
    }

    @PostMapping("/test")
    public ResponseEntity<String> emailSender(@Valid @RequestBody EmailRequest emailRequest) {

//        emailService.sendEmail(emailRequest.to(), emailRequest.subject(), emailRequest.body());
//        publisher.publish(new SendEmailEvent(emailRequest.to(), emailRequest.subject(), emailRequest.body()));
        return ResponseEntity.ok("Email Request Accepted");
    }
}
