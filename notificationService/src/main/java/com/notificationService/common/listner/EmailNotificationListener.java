package com.notificationService.common.listner;

import com.notificationService.common.constants.EmailContentType;
import com.notificationService.common.constants.GatewayConstants;
import com.notificationService.common.constants.RabbitMQConstants;
import com.notificationService.common.event.SendEmailEvent;
import com.notificationService.email.service.IEmailService;
import com.notificationService.notification.Notification;
import com.notificationService.notification.enums.NotificationChannel;
import com.notificationService.notification.enums.NotificationStatus;
import com.notificationService.notification.service.INotificationService;

import org.slf4j.MDC;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Component
@ConditionalOnProperty(
        name = "rabbitmq.enabled",
        havingValue = "true"
)
public class EmailNotificationListener {

    private final IEmailService emailService;
    private final INotificationService notificationService;

    public EmailNotificationListener(IEmailService emailService, INotificationService notificationService) {
        this.emailService = emailService;
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = RabbitMQConstants.EMAIL_QUEUE)
    public void consume(Message message, SendEmailEvent event) {

        String correlationId = (String) message.getMessageProperties()
                .getHeaders().get(GatewayConstants.CORRELATION_ID);

        Notification notification = buildNotification(event);
        notification.setStatus(NotificationStatus.PENDING);
        notification.setChannel(NotificationChannel.EMAIL);
        notification = notificationService.save(notification);

        if (StringUtils.hasText(correlationId)) {
            MDC.put(
                    GatewayConstants.MDC_CORRELATION_ID,
                    correlationId
            );
        }

        try{
            if(event.contentType() == EmailContentType.HTML) {
                emailService.sendHtmlEmail(
                        event.to(),
                        event.subject(),
                        event.body()
                );

            } else {
                emailService.sendEmail(
                        event.to(),
                        event.subject(),
                        event.body()
                );
            }

            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(LocalDateTime.now());

        } catch (Exception e) {
            notification.setStatus(NotificationStatus.FAILED);
            notification.setFailureReason(e.getMessage());

        } finally {
            MDC.remove(GatewayConstants.MDC_CORRELATION_ID);
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

    /*
    //Send welcome email with activation url after registration
    @RabbitListener(queues = RabbitMQConstants.USER_REGISTERED_QUEUE)
    public void consumeUserRegistered(UserRegisteredEvent event) {

        Notification notification = new Notification();
        notification.setRecipient(event.email());
        notification.setSubject("Welcome to E-Commerce");
        notification.setBody("Hello "
                        + event.firstName()
                        + ", welcome to our platform."
        );
        notification.setStatus(NotificationStatus.PENDING);
        notification.setChannel(NotificationChannel.EMAIL);
        notification = notificationService.save(notification);

        try {
            emailService.sendEmail(
                    event.email(),
                    "Welcome to E-Commerce",
                    "Hello "
                            + event.firstName()
                            + ", welcome to our platform."
            );
            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(LocalDateTime.now());

        } catch (Exception ex) {

            notification.setStatus(NotificationStatus.FAILED);
            notification.setFailureReason(ex.getMessage());
        }

        notificationService.save(notification);
    }
     */
}
