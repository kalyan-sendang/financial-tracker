package com.project.financialtracker.notification;

import com.project.financialtracker.utils.ResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
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

    @GetMapping("/unseennotification")
    public ResponseEntity<ResponseWrapper<List<Notification>>> getNewNotifications(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        ResponseWrapper<List<Notification>> response = new ResponseWrapper<>();
        try{
            if(notificationService.getNewNotifications(userId) != null){
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
    public ResponseEntity<ResponseWrapper<List<Notification>>> getNotifications(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        ResponseWrapper<List<Notification>> response = new ResponseWrapper<>();
        try{
            if(notificationService.getSeenNotifications(userId) != null){
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage("Notifications retrieved successfully");
                response.setSuccess(true);
                response.setResponse(notificationService.getSeenNotifications(userId));
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

}
