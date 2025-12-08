package com.jordan.club.fixture.enums;

import java.util.Arrays;

public enum FixtureStatus {
    SCHEDULED,
    TIMED,
    IN_PLAY,
    PAUSED,
    FINISHED;

    public static FixtureStatus getByName(String name) {
        return Arrays.stream(FixtureStatus.values())
                .filter(status -> status.name().equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }
}
