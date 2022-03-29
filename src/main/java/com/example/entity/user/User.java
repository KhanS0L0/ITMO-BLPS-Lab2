package com.example.entity.user;

import com.example.entity.notification.UserNotification;
import com.example.entity.review.PublishedReview;
import com.example.entity.review.TemporaryReview;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "USERS")
public class User extends Account {

    /**
     * список уведомлений, пришедших данному пользователю
     **/
    @OneToMany(mappedBy = "recipient")
    private List<UserNotification> userNotifications;

    /**
     * список всех отзывов, которые ещё не опубликованы
     **/
    @OneToMany(mappedBy = "author")
    private List<TemporaryReview> temporaryReviews;

    /**
     * список опубликованных отзывов
     **/
    @OneToMany(mappedBy = "author")
    private List<PublishedReview> publishedReviews;
}
