package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserNotificationDTO {
    private String notificationBody;
    private Timestamp dateOfSending;
    private String sender;
    private String revisionResult;
    private Long reviewId;
}