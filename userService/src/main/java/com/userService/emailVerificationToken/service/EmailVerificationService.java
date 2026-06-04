package com.userService.emailVerificationToken.service;

import com.userService.common.exception.TokenNotFoundException;
import com.userService.common.exception.UserNotFoundException;
import com.userService.emailVerificationToken.EmailVerificationToken;
import com.userService.emailVerificationToken.repo.EmailVerificationTokenRepository;
import com.userService.user.User;
import com.userService.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EmailVerificationService implements IEmailVerificationService{

    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final UserRepository userRepository;

    public EmailVerificationService(EmailVerificationTokenRepository emailVerificationTokenRepository, UserRepository userRepository) {
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
        this.userRepository = userRepository;
    }


    @Override
    public EmailVerificationToken createVerificationToken(User user) {

        emailVerificationTokenRepository.deleteByUser(user);

        EmailVerificationToken token = new EmailVerificationToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(LocalDateTime.now().plusHours(24));
        token.setUsed(false);

        EmailVerificationToken saved = emailVerificationTokenRepository.save(token);

        // publish RabbitMQ event here

        return saved;
    }

    @Override
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

    @Override
    public void resendVerification(String email) {

        User user = userRepository.findByEmail(email).orElseThrow( () -> new UserNotFoundException("Email I'd not found"));

        if(Boolean.TRUE.equals(user.getEmailVerified())) {
            throw new IllegalArgumentException("Email already verified");
        }
        createVerificationToken(user);
    }
}
