package com.jordan.club.competition.mapper;

import com.jordan.club.competition.dto.CompetitionDTO;
import com.jordan.club.competition.entity.Competition;
import com.jordan.club.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CompetitionMapper {

    public Competition fromDTO(CompetitionDTO competitionDTO) {
        if (competitionDTO == null) {
            return null;
        }
        Competition.CompetitionBuilder competitionBuilder = Competition.builder()
                .id(competitionDTO.getId())
                .name(competitionDTO.getName())
                .description(competitionDTO.getDescription());

        if (competitionDTO.getOrganizerId() != null) {
            User organizer = new User();
            organizer.setId(competitionDTO.getOrganizerId());
            competitionBuilder.organizer(organizer);
        }

        if (competitionDTO.getParticipantIds() != null) {
            competitionBuilder.participants(
                    competitionDTO.getParticipantIds().stream().map(participantId -> {
                        User participant = new User();
                        participant.setId(participantId);
                        return participant;
                    }).collect(Collectors.toSet())
            );
        }

        return competitionBuilder.build();
    }

    public CompetitionDTO toDTO(Competition competition) {
        if (competition == null) {
            return null;
        }
        CompetitionDTO.CompetitionDTOBuilder dtoBuilder = CompetitionDTO.builder()
                .id(competition.getId())
                .name(competition.getName())
                .description(competition.getDescription());

        if (competition.getOrganizer() != null) {
            dtoBuilder.organizerId(competition.getOrganizer().getId());
        }

        if (competition.getParticipants() != null) {
            dtoBuilder.participantIds(
                    competition.getParticipants().stream()
                            .map(User::getId)
                            .toList()
            );
        }

        return dtoBuilder.build();
    }
}
