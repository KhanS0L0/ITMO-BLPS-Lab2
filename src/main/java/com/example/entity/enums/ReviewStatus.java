package com.example.entity.enums;

import java.util.stream.Stream;

public enum ReviewStatus {
    ON_REVISION,
    ACCEPTED,
    REJECTED,
    EDITS_NEEDED;

    public static ReviewStatus of(String value) {
        return Stream.of(ReviewStatus.values())
                .filter(statuses -> statuses.toString().equals(value))
                .findFirst()
                .orElse(null);
    }
}
