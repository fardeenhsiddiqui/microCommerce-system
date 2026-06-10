package com.notificationService.email.service;

import com.notificationService.email.dto.EmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService{

    private final JavaMailSender mailSender;
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Value("${spring.mail.username}")
    private String sender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(EmailRequest emailRequest) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(emailRequest.to());
        message.setSubject(emailRequest.subject());
        message.setText(emailRequest.body());

        log.info("Sending email to {}", emailRequest.to());
        mailSender.send(message);
        log.info("Email sent successfully");
    }
}
