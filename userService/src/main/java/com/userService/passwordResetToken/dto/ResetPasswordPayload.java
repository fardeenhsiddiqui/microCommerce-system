package com.userService.passwordResetToken.dto;

public record ResetPasswordPayload(String token, String newPassword) {
}
