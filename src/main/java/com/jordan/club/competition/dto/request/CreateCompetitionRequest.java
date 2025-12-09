package com.jordan.club.competition.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCompetitionRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private Integer startGameWeek;

}
