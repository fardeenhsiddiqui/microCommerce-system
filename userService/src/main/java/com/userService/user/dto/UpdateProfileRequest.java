package com.userService.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateProfileRequest(
        @NotNull @NotBlank String userName,
        @NotNull @NotBlank String firstName,
        @NotNull @NotBlank String lastName
) {
}
