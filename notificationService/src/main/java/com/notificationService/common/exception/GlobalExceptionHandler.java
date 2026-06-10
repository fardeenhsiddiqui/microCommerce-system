package com.notificationService.common.exception;

import com.notificationService.common.dto.EmailErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MailAuthenticationException.class)
    public ResponseEntity<EmailErrorResponse> handleAuth(
            MailAuthenticationException ex) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        new EmailErrorResponse(
                                401,
                                "SMTP authentication failed",
                                LocalDateTime.now()
                        )
                );
    }

    @ExceptionHandler(MailSendException.class)
    public ResponseEntity<EmailErrorResponse> handleSend(
            MailSendException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(
                        new EmailErrorResponse(
                                502,
                                "Unable to send email",
                                LocalDateTime.now()
                        )
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<EmailErrorResponse> handleArgumentsException(
            MailSendException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(
                        new EmailErrorResponse(
                                502,
                                "Unable to send email",
                                LocalDateTime.now()
                        )
                );
    }
}
