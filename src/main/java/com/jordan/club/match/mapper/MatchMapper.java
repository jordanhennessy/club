package com.jordan.club.match.mapper;

import com.jordan.club.match.model.FullTime;
import com.jordan.club.match.model.Match;
import com.jordan.club.match.model.Score;
import com.jordan.club.match.model.Team;
import com.jordan.club.match.entity.Fixture;
import org.springframework.stereotype.Component;

@Component
public class MatchMapper {

    public Fixture fromDTO(Match match) {
        return Fixture.builder()
                .homeTeam(match.getHomeTeam().getName())
                .awayTeam(match.getAwayTeam().getName())
                .homeScore(match.getScore().getFullTime().getHomeScore())
                .awayScore(match.getScore().getFullTime().getAwayScore())
                .matchDate(match.getDate())
                .status(match.getStatus())
                .gameWeek(match.getMatchday())
                .build();
    }

    public Match toDTO(Fixture fixture) {
        return Match.builder()
                .homeTeam(new Team(fixture.getHomeTeam()))
                .awayTeam(new Team(fixture.getAwayTeam()))
                .score(Score.builder()
                        .fullTime(FullTime.builder()
                                .homeScore(fixture.getHomeScore())
                                .awayScore(fixture.getAwayScore())
                                .build())
                        .build())
                .date(fixture.getMatchDate())
                .status(fixture.getStatus())
                .matchday(fixture.getGameWeek())
                .build();
    }

}
