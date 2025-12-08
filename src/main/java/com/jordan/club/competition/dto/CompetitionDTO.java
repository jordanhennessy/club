package com.jordan.club.competition.dto;

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
public class CompetitionDTO {

    private Long id;
    private String name;
    private String description;
    private CompetitionStatus status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String joinCode;

    @Builder.Default
    private Set<Long> participantIds = new HashSet<>();
}
