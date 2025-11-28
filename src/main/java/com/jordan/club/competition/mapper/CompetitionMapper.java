package com.jordan.club.competition.mapper;

import com.jordan.club.competition.dto.CompetitionDTO;
import com.jordan.club.competition.entity.Competition;
import org.springframework.stereotype.Component;

@Component
public class CompetitionMapper {

    public Competition fromDTO(CompetitionDTO competitionDTO) {
        return Competition.builder()
                .id(competitionDTO.getId())
                .name(competitionDTO.getName())
                .description(competitionDTO.getDescription())
                .build();
    }

    public CompetitionDTO toDTO(Competition competition) {
        return CompetitionDTO.builder()
                .id(competition.getId())
                .name(competition.getName())
                .description(competition.getDescription())
                .build();
    }
}
