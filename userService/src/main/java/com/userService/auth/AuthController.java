package com.userService.auth;

import com.userService.auth.dto.JwtResponseDTO;
import com.userService.auth.dto.LoginRequestDTO;
import com.userService.auth.service.IAuthService;
import com.userService.emailVerificationToken.dto.ResendVerificationRequest;
import com.userService.emailVerificationToken.service.IEmailVerificationService;
import com.userService.refreshToken.dto.RefreshTokenRequest;
import com.userService.refreshToken.dto.TokenResponse;
import com.userService.common.response.ApiResponse;
import com.userService.user.dto.CreateUserDTO;
import com.userService.user.dto.UserResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user/oauth")
public class AuthController {

    private final IAuthService authService;
    private final IEmailVerificationService emailVerificationService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthController(IAuthService authService, IEmailVerificationService emailVerificationService) {
        this.authService = authService;
        this.emailVerificationService = emailVerificationService;
    }

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

    @GetMapping("/verify-email")
    public ResponseEntity<ApiResponse<String>> verifyEmail(@RequestParam String token) {

        emailVerificationService.verifyEmail(token);
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Email verified successfully",
                        null)
        );
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<ApiResponse<String>> resendVerification(@RequestBody ResendVerificationRequest request) {

        emailVerificationService.resendVerification(request.email());
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Verification email sent",
                        null)
        );
    }

}
