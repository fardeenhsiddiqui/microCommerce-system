package com.userService.refreshToken.service;

import com.userService.refreshToken.RefreshToken;
import com.userService.user.User;

public interface IRefreshTokenService {

    RefreshToken createRefreshToken(User user);

    RefreshToken validateRefreshToken(String token);

    void revokeToken(String token);

    void revokeUserTokens(User user);
}
