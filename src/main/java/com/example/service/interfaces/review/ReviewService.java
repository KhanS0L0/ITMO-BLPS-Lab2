package com.example.service.interfaces.review;

import com.example.dto.PublishedReviewDTO;
import com.example.dto.TemporaryReviewDTO;
import com.example.entity.review.PublishedReview;
import com.example.entity.review.TemporaryReview;
import com.example.exception.UserNotFoundException;

import java.util.List;

public interface ReviewService {

    void saveNewTemporaryReview(TemporaryReviewDTO dto) throws UserNotFoundException;

    void updateTemporaryReview(TemporaryReviewDTO dto) throws NullPointerException;

    void savePublishedReview(TemporaryReview review);

    void updateTemporaryReviewStatus(TemporaryReview review);

    TemporaryReview getTemporaryReviewById(Long id);

    PublishedReview getPublishedReviewById(Long id);

    List<TemporaryReviewDTO> getAllTemporaryReviews(Long adminId);

    List<PublishedReviewDTO> getAllPublishedReviews(Long userId);

}
