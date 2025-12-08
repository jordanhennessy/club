package com.jordan.club.competition.enums;

import java.util.Arrays;

public enum CompetitionStatus {
    DRAFT,
    ACTIVE,
    COMPLETED,
    CANCELLED;

    public static CompetitionStatus getByName(String name) {
        return Arrays.stream(CompetitionStatus.values())
                .filter(status -> status.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
