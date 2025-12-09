package com.jordan.club.competition.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCompetitionRequest {

    private String name;
    private String description;
    private Integer startGameWeek;

}
