package com.userService.user.admin;

import com.userService.common.response.ApiResponse;
import com.userService.user.dto.UserResponseDTO;
import com.userService.user.dto.UserRoleUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserAdminController {

    private final UserAdminService userAdminService;

    public UserAdminController(UserAdminService userAdminService) {
        this.userAdminService = userAdminService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserResponseDTO>>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<UserResponseDTO> response = userAdminService.getUsers(page, size);
        return ResponseEntity.ok(new ApiResponse<>(true, response, null));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@PathVariable UUID userId) {
        UserResponseDTO response = userAdminService.getUserById(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, response, null));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{userId}/roles")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUserRole(
            @PathVariable UUID userId,
            @Valid @RequestBody UserRoleUpdateRequest request) {
        UserResponseDTO response = userAdminService.updateUserRole(userId, request.role());
        return ResponseEntity.ok(new ApiResponse<>(true, response, null));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<String>> deactivateUser(@PathVariable UUID userId) {
        userAdminService.deactivateUser(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "User account deactivated", null));
    }
}
