package com.jordan.club.match.service;

import com.jordan.club.match.dto.Match;
import com.jordan.club.match.mapper.MatchMapper;
import com.jordan.club.match.repository.FixtureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final FixtureRepository repository;
    private final MatchMapper mapper;

    public List<Match> getMatchesByGameWeek(Integer gameWeek) {
        if (isNull(gameWeek)) {
            return repository.findAll().stream().map(mapper::toDTO).toList();
        }

        return repository.findByGameWeek(gameWeek).stream().map(mapper::toDTO).toList();
    }

}
