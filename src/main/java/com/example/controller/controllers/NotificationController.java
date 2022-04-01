package com.example.controller.controllers;

import com.example.dto.UserNotificationDTO;
import com.example.service.interfaces.notification.NotificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/notification")
@Api(tags = {"notifications"}, description = "Управление пользовательскими уведомлениями")
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @ApiOperation(value = "Отправить уведомление пользователю", authorizations = @Authorization("ADMIN"))
    @PostMapping(path = "/send", produces = "application/json")
    public ResponseEntity sendNotification(@RequestAttribute(name = "username") String username, @RequestBody UserNotificationDTO dto){
        dto.setSender(username);
        notificationService.generateNotificationToUser(dto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Получить все пришедшие уведомления", authorizations = @Authorization("USER"))
    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity getAllUserNotifications(@RequestAttribute(name = "userId") Long userId){
        List<UserNotificationDTO> notifications = notificationService.getAllNotifications(userId);
        if(notifications.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(notifications);
    }
}
