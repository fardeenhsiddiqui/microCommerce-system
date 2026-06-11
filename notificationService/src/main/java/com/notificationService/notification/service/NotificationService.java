package com.notificationService.notification.service;

import com.notificationService.notification.Notification;
import com.notificationService.notification.repo.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService implements INotificationService {

    private final NotificationRepository repository;

    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Notification save(Notification notification) {
        return repository.save(notification);
    }
}
