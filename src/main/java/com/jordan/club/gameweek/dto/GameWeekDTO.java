package com.jordan.club.gameweek.dto;

import com.jordan.club.gameweek.enums.GameWeekStatus;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GameWeekDTO {

    private Long id;
    private Integer gameWeek;
    private GameWeekStatus status;

}
