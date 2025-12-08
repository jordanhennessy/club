package com.jordan.club.match.service;


import com.jordan.club.match.client.MatchClient;
import com.jordan.club.match.model.Match;
import com.jordan.club.match.entity.Fixture;
import com.jordan.club.match.mapper.MatchMapper;
import com.jordan.club.match.repository.FixtureRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FixtureSyncService {

    private final MatchClient matchClient;
    private final MatchMapper mapper;
    private final FixtureRepository fixtureRepository;

    @Transactional
    public void refreshFixtureData() {
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

    @Transactional
    public void updateGameWeekData() {
        try {
            log.info("Updating game week data...");
            int currentGameWeek = matchClient.getCurrentGameWeek();
        } catch (Exception e) {
            log.error("Exception thrown when updating gameweeks", e);
        }
    }

}
