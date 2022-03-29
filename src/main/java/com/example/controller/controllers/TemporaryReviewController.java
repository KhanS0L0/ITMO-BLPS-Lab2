package com.example.controller.controllers;

import com.example.dto.TemporaryReviewDTO;
import com.example.exception.UserNotFoundException;
import com.example.service.interfaces.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/temporary")
public class TemporaryReviewController {
    private final ReviewService reviewService;

    @Autowired
    public TemporaryReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    //todo: find a new way to get and userId or you can just change it on username
    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity getTemporaryReviews(@RequestAttribute(name = "userId") Long administratorId){
        List<TemporaryReviewDTO> temporaryReviews = reviewService.getAllTemporaryReviews(administratorId);
        if(temporaryReviews.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        else
            return ResponseEntity.ok(temporaryReviews);
    }

    @PostMapping(path = "/create", produces = "application/json")
    public ResponseEntity createReview(@RequestAttribute(name = "username") String username, @RequestBody TemporaryReviewDTO dto) throws UserNotFoundException {
        dto.setUserLogin(username);
        reviewService.saveNewTemporaryReview(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(path = "/update", produces = "application/json")
    public ResponseEntity updateTemporaryReview(@RequestBody TemporaryReviewDTO dto){
        reviewService.updateTemporaryReview(dto);
        return ResponseEntity.ok().build();
    }
}
