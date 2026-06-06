package com.userService.passwordResetToken.service;

import com.userService.common.exception.InvalidTokenException;
import com.userService.common.exception.TokenNotFoundException;
import com.userService.passwordResetToken.PasswordResetToken;
import com.userService.passwordResetToken.dto.ResetPasswordPayload;
import com.userService.passwordResetToken.repo.PasswordResetTokenRepository;
import com.userService.refreshToken.service.RefreshTokenService;
import com.userService.user.User;
import com.userService.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

public class PasswordResetService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetService(PasswordResetTokenRepository passwordResetTokenRepository, UserRepository userRepository, RefreshTokenService refreshTokenService, PasswordEncoder passwordEncoder) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
    }


    // create password reset token
    public PasswordResetToken sendResetPasswordLink(User user) {

        passwordResetTokenRepository.deleteByUser(user);

        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUsed(false);
        token.setUser(user);
        token.setExpiryDate(LocalDateTime.now().plusMinutes(5));

        PasswordResetToken saved = passwordResetTokenRepository.save(token);

        // publish RabbitMQ event here
        return saved;
    }

    @Transactional
    public void resetPassword(ResetPasswordPayload payload) {

        PasswordResetToken passwordResetToken = validateToken(payload.token());
        User user = passwordResetToken.getUser();

        if(passwordEncoder.matches(payload.newPassword(), user.getPassword())){
            throw new IllegalArgumentException(
                    "New password must be different from current password"
            );
        }
        user.setPassword(passwordEncoder.encode(payload.newPassword()));
        passwordResetToken.setUsed(true);

        userRepository.save(user);
        passwordResetTokenRepository.save(passwordResetToken);
        refreshTokenService.revokeUserTokens(user);
    }

    public PasswordResetToken validateToken(String token) {

        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException("Token Not found"));

        if(passwordResetToken.getUsed()) {
            throw new InvalidTokenException("Password reset token already used");
        }

        if(passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now())){
            throw new InvalidTokenException("Password reset token expired");
        }

        return passwordResetToken;
    }

    /*
    Generate reset token
    Publish email event
     */
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email).orElse(null);

        if(user == null){
            return;
        }
        sendResetPasswordLink(user);
    }


}
