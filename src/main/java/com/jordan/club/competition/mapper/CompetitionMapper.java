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

    private final UserRepository userRepository;

    public Competition fromDTO(CompetitionResponse dto) {
        Competition competition = Competition.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .joinCode(dto.getJoinCode())
                .participants(new HashSet<>())
                .build();

        // Load participants from repository if IDs are provided
        if (dto.getParticipantIds() != null && !dto.getParticipantIds().isEmpty()) {
            var participants = dto.getParticipantIds().stream()
                    .map(id -> userRepository.findById(id)
                            .orElseThrow(() -> new IllegalArgumentException("User not found: " + id)))
                    .collect(Collectors.toSet());
            competition.setParticipants(participants);
        }

        return competition;
    }

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

    }
}
