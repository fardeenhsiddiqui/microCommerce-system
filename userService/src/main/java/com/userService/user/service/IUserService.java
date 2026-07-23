package com.userService.user.service;

import com.userService.common.dto.ProductResponse;
import com.userService.user.dto.UpdateProfileRequest;
import com.userService.user.dto.UserResponseDTO;

import java.util.UUID;

public interface IUserService {

    UserResponseDTO getCurrentUser();

    UserResponseDTO updateProfile(UpdateProfileRequest request);

    void deactivateCurrentUser();

    ProductResponse getProduct(UUID productId);
}
