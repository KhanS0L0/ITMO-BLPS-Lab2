package com.example.controller.controllers;

import com.example.dto.PublishedReviewDTO;
import com.example.service.interfaces.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/published")
public class PublishedReviewController {
    private final ReviewService reviewService;

    @Autowired
    public PublishedReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    //todo: find a new way to get and userId or you can just change it on username
    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity getPublishedReviews(@RequestAttribute(name = "userId") Long userId){
        List<PublishedReviewDTO> publishedReviews = reviewService.getAllPublishedReviews(userId);
        if(publishedReviews.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("You don't have any published reviews");
        else
            return ResponseEntity.ok(publishedReviews);
    }

}
