package com.jordan.club.gameweek.service;

import com.jordan.club.common.exception.ValidationException;
import com.jordan.club.fixture.dto.FixtureDTO;
import com.jordan.club.fixture.enums.FixtureStatus;
import com.jordan.club.fixture.service.FixtureService;
import com.jordan.club.gameweek.dto.GameWeekDTO;
import com.jordan.club.gameweek.entity.GameWeek;
import com.jordan.club.gameweek.enums.GameWeekStatus;
import com.jordan.club.gameweek.mapper.GameWeekMapper;
import com.jordan.club.gameweek.repository.GameWeekRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static com.jordan.club.gameweek.enums.GameWeekStatus.*;
import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameWeekService {

    @Value("${game_week.count:38}")
    private int expectedNumOfGameWeeks;

    private final GameWeekRepository gameWeekRepository;
    private final GameWeekMapper gameWeekMapper;
    private final FixtureService fixtureService;

    public List<GameWeekDTO> getAll() {
        return gameWeekRepository.findAll().stream().map(gameWeekMapper::toDTO).toList();
    }

    public GameWeekDTO getById(Long id) {
        return gameWeekRepository.findById(id).map(gameWeekMapper::toDTO).orElseThrow();
    }

    public GameWeekDTO getNextGameWeek() {
        List<GameWeek> upcomingGameWeeks = gameWeekRepository.findAllByStatus(GameWeekStatus.UPCOMING);
        GameWeek nextGameWeek = upcomingGameWeeks.stream()
                .min(Comparator.comparing(GameWeek::getGameWeek)).stream().
                findFirst().orElseThrow();
        return  gameWeekMapper.toDTO(nextGameWeek);
    }

    public List<GameWeekDTO> getByStatus(String status) {
        if (isNull(status)) {
            return getAll();
        }

        GameWeekStatus gameWeekStatus = GameWeekStatus.getByName(status);

        if (isNull(gameWeekStatus)) {
            throw new ValidationException("Invalid value for GameWeekStatus: " + status);
        }

        List<GameWeek> gameWeeks = gameWeekRepository.findAllByStatus(gameWeekStatus);
        return gameWeeks.stream().map(gameWeekMapper::toDTO).toList();
    }

    public void update(GameWeekDTO gameWeekDTO) {
        GameWeek gameWeek = gameWeekMapper.fromDTO(gameWeekDTO);
        gameWeekRepository.save(gameWeek);
    }

    public boolean gameWeeksExist() {
        return gameWeekRepository.count() == expectedNumOfGameWeeks;
    }

    public void seedGameWeeks() {
        for (int gameWeek = 1; gameWeek <= expectedNumOfGameWeeks; gameWeek++) {
            GameWeek gameWeekEntity = buildGameWeek(gameWeek);
            gameWeekRepository.save(gameWeekEntity);
        }
    }

    private GameWeek buildGameWeek(int gameWeek) {
        List<FixtureDTO> fixtures = fixtureService.getFixturesByGameWeek(gameWeek);
        GameWeekStatus status = resolveGameWeekStatus(fixtures);
        LocalDateTime deadline = resolveDeadline(fixtures);
        return GameWeek.builder()
                .gameWeek(gameWeek)
                .status(status)
                .deadline(deadline)
                .build();
    }

    public GameWeekStatus resolveGameWeekStatus(List<FixtureDTO> fixtures) {
        if (fixtures.stream().allMatch(fixture -> fixture.getStatus().equals(FixtureStatus.FINISHED))) {
            return COMPLETED;
        }

        if (fixtures.stream().anyMatch(fixture -> fixture.getStatus().equals(FixtureStatus.IN_PLAY))) {
            return ACTIVE;
        }

        return UPCOMING;
    }

    public LocalDateTime resolveDeadline(List<FixtureDTO> fixtures) {
        LocalDateTime firstKickOff = fixtures.stream().map(FixtureDTO::getKickOffTime)
                .min(LocalDateTime::compareTo).orElseThrow();
        return firstKickOff.minusHours(24);
    }
}
