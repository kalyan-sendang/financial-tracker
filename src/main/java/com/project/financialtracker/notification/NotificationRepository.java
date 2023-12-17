package com.project.financialtracker.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    @Query(value = "select * from Notification where user_id = :userId", nativeQuery = true)
    List<Notification> getNotificationByUserId(Integer userId);

    @Query(value = "select * from Notification where user_id = :userId and view = :view", nativeQuery = true)
    List<Notification> getNotificationByUserIdAndView(Integer userId, Boolean view);


}
