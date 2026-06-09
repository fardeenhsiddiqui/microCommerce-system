package com.notificationService.email;

import com.notificationService.email.dto.EmailRequest;
import com.notificationService.email.service.IEmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class EmailController {

    private final IEmailService emailService;

    public EmailController(IEmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("v1/email/test")
    public ResponseEntity<String> emailSender(@RequestBody EmailRequest emailRequest) {

        emailService.sendEmail(emailRequest);
        return ResponseEntity.ok("Simple email sent successfully!");
    }
}
