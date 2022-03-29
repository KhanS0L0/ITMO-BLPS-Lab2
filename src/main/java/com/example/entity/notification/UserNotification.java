package com.example.entity.notification;

import com.example.entity.user.Administrator;
import com.example.entity.user.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Entity(name = "NOTIFICATIONS")
public class UserNotification {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "BODY")
    private String notificationBody;

    @NotNull
    @Column(name = "DATE_OF_SENDING")
    private Timestamp dateOfSending;

    @NotNull
    @Column(name = "REVISION_RESULT")
    private String revisionResult;

    /**
     * администратор, отправивший уведомление
     **/
    @NotNull
    @ManyToOne
    @JoinColumn(name = "SENDER_ID", referencedColumnName = "ID")
    private Administrator sender;

    /**
     * пользователь, которому нужно отправить уведомление (получатель)
     **/
    @NotNull
    @ManyToOne
    @JoinColumn(name = "RECIPIENT_ID", referencedColumnName = "ID")
    private User recipient;

    @NotNull
    @Column(name = "REVIEW_ID")
    private Long reviewId;
}
