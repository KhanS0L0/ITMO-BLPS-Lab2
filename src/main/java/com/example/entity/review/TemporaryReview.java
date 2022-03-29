package com.example.entity.review;

import com.example.entity.enums.ReviewStatus;
import com.example.entity.user.Administrator;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "TEMPORARY_REVIEWS")
public class TemporaryReview extends Review {

    @Column(name = "REVIEW_STATUS", nullable = false)
    private String status;

    @Transient
    private ReviewStatus reviewStatus;

    @ManyToOne
    @JoinColumn(name = "INSPECTOR_ID", referencedColumnName = "ID", nullable = false)
    private Administrator inspector;

    @PrePersist
    public void prePersist() {
        if (reviewStatus != null) {
            status = reviewStatus.name();
        }
    }

    @PostLoad
    public void postLoad() {
        if (status != null) {
            reviewStatus = ReviewStatus.of(status);
        }
    }
}
