package com.userService.user.dto;

import com.userService.user.enums.Role;
import jakarta.validation.constraints.NotNull;

public record UserRoleUpdateRequest(
        @NotNull Role role
) {
}
