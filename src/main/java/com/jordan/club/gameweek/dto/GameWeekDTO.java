package com.jordan.club.gameweek.dto;

import com.jordan.club.gameweek.enums.GameWeekStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class GameWeekDTO {

    private Long id;
    private Integer gameWeek;
    private LocalDateTime deadline;
    private GameWeekStatus status;

}
