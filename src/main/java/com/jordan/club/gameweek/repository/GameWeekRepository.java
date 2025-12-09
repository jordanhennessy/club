package com.jordan.club.gameweek.repository;

import com.jordan.club.gameweek.entity.GameWeek;
import com.jordan.club.gameweek.enums.GameWeekStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameWeekRepository extends JpaRepository<GameWeek, Long> {
    List<GameWeek> findAllByStatus(GameWeekStatus status);
    Optional<GameWeek> findByGameWeek(Integer gameWeek);
}
