package com.example.service.interfaces.notification;

import com.example.dto.UserNotificationDTO;

import java.util.List;

public interface NotificationService {

    void generateNotificationToUser(UserNotificationDTO dto);

    List<UserNotificationDTO> getAllNotifications(Long userId);
}
