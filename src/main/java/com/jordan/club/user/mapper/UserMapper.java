package com.jordan.club.user.mapper;

import com.jordan.club.competition.entity.Competition;
import com.jordan.club.user.dto.UserDTO;
import com.jordan.club.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
public class UserMapper {

    public User fromDTO(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User.UserBuilder userBuilder = User.builder()
                .id(userDTO.getId())
                .name(userDTO.getName());

        if (userDTO.getOrganizedCompetitionIds() != null) {
            userBuilder.organizedCompetitions(
                    userDTO.getOrganizedCompetitionIds().stream().map(competitionId -> {
                        Competition competition = new Competition();
                        competition.setId(competitionId);
                        return competition;
                    }).collect(Collectors.toSet())
            );
        }

        if (userDTO.getEnteredCompetitionIds() != null) {
            userBuilder.enteredCompetitions(
                    userDTO.getEnteredCompetitionIds().stream().map(competitionId -> {
                        Competition competition = new Competition();
                        competition.setId(competitionId);
                        return competition;
                    }).collect(Collectors.toSet())
            );
        }

        return userBuilder.build();
    }

    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO.UserDTOBuilder dtoBuilder = UserDTO.builder()
                .id(user.getId())
                .name(user.getName());

        if (user.getOrganizedCompetitions() != null) {
            dtoBuilder.organizedCompetitionIds(
                    user.getOrganizedCompetitions().stream()
                            .map(Competition::getId)
                            .toList()
            );
        }

        if (user.getEnteredCompetitions() != null) {
            dtoBuilder.enteredCompetitionIds(
                    user.getEnteredCompetitions().stream()
                            .map(Competition::getId)
                            .toList()
            );
        }

        return dtoBuilder.build();
    }

}
