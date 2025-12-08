package com.jordan.club.fixture.service;

import com.jordan.club.common.exception.ValidationException;
import com.jordan.club.fixture.client.FixtureClient;
import com.jordan.club.fixture.dto.FixtureDTO;
import com.jordan.club.fixture.enums.FixtureStatus;
import com.jordan.club.fixture.model.FixtureResponse;
import com.jordan.club.fixture.mapper.FixtureMapper;
import com.jordan.club.fixture.repository.FixtureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class FixtureService {

    private final FixtureRepository repository;
    private final FixtureMapper mapper;
    private final FixtureClient fixtureClient;

    public List<FixtureDTO> getFixturesByGameWeekAndStatus(Integer gameWeek, String status) {
        if (isNull(gameWeek) && isNull(status)) {
            return repository.findAll().stream().map(mapper::toDTO).toList();
        }

        if (isNull(status)) {
            return repository.findByGameWeek(gameWeek).stream().map(mapper::toDTO).toList();
        }

        FixtureStatus fixtureStatus = FixtureStatus.getByName(status);

        if (isNull(fixtureStatus)) {
            throw new ValidationException("Invalid status provided for FixtureStatus: " + status);
        }

        if(isNull(gameWeek)) {
            return repository.findByStatus(fixtureStatus).stream().map(mapper::toDTO).toList();
        }

        return repository.findByGameWeekAndStatus(gameWeek, fixtureStatus).stream().map(mapper::toDTO).toList();
    }

    public List<FixtureDTO> getFixturesByGameWeek(Integer gameWeek) {
        if (isNull(gameWeek)) {
            return repository.findAll().stream().map(mapper::toDTO).toList();
        }

        return repository.findByGameWeek(gameWeek).stream().map(mapper::toDTO).toList();
    }

    public List<FixtureDTO> getFixturesForCurrentGameWeek() {
        try {
            log.info("Fetching current game week");
            int currentGameWeek = fixtureClient.getCurrentGameWeek();
            return getFixturesByGameWeek(currentGameWeek);
        } catch (Exception e) {
            log.error("Exception thrown when fetching current game week", e);
        }
        return Collections.emptyList();
    }

}
