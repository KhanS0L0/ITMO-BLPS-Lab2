package com.example.mapper;

import com.example.dto.UserNotificationDTO;
import com.example.entity.notification.UserNotification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationMapper {

    public UserNotification mapDTOToEntity(UserNotificationDTO dto){
        UserNotification entity = new UserNotification();
        entity.setNotificationBody(dto.getNotificationBody());
        entity.setDateOfSending(dto.getDateOfSending());
        entity.setRevisionResult(dto.getRevisionResult());
        entity.setReviewId(dto.getReviewId());
        return entity;
    }

    public UserNotificationDTO mapEntityToDTO(UserNotification entity){
        UserNotificationDTO dto = new UserNotificationDTO();
        dto.setReviewId(entity.getReviewId());
        dto.setNotificationBody(entity.getNotificationBody());
        dto.setDateOfSending(entity.getDateOfSending());
        dto.setRevisionResult(entity.getRevisionResult());
        dto.setSender(entity.getSender().getUsername());
        return dto;
    }

    public List<UserNotificationDTO> mapEntityListToDTOList(List<UserNotification> entities){
        List<UserNotificationDTO> dtoList = new ArrayList<>();
        entities.forEach(entity -> dtoList.add(mapEntityToDTO(entity)));
        return dtoList;
    }
}
