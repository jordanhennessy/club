package com.jordan.club.gameweek.service;

import com.jordan.club.fixture.dto.FixtureDTO;
import com.jordan.club.fixture.enums.FixtureStatus;
import com.jordan.club.fixture.service.FixtureService;
import com.jordan.club.gameweek.dto.GameWeekDTO;
import com.jordan.club.gameweek.enums.GameWeekStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static com.jordan.club.gameweek.enums.GameWeekStatus.*;
import static java.time.temporal.ChronoUnit.HOURS;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameWeekSyncService {

    private final FixtureService fixtureService;
    private final GameWeekService gameWeekService;

    @Transactional
    public void syncGameWeekData() {
        List<GameWeekDTO> gameWeeks = gameWeekService.getAll();

        gameWeeks.forEach(gameWeek -> {
            GameWeekStatus status = resolveGameWeekStatus(gameWeek);
            gameWeek.setStatus(status);
            gameWeekService.update(gameWeek);
        });
    }

    private GameWeekStatus resolveGameWeekStatus(GameWeekDTO gameWeek) {
        List<FixtureDTO> fixtures = fixtureService.getFixturesByGameWeek(gameWeek.getGameWeek());

        if (isAllFixturesCompleted(fixtures)) {
            return COMPLETED;
        }

        if (isDeadlinePassed(fixtures)) {
            return ACTIVE;
        }

        return UPCOMING;
    }

    private boolean isDeadlinePassed(List<FixtureDTO> fixtures) {
        List<LocalDateTime> kickOffTimes = fixtures.stream().map(FixtureDTO::getKickOffTime).toList();
        LocalDateTime firstKickOff = kickOffTimes.stream().min(LocalDateTime::compareTo).orElseThrow();
        LocalDateTime deadline = firstKickOff.minusHours(24);
        return LocalDateTime.now().isAfter(deadline);
    }

    private boolean isAllFixturesCompleted(List<FixtureDTO> fixtures) {
        return fixtures.stream().allMatch(fixture -> fixture.getStatus().equals(FixtureStatus.FINISHED));
    }

}
