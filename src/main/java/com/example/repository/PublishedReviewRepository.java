package com.example.repository;

import com.example.entity.review.PublishedReview;
import com.example.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublishedReviewRepository extends JpaRepository<PublishedReview, Long> {

    PublishedReview findPublishedReviewById(Long id);

    List<PublishedReview> findAllByAuthor(User author);

}