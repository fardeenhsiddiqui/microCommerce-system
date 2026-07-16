package com.userService.user.service;

import com.userService.common.dto.ProductResponse;
import com.userService.user.User;
import com.userService.user.dto.UserResponseDTO;

import java.util.UUID;

public interface IUserService {

    UserResponseDTO getCurrentUser();

    UserResponseDTO updateProfile(User request);

    void deactivateUser(UUID userId);

    ProductResponse getProduct(UUID productId);
}
