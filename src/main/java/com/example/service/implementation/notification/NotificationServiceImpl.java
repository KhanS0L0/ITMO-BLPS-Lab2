package com.example.service.implementation.notification;

import com.example.dto.UserNotificationDTO;
import com.example.entity.enums.ReviewStatus;
import com.example.entity.notification.UserNotification;
import com.example.entity.review.TemporaryReview;
import com.example.entity.user.Administrator;
import com.example.entity.user.User;
import com.example.mapper.NotificationMapper;
import com.example.repository.notification.UserNotificationRepository;
import com.example.service.interfaces.notification.NotificationService;
import com.example.service.interfaces.review.ReviewService;
import com.example.service.interfaces.user.AdministratorService;
import com.example.service.interfaces.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final ReviewService reviewService;
    private final AdministratorService administratorService;
    private final UserService userService;
    private final NotificationMapper notificationMapper;
    private final UserNotificationRepository userNotificationRepository;

    @Autowired
    public NotificationServiceImpl(ReviewService reviewService,
                                   AdministratorService administratorService,
                                   UserService userService,
                                   NotificationMapper notificationMapper,
                                   UserNotificationRepository userNotificationRepository) {
        this.reviewService = reviewService;
        this.administratorService = administratorService;
        this.userService = userService;
        this.notificationMapper = notificationMapper;
        this.userNotificationRepository = userNotificationRepository;
    }

    @Override
    public void generateNotificationToUser(UserNotificationDTO dto) {
        UserNotification notification = notificationMapper.mapDTOToEntity(dto);

        Administrator administrator = administratorService.findAdministratorByUsername(dto.getSender());
        TemporaryReview review = reviewService.getTemporaryReviewById(dto.getReviewId());
        User author = review.getAuthor();

        notification.setSender(administrator);
        notification.setRecipient(author);
        notification.setDateOfSending(new Timestamp(new Date().getTime()));

        userNotificationRepository.save(notification);
        updateReviewInfo(review, dto.getRevisionResult());
    }

    @Override
    public List<UserNotificationDTO> getAllNotifications(Long userId) {
        List<UserNotification> notifications = userService.findUserById(userId).getUserNotifications();
        return notificationMapper.mapEntityListToDTOList(notifications);
    }

    private void updateReviewInfo(TemporaryReview review, String revisionResult){
        if(revisionResult.equals("accepted")){
            review.setReviewStatus(ReviewStatus.ACCEPTED);
            reviewService.savePublishedReview(review);
        }else if (revisionResult.equals("rejected")){
            review.setReviewStatus(ReviewStatus.REJECTED);
            reviewService.updateTemporaryReviewStatus(review);
        }else {
            review.setReviewStatus(ReviewStatus.EDITS_NEEDED);
            reviewService.updateTemporaryReviewStatus(review);
        }
    }
}
