package com.jordan.club.match.service;


import com.jordan.club.match.client.MatchClient;
import com.jordan.club.match.dto.Match;
import com.jordan.club.match.entity.Fixture;
import com.jordan.club.match.mapper.MatchMapper;
import com.jordan.club.match.repository.FixtureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.concurrent.TimeUnit.HOURS;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class MatchSyncService {

    private final MatchClient matchClient;
    private final MatchMapper mapper;
    private final FixtureRepository fixtureRepository;

    @Scheduled(fixedRate = 1, timeUnit = HOURS)
    public void syncMatchData() {
        try {
            log.info("Fetching matches from API");
            List<Match> matches = matchClient.getAllMatches();
            log.info("Successfully fetched {} matches from the API, refreshing the DB", matches.size());
            List<Fixture> fixtures = matches.stream().map(mapper::fromDTO).toList();
            fixtureRepository.deleteAll();
            fixtureRepository.saveAll(fixtures);
            log.info("DB refreshed successfully");
        } catch (Exception e) {
            log.error("Exception thrown when fetching matches from API", e);
        }
    }


}
