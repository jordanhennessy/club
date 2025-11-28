package com.jordan.club.competition.service;

import com.jordan.club.competition.dto.CompetitionDTO;
import com.jordan.club.competition.entity.Competition;
import com.jordan.club.competition.mapper.CompetitionMapper;
import com.jordan.club.competition.repository.CompetitionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Objects.nonNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final CompetitionMapper competitionMapper;

    public List<CompetitionDTO> getAll() {
        return competitionRepository.findAll()
                .stream().map(competitionMapper::toDTO).toList();
    }

    public CompetitionDTO getById(Long id) {
        Competition existingCompetition = competitionRepository.findById(id).orElseThrow();
        return competitionMapper.toDTO(existingCompetition);
    }

    public CompetitionDTO save(CompetitionDTO competitionDTO) {
        Competition newCompetition = competitionMapper.fromDTO(competitionDTO);
        Competition savedCompetition = competitionRepository.save(newCompetition);
        return competitionMapper.toDTO(savedCompetition);
    }

    public CompetitionDTO update(Long id, CompetitionDTO newCompetition) {
        Competition existingCompetition = competitionRepository.findById(id).orElseThrow();

        if (nonNull(newCompetition.getName())) {
            existingCompetition.setName(newCompetition.getName());
        }

        if (nonNull(newCompetition.getDescription())) {
            existingCompetition.setDescription(newCompetition.getDescription());
        }

        return competitionMapper.toDTO(competitionRepository.save(existingCompetition));
    }

    public void delete(Long id) {
        Competition existingCompetition = competitionRepository.findById(id).orElseThrow();
        competitionRepository.delete(existingCompetition);
    }

}
