package com.example.controller.controllers;

import com.example.dto.TemporaryReviewDTO;
import com.example.exception.UserNotFoundException;
import com.example.service.interfaces.review.ReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/temporary")
@Api(tags = {"temporary"}, description = "Управление неопубликованными отзывами")
public class TemporaryReviewController {
    private final ReviewService reviewService;

    @Autowired
    public TemporaryReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @ApiOperation(value = "Получить все отзывы, ожидающие проверки", authorizations = @Authorization("ADMIN"))
    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity getTemporaryReviews(@RequestAttribute(name = "userId") Long administratorId){
        List<TemporaryReviewDTO> temporaryReviews = reviewService.getAllTemporaryReviews(administratorId);
        if(temporaryReviews.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        else
            return ResponseEntity.ok(temporaryReviews);
    }

    @ApiOperation(value = "Написать новый отзыв", authorizations = @Authorization("USER"))
    @PostMapping(path = "/create", produces = "application/json")
    public ResponseEntity createReview(@RequestAttribute(name = "username") String username, @RequestBody TemporaryReviewDTO dto) throws UserNotFoundException {
        dto.setUserLogin(username);
        reviewService.saveNewTemporaryReview(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "обновить содержание отзыва", authorizations = @Authorization("USER"))
    @PutMapping(path = "/update", produces = "application/json")
    public ResponseEntity updateTemporaryReview(@RequestBody TemporaryReviewDTO dto){
        reviewService.updateTemporaryReview(dto);
        return ResponseEntity.ok().build();
    }
}
