package com.notificationService.common.event;

import com.notificationService.common.constants.EmailContentType;

public record SendEmailEvent(
        String to,
        String subject,
        String body,
        EmailContentType contentType
) {
}
