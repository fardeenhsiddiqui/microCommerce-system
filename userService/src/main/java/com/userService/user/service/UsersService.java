package com.userService.user.service;

import com.userService.common.client.ProductClient;
import com.userService.common.dto.ProductResponse;
import com.userService.common.exception.UserAlreadyExistsException;
import com.userService.common.exception.UserNotFoundException;
import com.userService.refreshToken.service.RefreshTokenService;
import com.userService.user.User;
import com.userService.user.dto.UpdateProfileRequest;
import com.userService.user.dto.UserResponseDTO;
import com.userService.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UsersService implements IUserService {

    private final UserRepository userRepository;
    private final ProductClient productClient;
    private final RefreshTokenService refreshTokenService;

    public UsersService(UserRepository userRepository, ProductClient productClient, RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.productClient = productClient;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public UserResponseDTO getCurrentUser() {
        return mapToResponseDTO(getCurrentAuthenticatedUser());
    }

    @Override
    @Transactional
    public UserResponseDTO updateProfile(UpdateProfileRequest request) {
        User currentUser = getCurrentAuthenticatedUser();

        String normalizedUserName = normalizeUserName(request.userName());
        ensureUserNameAvailable(currentUser, normalizedUserName);

        currentUser.setUserName(normalizedUserName);
        currentUser.setFirstName(request.firstName().trim());
        currentUser.setLastName(request.lastName().trim());

        User savedUser = userRepository.save(currentUser);
        return mapToResponseDTO(savedUser);
    }

    @Override
    @Transactional
    public void deactivateCurrentUser() {
        User currentUser = getCurrentAuthenticatedUser();
        currentUser.setActive(false);
        currentUser.setAccountLocked(false);
        refreshTokenService.revokeUserTokens(currentUser);
        userRepository.save(currentUser);
    }

    @Override
    public ProductResponse getProduct(java.util.UUID productId) {
        return productClient.getProduct(productId);
    }

    private User getCurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new UserNotFoundException("Authenticated user not found");
        }

        String principal = authentication.getName();

        return userRepository.findByUserName(principal)
                .or(() -> userRepository.findByEmail(principal))
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private void ensureUserNameAvailable(User currentUser, String requestedUserName) {
        userRepository.findByUserName(requestedUserName)
                .filter(existing -> !existing.getId().equals(currentUser.getId()))
                .ifPresent(existing -> {
                    throw new UserAlreadyExistsException("Username already taken");
                });
    }

    private String normalizeUserName(String userName) {
        return userName.trim().toLowerCase();
    }

    private UserResponseDTO mapToResponseDTO(User user) {
        return new UserResponseDTO(user);
    }
}
