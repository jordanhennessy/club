package com.jordan.club.gameweek.repository;

import com.jordan.club.gameweek.entity.GameWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameWeekRepository extends JpaRepository<GameWeek, Long> {}
