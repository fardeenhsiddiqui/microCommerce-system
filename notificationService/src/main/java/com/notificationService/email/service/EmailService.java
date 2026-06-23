package com.notificationService.email.service;

import com.notificationService.email.dto.EmailRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
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
    public void sendEmail(String to, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        log.info("Sending email to {}", to);
        mailSender.send(message);
        log.info("Email sent successfully");
    }

    @Override
    public void sendHtmlEmail(String to, String subject, String html) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // Pass "true" to constructor flags to indicate a multipart message request
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setFrom(sender);
        helper.setTo(to);
        helper.setSubject(subject);

        // Setting the second parameter to true renders the content as HTML
        helper.setText(html, true);

        // Optional: Attach local server files
//        if (attachmentPath != null) {
//            FileSystemResource fileSystemResource = new FileSystemResource(new File(attachmentPath));
//            helper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
//        }

        mailSender.send(mimeMessage);

    }
}
