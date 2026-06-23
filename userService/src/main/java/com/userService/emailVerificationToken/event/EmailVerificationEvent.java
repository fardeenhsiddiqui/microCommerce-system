package com.userService.emailVerificationToken.event;

public record EmailVerificationEvent(
        String email,
        String firstName,
        String verificationUrl
) {
}
