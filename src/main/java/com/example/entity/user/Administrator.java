package com.example.entity.user;

import com.example.entity.notification.UserNotification;
import com.example.entity.review.TemporaryReview;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "ADMINISTRATORS")
public class Administrator extends Account {
    /**
     * список отзывов, которые необходимо просмотреть
     **/
    @OneToMany(mappedBy = "inspector")
    private List<TemporaryReview> inspections;

    /**
     * список уведомлений, отправленных пользователю
     **/
    @OneToMany(mappedBy = "sender")
    private List<UserNotification> senderUserNotifications;


}
