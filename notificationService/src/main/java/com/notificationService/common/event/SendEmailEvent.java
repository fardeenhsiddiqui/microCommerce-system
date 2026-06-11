package com.notificationService.common.event;

public record SendEmailEvent(
        String to,
        String subject,
        String body
) {
}
