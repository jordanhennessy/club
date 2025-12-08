package com.jordan.club.fixture.dto;

import com.jordan.club.fixture.enums.FixtureStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FixtureDTO {

    private Long id;
    private String homeTeam;
    private String awayTeam;
    private int homeScore;
    private int awayScore;
    private LocalDateTime kickOffTime;
    private int gameWeek;
    private FixtureStatus status;

}
