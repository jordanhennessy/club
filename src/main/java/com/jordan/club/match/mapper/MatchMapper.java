package com.jordan.club.match.mapper;

import com.jordan.club.match.dto.Match;
import com.jordan.club.match.dto.Team;
import com.jordan.club.match.entity.Fixture;
import org.springframework.stereotype.Component;

@Component
public class MatchMapper {

    public Fixture fromDTO(Match match) {
        return Fixture.builder()
                .homeTeam(match.getHomeTeam().getName())
                .awayTeam(match.getAwayTeam().getName())
                .matchDate(match.getDate())
                .status(match.getStatus())
                .gameWeek(match.getMatchday())
                .build();
    }

    public Match toDTO(Fixture fixture) {
        return Match.builder()
                .homeTeam(new Team(fixture.getHomeTeam()))
                .awayTeam(new Team(fixture.getAwayTeam()))
                .date(fixture.getMatchDate())
                .status(fixture.getStatus())
                .matchday(fixture.getGameWeek())
                .build();
    }

}
