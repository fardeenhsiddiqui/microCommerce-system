package com.notificationService.email.service;

import com.notificationService.email.dto.EmailRequest;

public interface IEmailService {

    void sendEmail(EmailRequest emailRequest);
}
