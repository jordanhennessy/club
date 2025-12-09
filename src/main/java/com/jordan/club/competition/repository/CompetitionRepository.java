package com.jordan.club.competition.repository;

import com.jordan.club.competition.entity.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {

    Optional<Competition> findByName(String name);
    boolean existsByName(String name);
    Optional<Competition> findByJoinCode(String joinCode);
    boolean existsByJoinCode(String joinCode);
}
