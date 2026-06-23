package com.notificationService.email.service;

import com.notificationService.email.dto.EmailRequest;
import jakarta.mail.MessagingException;

public interface IEmailService {

    void sendEmail(String to, String subject, String body);

    void sendHtmlEmail(
            String to,
            String subject,
            String html
    ) throws MessagingException;
}
