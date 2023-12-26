package com.project.financialtracker.notification;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<NotificationDto> getNewNotifications(Integer userId) {
        List<Notification> notifications = notificationRepository.getNotificationByUserIdAndView(userId, false);
        return notifications.stream().map(notification -> new NotificationDto(notification.getNotificationId(), notification.getAlerts(),notification.getTimeStamp(), notification.getView())).toList();
    }

    public List<NotificationDto> getAllNotifications(Integer userId) {
        List<Notification> notifications = notificationRepository.getNotificationByUserId(userId);
        if (notifications.isEmpty()) {
            return Collections.emptyList();
        }
        return notifications.stream().map(notification -> new NotificationDto(notification.getNotificationId(), notification.getAlerts(),notification.getTimeStamp(), notification.getView())).toList();
    }

}
