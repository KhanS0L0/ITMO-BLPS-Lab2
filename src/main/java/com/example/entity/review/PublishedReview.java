package com.example.entity.review;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Timestamp;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "PUBLISHED_REVIEWS")
public class PublishedReview extends Review{
    @Column(name = "PUBLISHED_DATE", nullable = false)
    private Timestamp publishedDate;
}
