package com.project.financialtracker.notification;

import com.project.financialtracker.utils.ResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/new-notification")
    public ResponseEntity<ResponseWrapper<List<NotificationDto>>> getNewNotifications(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        ResponseWrapper<List<NotificationDto>> response = new ResponseWrapper<>();
        try{
            if(!notificationService.getNewNotifications(userId).isEmpty()){
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage("Notifications retrieved successfully");
                response.setSuccess(true);
                response.setResponse(notificationService.getNewNotifications(userId));
                return ResponseEntity.ok(response);
            }else{
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("Notification not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        }catch(Exception e){
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/notification")
    public ResponseEntity<ResponseWrapper<List<NotificationDto>>> getNotifications(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        ResponseWrapper<List<NotificationDto>> response = new ResponseWrapper<>();
        try{
            List<NotificationDto> notifications = notificationService.getAllNotifications(userId);
            if(!notifications.isEmpty()){
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage("Notifications retrieved successfully");
                response.setSuccess(true);
                response.setResponse(notifications);
                return ResponseEntity.ok(response);
            }else{
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("Notification not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        }catch(Exception e){
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
