package com.jordan.club.competition.mapper;

import com.jordan.club.competition.dto.response.CompetitionResponse;
import com.jordan.club.competition.dto.request.CreateCompetitionRequest;
import com.jordan.club.competition.entity.Competition;
import com.jordan.club.user.entity.User;
import com.jordan.club.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompetitionMapper {

    public CompetitionResponse toDTO(Competition entity) {
        return CompetitionResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .startGameWeek(entity.getStartGameWeek())
                .joinCode(entity.getJoinCode())
                .participantIds(entity.getParticipants().stream()
                        .map(User::getId)
                        .collect(Collectors.toSet()))
                .build();
    }

    public Competition mapCreateRequestToEntity(CreateCompetitionRequest newCompetition) {
        return Competition.builder()
                .name(newCompetition.getName())
                .description(newCompetition.getDescription())
                .startGameWeek(newCompetition.getStartGameWeek())
                .build();
    }
}
