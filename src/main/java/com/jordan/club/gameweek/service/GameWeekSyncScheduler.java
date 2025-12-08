package com.jordan.club.gameweek.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class GameWeekSyncScheduler {

    private final GameWeekSyncService gameWeekSyncService;

    @Scheduled(initialDelay = 30, timeUnit = SECONDS)
    public void scheduleGameWeekSync() {
        log.info("Syncing GameWeek data...");
        gameWeekSyncService.syncGameWeekData();
        log.info("Successfully synced GameWeek data");
    }
}
