package com.notificationService.common.listner;

import com.notificationService.common.constants.RabbitMQConstants;
import com.notificationService.common.event.SendEmailEvent;
import com.notificationService.email.service.IEmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    private final IEmailService emailService;

    public NotificationListener(IEmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = RabbitMQConstants.EMAIL_QUEUE)
    public void consume(SendEmailEvent event) {
//        System.out.println("Received Message: " + event.message());
        emailService.sendEmail(event.to(), event.subject(), event.body());
    }
}
