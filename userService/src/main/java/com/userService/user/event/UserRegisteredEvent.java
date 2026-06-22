package com.userService.user.event;

import java.util.UUID;

public record UserRegisteredEvent(
        UUID userId,

        String firstName,

        String email
) {
}
