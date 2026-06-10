package com.notificationService.common.dto;

import java.time.LocalDateTime;

public record EmailErrorResponse(
        int status,
        String message,
        LocalDateTime timestamp
) {
}
