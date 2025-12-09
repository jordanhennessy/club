package com.jordan.club.gameweek.service;

import com.jordan.club.common.event.FixtureDataRefreshEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.event.TransactionPhase.BEFORE_COMMIT;


@Slf4j
@Component
@RequiredArgsConstructor
public class GameWeekSyncScheduler {

    private final GameWeekSyncService gameWeekSyncService;

    @TransactionalEventListener(classes = FixtureDataRefreshEvent.class, phase = BEFORE_COMMIT)
    public void scheduleGameWeekSync() {
        log.info("Updating GameWeek data...");
        gameWeekSyncService.syncGameWeekData();
        log.info("Successfully synced GameWeek data");
    }
}
