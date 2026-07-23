package com.userService.user.admin;

import com.userService.common.exception.UserNotFoundException;
import com.userService.refreshToken.service.RefreshTokenService;
import com.userService.user.User;
import com.userService.user.dto.UserResponseDTO;
import com.userService.user.enums.Role;
import com.userService.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserAdminService {

    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    public UserAdminService(UserRepository userRepository, RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
    }

    public Page<UserResponseDTO> getUsers(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size))
                .map(UserResponseDTO::new);
    }

    public UserResponseDTO getUserById(UUID userId) {
        return mapToResponseDTO(findUserById(userId));
    }

    @Transactional
    public UserResponseDTO updateUserRole(UUID userId, Role role) {
        User user = findUserById(userId);
        user.setRole(role);
        return mapToResponseDTO(userRepository.save(user));
    }

    @Transactional
    public void deactivateUser(UUID userId) {
        User user = findUserById(userId);
        user.setActive(false);
        refreshTokenService.revokeUserTokens(user);
        userRepository.save(user);
    }

    private User findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private UserResponseDTO mapToResponseDTO(User user) {
        return new UserResponseDTO(user);
    }
}
