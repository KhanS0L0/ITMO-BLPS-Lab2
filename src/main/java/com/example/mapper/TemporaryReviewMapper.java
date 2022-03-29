package com.example.mapper;

import com.example.dto.TemporaryReviewDTO;
import com.example.entity.enums.ReviewStatus;
import com.example.entity.review.TemporaryReview;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TemporaryReviewMapper {

    public TemporaryReview mapDTOToEntity(TemporaryReviewDTO dto){
        TemporaryReview review = new TemporaryReview();
        review.setCountry(dto.getCountry());
        review.setCity(dto.getCity());
        review.setReviewBody(dto.getReviewBody());
        review.setAdvantages(dto.getAdvantages());
        review.setDisadvantages(dto.getDisadvantages());
        review.setConclusion(dto.getConclusion());
        return review;
    }

    public TemporaryReviewDTO mapEntityToDTO(TemporaryReview review){
        TemporaryReviewDTO dto = new TemporaryReviewDTO();
        dto.setReviewId(review.getId());
        dto.setCountry(review.getCountry());
        dto.setCity(review.getCity());
        dto.setAdvantages(review.getAdvantages());
        dto.setDisadvantages(review.getDisadvantages());
        dto.setReviewBody(review.getReviewBody());
        dto.setConclusion(review.getConclusion());
        dto.setUserLogin(review.getAuthor().getUsername());
        return dto;
    }

    public List<TemporaryReviewDTO> mapEntityListToDTOList(List<TemporaryReview> entities){
        List<TemporaryReviewDTO> dtoList = new ArrayList<>();
        entities.forEach(entity -> dtoList.add(mapEntityToDTO(entity)));
        return dtoList;
    }

    public void updateEntity(TemporaryReview entity, TemporaryReviewDTO dto){
        entity.setCountry(dto.getCountry());
        entity.setCity(dto.getCity());
        entity.setReviewBody(dto.getReviewBody());
        entity.setAdvantages(dto.getAdvantages());
        entity.setDisadvantages(dto.getDisadvantages());
        entity.setConclusion(dto.getConclusion());
        entity.setReviewStatus(ReviewStatus.ON_REVISION);
    }
}
