package com.jordan.club.fixture.mapper;

import com.jordan.club.fixture.dto.FixtureDTO;
import com.jordan.club.fixture.entity.Fixture;
import com.jordan.club.fixture.model.FixtureResponse;
import org.springframework.stereotype.Component;

@Component
public class FixtureMapper {

    public Fixture fromAPIResponse(FixtureResponse fixtureResponse) {
        return Fixture.builder()
                .homeTeam(fixtureResponse.getHomeTeam().getName())
                .awayTeam(fixtureResponse.getAwayTeam().getName())
                .homeScore(fixtureResponse.getScore().getFullTime().getHomeScore())
                .awayScore(fixtureResponse.getScore().getFullTime().getAwayScore())
                .matchDate(fixtureResponse.getDate())
                .status(fixtureResponse.getStatus())
                .gameWeek(fixtureResponse.getMatchday())
                .build();
    }

    public Fixture fromDTO(FixtureDTO fixtureDTO) {
        return Fixture.builder()
                .id(fixtureDTO.getId())
                .homeTeam(fixtureDTO.getHomeTeam())
                .awayTeam(fixtureDTO.getAwayTeam())
                .homeScore(fixtureDTO.getHomeScore())
                .awayScore(fixtureDTO.getAwayScore())
                .matchDate(fixtureDTO.getKickOffTime())
                .gameWeek(fixtureDTO.getGameWeek())
                .status(fixtureDTO.getStatus())
                .build();
    }

    public FixtureDTO toDTO(Fixture fixture) {
        return FixtureDTO.builder()
                .id(fixture.getId())
                .homeTeam(fixture.getHomeTeam())
                .awayTeam(fixture.getAwayTeam())
                .homeScore(fixture.getHomeScore())
                .awayScore(fixture.getAwayScore())
                .kickOffTime(fixture.getMatchDate())
                .gameWeek(fixture.getGameWeek())
                .status(fixture.getStatus())
                .build();
    }

}
