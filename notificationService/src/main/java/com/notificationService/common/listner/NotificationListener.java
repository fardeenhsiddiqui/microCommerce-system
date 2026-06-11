package com.notificationService.common.listner;

import com.notificationService.common.constants.RabbitMQConstants;
import com.notificationService.common.event.SendEmailEvent;
import com.notificationService.email.service.IEmailService;
import com.notificationService.notification.Notification;
import com.notificationService.notification.enums.NotificationChannel;
import com.notificationService.notification.enums.NotificationStatus;
import com.notificationService.notification.service.INotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NotificationListener {

    private final IEmailService emailService;
    private final INotificationService notificationService;

    public NotificationListener(IEmailService emailService, INotificationService notificationService) {
        this.emailService = emailService;
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = RabbitMQConstants.EMAIL_QUEUE)
    public void consume(SendEmailEvent event) {

        Notification notification = buildNotification(event);
        notification.setStatus(NotificationStatus.PENDING);
        notification.setChannel(NotificationChannel.EMAIL);
        notification = notificationService.save(notification);

        try{
            emailService.sendEmail(event.to(), event.subject(), event.body());
            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(LocalDateTime.now());

        } catch (Exception e) {
            notification.setStatus(NotificationStatus.FAILED);
            notification.setFailureReason(e.getMessage());
        }

        notificationService.save(notification);
    }

    public Notification buildNotification(SendEmailEvent event) {

        Notification notification = new Notification();
        notification.setRecipient(event.to());
        notification.setBody(event.body());
        notification.setSubject(event.subject());

        return notification;
    }
}
