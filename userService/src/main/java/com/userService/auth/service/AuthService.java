package com.userService.auth.service;

import com.userService.auth.dto.JwtResponseDTO;
import com.userService.auth.dto.LoginRequestDTO;
import com.userService.auth.dto.TokenResponse;
import com.userService.common.exception.AccountLockedException;
import com.userService.common.exception.UserAlreadyExistsException;
import com.userService.common.utils.JwtUtil;
import com.userService.user.User;
import com.userService.user.dto.CreateUserDTO;
import com.userService.user.dto.UserResponseDTO;
import com.userService.user.enums.Role;
import com.userService.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public class AuthService implements IAuthService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
//    private final RefreshTokenService refreshTokenService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public JwtResponseDTO login(LoginRequestDTO request) {

        Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.userName(),
                                request.password()
                        )
                );

        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        User user = userRepository
                .findByUserName(userDetails.getUsername());

        if(user.getAccountLocked()) {
            throw new AccountLockedException(
                    "Account is locked"
            );
        }
        String accessToken =
                jwtUtil.generateAccessToken(user);

        String refreshToken =
                jwtUtil.generateRefreshToken(user);
        user.setLastLoginAt(LocalDateTime.now());
        //Failed Login Reset
//        user.setFailedLoginAttempts(0);
        return new JwtResponseDTO(
                accessToken,
                refreshToken
        );
    }

    @Override
    @Transactional
    public UserResponseDTO register(CreateUserDTO dto) {

        validateDuplicateUser(dto);


        User user = mapToEntity(dto);

        user.setRole(Role.CUSTOMER);
        user.setActive(true);
        user.setEmailVerified(false);
        user.setAccountLocked(false);
        user.setFailedLoginAttempts(0);
        user.setDeleted(false);
        user.setLastLoginAt(null);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        User savedUser = userRepository.save(user);
        return mapToResponseDTO(savedUser);
    }

    @Override
    public TokenResponse refreshToken(String refreshToken) {
        return null;
    }

    private UserResponseDTO mapToResponseDTO(User user){
        return new UserResponseDTO(user);
    }

    private User mapToEntity(CreateUserDTO dto) {

        User user = new User();
        String normalizedEmail = dto.getEmail().trim().toLowerCase();
        String normalizedUsername = dto.getUserName().trim().toLowerCase();
        user.setUserName(normalizedUsername);
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(normalizedEmail);

        return user;
    }

    private void validateDuplicateUser(CreateUserDTO dto) {

        if(userRepository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsException(
                    "Email already registered"
            );
        }
        if(userRepository.existsByUserName(dto.getUserName())) {
            throw new UserAlreadyExistsException(
                    "Username already taken"
            );
        }
    }
}
