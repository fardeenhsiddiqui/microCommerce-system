package com.userService.auth.service;


import com.userService.auth.dto.JwtResponseDTO;
import com.userService.auth.dto.LoginRequestDTO;
import com.userService.refreshToken.dto.TokenResponse;
import com.userService.user.User;
import com.userService.user.dto.CreateUserDTO;
import com.userService.user.dto.UserResponseDTO;

import javax.security.auth.login.AccountLockedException;

public interface IAuthService {

    JwtResponseDTO login(LoginRequestDTO request) throws AccountLockedException;

    UserResponseDTO register(CreateUserDTO request);

    TokenResponse refreshToken(String refreshToken);

    void logout(String refreshToken);

}
