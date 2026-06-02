package com.userService.refreshToken.service;

import com.userService.common.exception.InvalidCredentialsException;
import com.userService.refreshToken.RefreshToken;
import com.userService.refreshToken.repo.RefreshTokenRepository;
import com.userService.user.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenService implements IRefreshTokenService{

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository){
        this.refreshTokenRepository = refreshTokenRepository;
    }

    // Create a new session record for the authenticated user
    @Override
    public RefreshToken createRefreshToken(User user) {

        refreshTokenRepository.deleteByUser(user);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7));
        refreshToken.setRevoked(false);

        return refreshTokenRepository.save(refreshToken);
    }

    // Verify that the session token is valid and not revoked
    @Override
    public RefreshToken validateRefreshToken(String token) {

        RefreshToken refreshToken = refreshTokenRepository
                        .findByToken(token)
                        .orElseThrow(
                                () -> new InvalidCredentialsException(
                                        "Invalid refresh token")
                        );

        if(Boolean.TRUE.equals(refreshToken.getRevoked())) {
            throw new InvalidCredentialsException(
                    "Refresh token revoked"
            );
        }

        if(refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new InvalidCredentialsException(
                    "Refresh token expired"
            );
        }
        return refreshToken;
    }

    // Invalidate a user session during logout
    @Override
    @Transactional
    public void revokeToken(String token) {

        RefreshToken refreshToken = validateRefreshToken(token);
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public void revokeUserTokens(User user) {

    }
}
