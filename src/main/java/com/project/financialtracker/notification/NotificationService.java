package com.project.financialtracker.notification;

import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getNewNotifications(Integer userId) {
        List<Notification> notifications = notificationRepository.getNotificationByUserId(userId);
        notifications.forEach(notification -> {
            notification.setView(true);
            notificationRepository.save(notification);
        });
        return notifications;
    }

    public List<Notification> getSeenNotifications(Integer userId){
        return notificationRepository.getNotificationByUserIdAndView(userId, false);
    }
}
