package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TemporaryReviewDTO {
    private long reviewId;
    private String country;
    private String city;
    private String reviewBody;
    private String advantages;
    private String disadvantages;
    private String conclusion;
    private String userLogin;
}
