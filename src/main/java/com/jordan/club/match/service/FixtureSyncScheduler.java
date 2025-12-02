package com.jordan.club.match.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static java.util.concurrent.TimeUnit.HOURS;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class FixtureSyncScheduler {

    private final FixtureSyncService fixtureSyncService;

    @Scheduled(fixedRate = 1, timeUnit = HOURS)
    public void scheduleFixtureSync() {
        fixtureSyncService.syncMatchData();
    }

}
