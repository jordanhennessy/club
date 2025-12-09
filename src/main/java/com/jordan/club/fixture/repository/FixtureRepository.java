package com.jordan.club.fixture.repository;

import com.jordan.club.fixture.entity.Fixture;
import com.jordan.club.fixture.enums.FixtureStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FixtureRepository extends JpaRepository<Fixture, Long> {
    List<Fixture> findAllByGameWeek(int gameWeek);
    List<Fixture> findAllByGameWeekAndStatus(int gameWeek, FixtureStatus status);
    List<Fixture> findAllByStatus(FixtureStatus status);
}
