package com.project.financialtracker.notification;


import com.project.financialtracker.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Integer notificationId;
    @Column(name = "alert")
    private String alerts;
    @Column(name = "date")
    private LocalDateTime timeStamp;
    @Column(name = "view")
    private Boolean view = false;
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}
