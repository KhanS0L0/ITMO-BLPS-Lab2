package com.example.mapper;

import com.example.dto.PublishedReviewDTO;
import com.example.entity.review.PublishedReview;
import com.example.entity.review.TemporaryReview;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class PublishedReviewMapper {

    public PublishedReview mapTemporaryReviewToPublishedReview(TemporaryReview temporaryReview){
        PublishedReview review = new PublishedReview();
        review.setCountry(temporaryReview.getCountry());
        review.setCity(temporaryReview.getCity());
        review.setReviewBody(temporaryReview.getReviewBody());
        review.setAdvantages(temporaryReview.getAdvantages());
        review.setDisadvantages(temporaryReview.getDisadvantages());
        review.setConclusion(temporaryReview.getConclusion());
        review.setAuthor(temporaryReview.getAuthor());
        review.setPublishedDate(new Timestamp(new Date().getTime()));
        return review;
    }

    public PublishedReviewDTO mapEntityToDTO(PublishedReview entity){
        PublishedReviewDTO dto = new PublishedReviewDTO();
        dto.setCountry(entity.getCountry());
        dto.setCity(entity.getCity());
        dto.setReviewBody(entity.getReviewBody());
        dto.setAdvantages(entity.getAdvantages());
        dto.setDisadvantages(entity.getDisadvantages());
        dto.setConclusion(entity.getConclusion());
        dto.setUserLogin(entity.getAuthor().getUsername());
        dto.setPublishedDate(entity.getPublishedDate());
        return dto;
    }

    public List<PublishedReviewDTO> mapEntityListToDTOList(List<PublishedReview> entities){
        List<PublishedReviewDTO> dtoList = new ArrayList<>();
        entities.forEach(entity -> dtoList.add(mapEntityToDTO(entity)));
        return dtoList;
    }
}
