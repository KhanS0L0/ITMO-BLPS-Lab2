package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublishedReviewDTO {
    private String country;
    private String city;
    private String reviewBody;
    private String advantages;
    private String disadvantages;
    private String conclusion;
    private String userLogin;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Timestamp publishedDate;
}
