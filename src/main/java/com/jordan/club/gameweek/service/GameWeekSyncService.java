package com.jordan.club.gameweek.service;

import com.jordan.club.fixture.dto.FixtureDTO;
import com.jordan.club.fixture.service.FixtureService;
import com.jordan.club.gameweek.dto.GameWeekDTO;
import com.jordan.club.gameweek.entity.GameWeek;
import com.jordan.club.gameweek.enums.GameWeekStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.jordan.club.gameweek.enums.GameWeekStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameWeekSyncService {

    private final FixtureService fixtureService;
    private final GameWeekService gameWeekService;

    @Transactional
    public void syncGameWeekData() {
        if (!gameWeekService.gameWeeksExist()) {
            gameWeekService.seedGameWeeks();
        }
        List<GameWeekDTO> upcomingGameWeeks = gameWeekService.getByStatus(UPCOMING.name());
        updateDeadlines(upcomingGameWeeks);
        updateStatuses(upcomingGameWeeks);
    }

    public void updateDeadlines(List<GameWeekDTO> gameWeeks) {
        gameWeeks.forEach(gameWeek -> {
            List<FixtureDTO> fixtures = fixtureService.getFixturesByGameWeek(gameWeek.getGameWeek());
            LocalDateTime deadline = gameWeekService.resolveDeadline(fixtures);
            gameWeek.setDeadline(deadline);
            gameWeekService.update(gameWeek);
        });
    }

    public void updateStatuses(List<GameWeekDTO> gameWeeks) {
        gameWeeks.forEach(gameWeek -> {
            List<FixtureDTO> fixtures = fixtureService.getFixturesByGameWeek(gameWeek.getGameWeek());
            GameWeekStatus status = gameWeekService.resolveGameWeekStatus(fixtures);
            gameWeek.setStatus(status);
            gameWeekService.update(gameWeek);
        });
    }

}
