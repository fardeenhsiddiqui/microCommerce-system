package com.userService.auth;

import com.userService.auth.dto.JwtResponseDTO;
import com.userService.auth.dto.LoginRequestDTO;
import com.userService.auth.service.AuthService;
import com.userService.refreshToken.dto.RefreshTokenRequest;
import com.userService.refreshToken.dto.TokenResponse;
import com.userService.user.User;
import com.userService.common.response.ApiResponse;
import com.userService.user.dto.CreateUserDTO;
import com.userService.user.dto.UserResponseDTO;
import com.userService.user.service.UsersService;
import com.userService.common.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountLockedException;

@RestController
@RequestMapping("/auth/user")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDTO>> register(@Valid @RequestBody CreateUserDTO dto){

        UserResponseDTO response = authService.register(dto);;
        return ResponseEntity.status(HttpStatus.OK).
                body(new ApiResponse<>(true, response,null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponseDTO>> login(@RequestBody LoginRequestDTO dto) {

        JwtResponseDTO response = authService.login(dto);
        return ResponseEntity.ok(new ApiResponse<>(true, response, null));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenResponse>> refreshToken(@RequestBody RefreshTokenRequest request) {

        TokenResponse response = authService.refreshToken(request.refreshToken());
        return ResponseEntity.ok(new ApiResponse<>(true, response, null));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(@RequestBody RefreshTokenRequest request) {

        authService.logout(request.refreshToken());
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Logged out",
                        null
                ));
    }

}
