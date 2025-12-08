package com.jordan.club.fixture.service;


import com.jordan.club.fixture.client.FixtureClient;
import com.jordan.club.fixture.entity.Fixture;
import com.jordan.club.fixture.model.FixtureResponse;
import com.jordan.club.fixture.mapper.FixtureMapper;
import com.jordan.club.fixture.repository.FixtureRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FixtureSyncService {

    private final FixtureClient fixtureClient;
    private final FixtureMapper mapper;
    private final FixtureRepository fixtureRepository;

    @Transactional
    public void refreshFixtureData() {
        try {
            log.info("Fetching fixtures from API");
            List<FixtureResponse> fixtureResponses = fixtureClient.getAllFixtures();
            log.info("Successfully fetched {} fixtures from the API, refreshing the DB", fixtureResponses.size());
            List<Fixture> fixtures = fixtureResponses.stream().map(mapper::fromAPIResponse).toList();
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
            int currentGameWeek = fixtureClient.getCurrentGameWeek();
        } catch (Exception e) {
            log.error("Exception thrown when updating gameweeks", e);
        }
    }

}
