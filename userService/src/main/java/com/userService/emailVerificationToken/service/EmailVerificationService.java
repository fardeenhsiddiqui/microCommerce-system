package com.userService.emailVerificationToken.service;

import com.userService.common.constants.EmailContentType;
import com.userService.common.constants.RabbitMQConstants;
import com.userService.common.event.SendEmailEvent;
import com.userService.common.exception.TokenNotFoundException;
import com.userService.common.exception.UserNotFoundException;
import com.userService.emailVerificationToken.EmailVerificationToken;
import com.userService.common.publisher.EventPublisher;
import com.userService.emailVerificationToken.repo.EmailVerificationTokenRepository;
import com.userService.user.User;
import com.userService.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EmailVerificationService {

    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final UserRepository userRepository;
    private final EventPublisher eventPublisher;

    public EmailVerificationService(EmailVerificationTokenRepository emailVerificationTokenRepository, UserRepository userRepository, EventPublisher eventPublisher) {
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    public EmailVerificationToken createVerificationToken(User user) {

        emailVerificationTokenRepository.deleteByUser(user);

        EmailVerificationToken token = new EmailVerificationToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(LocalDateTime.now().plusHours(24));
        token.setUsed(false);

        EmailVerificationToken saved = emailVerificationTokenRepository.save(token);

        // publish RabbitMQ event here to send url for verification
        String verificationUrl = buildVerficationUrl(saved.getToken());
        String body = buildVerificationBody(user, verificationUrl);

        eventPublisher.publish(
                RabbitMQConstants.EMAIL_ROUTING_KEY,
                new SendEmailEvent(
                        user.getEmail(),
                        "Verify your Email",
                        body,
                        EmailContentType.HTML
                )
        );

        return saved;
    }

    public void verifyEmail(String token) {

        EmailVerificationToken verificationToken = emailVerificationTokenRepository.findByToken(token)
                .orElseThrow( () -> new TokenNotFoundException("Token Not Found"));

        if(Boolean.TRUE.equals(verificationToken.getUsed())) {
            throw new IllegalArgumentException("Verification link already used");
        }

        if(verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Verification link expired");
        }

        User user = verificationToken.getUser();
        user.setEmailVerified(true);
        verificationToken.setUsed(true);
        userRepository.save(user);

        emailVerificationTokenRepository.save(verificationToken);
    }

    public void resendVerification(String email) {

        User user = userRepository.findByEmail(email).orElseThrow( () -> new UserNotFoundException("Email I'd not found"));

        if(Boolean.TRUE.equals(user.getEmailVerified())) {
            throw new IllegalArgumentException("Email already verified");
        }
        createVerificationToken(user);
    }

    //Future: provide Email Template
    public String buildVerficationUrl(String token) {
        String frontendUrl = "/api/user/oauth";
        return frontendUrl
                + "/verify-email?token="
                + token;
    }

    private String buildVerificationBody(User user, String url) {

        return """
        <!DOCTYPE html>
        <html>
        <body style="font-family: Arial, sans-serif">
            <h2>Welcome to E-Commerce</h2>
            <p>Hello <b>%s</b>,</p>
            <p>
                Thank you for creating your account.
            </p>
            <p>
                Please verify your email by clicking
                the button below.
            </p>
            <a href="%s"
               style="
                    background:#0d6efd;
                    color:white;
                    padding:12px 20px;
                    text-decoration:none;
                    border-radius:5px;
               ">
                Verify Email
            </a>
            <p>
                This link expires in 24 hours.
            </p>
            <hr>
            <small>
                If you didn't create this account,
                you can ignore this email.
            </small>
        </body>
        </html>
        """
                .formatted(
                        user.getFirstName(),
                        url
                );
    }
}
