package com.jordan.club.competition.dto.response;

import com.jordan.club.competition.enums.CompetitionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionResponse {

    private Long id;
    private String name;
    private String description;
    private CompetitionStatus status;
    private Integer startGameWeek;
    private String joinCode;

    @Builder.Default
    private Set<Long> participantIds = new HashSet<>();
}
