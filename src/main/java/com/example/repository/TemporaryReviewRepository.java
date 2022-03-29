package com.example.repository;

import com.example.entity.review.TemporaryReview;
import com.example.entity.user.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemporaryReviewRepository extends JpaRepository<TemporaryReview, Long> {

    TemporaryReview findTemporaryReviewById(Long id);

    List<TemporaryReview> findAllByInspector(Administrator author);

}
