package com.jordan.club.match.service;

import com.jordan.club.match.client.MatchClient;
import com.jordan.club.match.model.Match;
import com.jordan.club.match.mapper.MatchMapper;
import com.jordan.club.match.repository.FixtureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchService {

    private final FixtureRepository repository;
    private final MatchMapper mapper;
    private final MatchClient matchClient;

    public List<Match> getMatchesByGameWeek(Integer gameWeek) {
        if (isNull(gameWeek)) {
            return repository.findAll().stream().map(mapper::toDTO).toList();
        }

        return repository.findByGameWeek(gameWeek).stream().map(mapper::toDTO).toList();
    }

    public List<Match> getMatchesForCurrentGameWeek() {
        try {
            log.info("Fetching current game week");
            int currentGameWeek = matchClient.getCurrentGameWeek();
            return getMatchesByGameWeek(currentGameWeek);
        } catch (Exception e) {
            log.error("Exception thrown when fetching current game week", e);
        }
        return Collections.emptyList();
    }

}
