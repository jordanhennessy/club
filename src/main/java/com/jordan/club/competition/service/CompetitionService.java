package com.jordan.club.competition.service;

import com.jordan.club.common.service.CommonService;
import com.jordan.club.competition.dto.CompetitionDTO;
import com.jordan.club.competition.entity.Competition;
import com.jordan.club.competition.mapper.CompetitionMapper;
import com.jordan.club.competition.repository.CompetitionRepository;
import com.jordan.club.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Objects.nonNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompetitionService implements CommonService<CompetitionDTO> {

    private final CompetitionRepository competitionRepository;
    private final CompetitionMapper competitionMapper;

    @Override
    public List<CompetitionDTO> getAll() {
        return competitionRepository.findAll().stream().map(competitionMapper::toDTO).toList();
    }

    @Override
    public CompetitionDTO getById(Long id) {
        return competitionMapper.toDTO(competitionRepository.findById(id).orElseThrow());
    }

    @Override
    public CompetitionDTO save(CompetitionDTO newCompetition) {
        Competition savedCompetition = competitionRepository.save(competitionMapper.fromDTO(newCompetition));
        return competitionMapper.toDTO(savedCompetition);
    }

    @Override
    public CompetitionDTO update(Long id, CompetitionDTO updatedDTO) {
        Competition existingCompetition = competitionRepository.findById(id).orElseThrow();

        if (nonNull(updatedDTO.getName())) {
            existingCompetition.setName(updatedDTO.getName());
        }

        if (nonNull(updatedDTO.getDescription())) {
            existingCompetition.setDescription(updatedDTO.getDescription());
        }

        return competitionMapper.toDTO(competitionRepository.save(existingCompetition));
    }

    @Override
    public void delete(Long id) {
        Competition existingCompetition = competitionRepository.findById(id).orElseThrow();
        competitionRepository.delete(existingCompetition);
    }
}
