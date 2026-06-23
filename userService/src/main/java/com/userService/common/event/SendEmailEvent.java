package com.userService.common.event;

import com.userService.common.constants.EmailContentType;

public record SendEmailEvent(
        String to,
        String subject,
        String body,
        EmailContentType contentType
) {
}
