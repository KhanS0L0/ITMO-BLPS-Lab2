package com.example.entity.review;

import com.example.entity.user.User;
import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public class Review {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "COUNTRY", nullable = false)
    private String country;

    @Column(name = "CITY", nullable = false)
    private String city;

    @Column(name = "REVIEW", length = 1000, nullable = false)
    private String reviewBody;

    @Column(name = "ADVANTAGES", length = 500, nullable = false)
    private String advantages;

    @Column(name = "DISADVANTAGES", length = 500, nullable = false)
    private String disadvantages;

    @Column(name = "CONCLUSION", length = 500, nullable = false)
    private String conclusion;

    @ManyToOne
    @JoinColumn(name = "AUTHOR", referencedColumnName = "ID")
    private User author;
}
