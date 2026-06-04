package com.userService.emailVerificationToken.service;

import com.userService.emailVerificationToken.EmailVerificationToken;
import com.userService.user.User;

public interface IEmailVerificationService {

    EmailVerificationToken createVerificationToken(User user);

    void verifyEmail(String token);

    void resendVerification(String email);
}
