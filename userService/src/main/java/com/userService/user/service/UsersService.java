package com.userService.user.service;

import com.userService.common.client.ProductClient;
import com.userService.common.dto.ProductResponse;
import com.userService.common.response.ApiResponse;
import com.userService.user.User;
import com.userService.user.dto.CreateUserDTO;
import com.userService.user.dto.UserResponseDTO;
import com.userService.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UsersService implements IUserService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ProductClient productClient;

    public UsersService(UserRepository userRepository, PasswordEncoder passwordEncoder, ProductClient productClient){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.productClient = productClient;
    }

    public void updateUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
    }

    @Override
    public UserResponseDTO getCurrentUser() {
        return null;
    }

    @Override
    public UserResponseDTO updateProfile(User request) {
        return null;
    }

    @Override
    public void deactivateUser(UUID userId) {
        System.out.println("De-Activate User");
    }

    @Override
    public ProductResponse getProduct(UUID productId) {
        return productClient.getProduct(productId);
    }

}
