package com.jordan.club.gameweek.service;

import com.jordan.club.common.exception.ValidationException;
import com.jordan.club.common.service.CommonService;
import com.jordan.club.gameweek.dto.GameWeekDTO;
import com.jordan.club.gameweek.entity.GameWeek;
import com.jordan.club.gameweek.enums.GameWeekStatus;
import com.jordan.club.gameweek.mapper.GameWeekMapper;
import com.jordan.club.gameweek.repository.GameWeekRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.jordan.club.gameweek.enums.GameWeekStatus.UPCOMING;
import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameWeekService {

    @Value("${game_week.count:38}")
    private int expectedNumOfGameWeeks;

    private final GameWeekRepository gameWeekRepository;
    private final GameWeekMapper gameWeekMapper;

    @EventListener(ApplicationReadyEvent.class)
    public void seedGameWeeks() {
        if (!gameWeeksExist()) {
            log.info("GameWeeks not initialised, seeding now...");
            initialiseGameWeeks();
        } else {
            log.info("GameWeeks already exist in table");
        }
    }

    public List<GameWeekDTO> getAll() {
        return gameWeekRepository.findAll().stream().map(gameWeekMapper::toDTO).toList();
    }

    public GameWeekDTO getById(Long id) {
        return gameWeekRepository.findById(id).map(gameWeekMapper::toDTO).orElseThrow();
    }

    public List<GameWeekDTO> getByStatus(String status) {
        if (isNull(status)) {
            return getAll();
        }

        GameWeekStatus gameWeekStatus = GameWeekStatus.getByName(status);

        if (isNull(gameWeekStatus)) {
            throw new ValidationException("Invalid value for GameWeekStatus: " + status);
        }

        List<GameWeek> gameWeeks = gameWeekRepository.findByStatus(gameWeekStatus);
        return gameWeeks.stream().map(gameWeekMapper::toDTO).toList();
    }

    public void update(GameWeekDTO gameWeekDTO) {
        GameWeek gameWeek = gameWeekMapper.fromDTO(gameWeekDTO);
        gameWeekRepository.save(gameWeek);
    }

    private boolean gameWeeksExist() {
        return gameWeekRepository.count() == expectedNumOfGameWeeks;
    }

    private void initialiseGameWeeks() {
        for (int gameWeek = 1; gameWeek <= expectedNumOfGameWeeks; gameWeek++) {
            GameWeek gameWeekEntity = buildGameWeek(gameWeek);
            gameWeekRepository.save(gameWeekEntity);
        }
    }

    private GameWeek buildGameWeek(int gameWeek) {
        return GameWeek.builder()
                .gameWeek(gameWeek)
                .status(UPCOMING)
                .build();
    }
}
