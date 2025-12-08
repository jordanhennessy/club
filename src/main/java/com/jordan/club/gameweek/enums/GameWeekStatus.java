package com.jordan.club.gameweek.enums;

import java.util.Arrays;

public enum GameWeekStatus {
    UPCOMING,
    ACTIVE,
    COMPLETED;

    public static GameWeekStatus getByName(String name) {
        return Arrays.stream(GameWeekStatus.values())
                .filter(status -> status.name().equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }
}
