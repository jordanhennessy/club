package com.jordan.club.competition.service;

import com.jordan.club.common.exception.ValidationException;
import com.jordan.club.competition.dto.request.UpdateCompetitionRequest;
import com.jordan.club.competition.dto.response.CompetitionResponse;
import com.jordan.club.competition.dto.request.CreateCompetitionRequest;
import com.jordan.club.competition.entity.Competition;
import com.jordan.club.competition.enums.CompetitionStatus;
import com.jordan.club.competition.mapper.CompetitionMapper;
import com.jordan.club.competition.repository.CompetitionRepository;
import com.jordan.club.gameweek.service.GameWeekService;
import com.jordan.club.user.entity.User;
import com.jordan.club.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.NoSuchElementException;

import static com.jordan.club.competition.enums.CompetitionStatus.ACTIVE;
import static com.jordan.club.competition.enums.CompetitionStatus.DRAFT;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompetitionService {

    private final CompetitionRepository repository;
    private final CompetitionMapper mapper;
    private final UserRepository userRepository;
    private final GameWeekService gameWeekService;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int JOIN_CODE_LENGTH = 8;
    private static final SecureRandom RANDOM = new SecureRandom();

    public List<CompetitionResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    public CompetitionResponse getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow();
    }

    @Transactional
    public CompetitionResponse save(CreateCompetitionRequest newCompetition) {

        Integer startGameWeek;
        if (nonNull(newCompetition.getStartGameWeek())) {
            validateStartGameWeek(newCompetition.getStartGameWeek());
            startGameWeek = newCompetition.getStartGameWeek();
        } else {
            startGameWeek = gameWeekService.getNextGameWeek().getGameWeek();
        }

        Competition competition = mapper.mapCreateRequestToEntity(newCompetition);
        competition.setJoinCode(generateUniqueJoinCode());
        competition.setStatus(DRAFT);
        competition.setStartGameWeek(startGameWeek);
        Competition savedCompetition = repository.save(competition);

        log.info("Created new competition, {}", savedCompetition);
        return mapper.toDTO(savedCompetition);
    }

    @Transactional
    public CompetitionResponse update(Long id, UpdateCompetitionRequest updatedCompetition) {
        Competition existingCompetition = repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Unable to find competition with id=" + id));

        if (nonNull(updatedCompetition.getName())) {
            existingCompetition.setName(updatedCompetition.getName());
        }

        if (nonNull(updatedCompetition.getDescription())) {
            existingCompetition.setDescription(updatedCompetition.getDescription());
        }

        if (nonNull(updatedCompetition.getStartGameWeek())) {
            validateStartGameWeek(updatedCompetition.getStartGameWeek());
            existingCompetition.setStartGameWeek(updatedCompetition.getStartGameWeek());
        }

        Competition savedCompetition = repository.save(existingCompetition);

        log.info("Updated competition with id={}", id);
        return mapper.toDTO(savedCompetition);
    }

    @Transactional
    public void delete(Long id) {
        Competition competition = repository.findById(id).orElseThrow();

        // Clear participants before deletion to maintain referential integrity
        competition.getParticipants().clear();
        repository.save(competition);

        repository.delete(competition);
        log.info("Deleted competition with id: {}", id);
    }

    @Transactional
    public CompetitionResponse joinCompetition(String joinCode, Long userId) {
        Competition competition = repository.findByJoinCode(joinCode)
                .orElseThrow(() ->
                        new NoSuchElementException("Unable to find competition with the join code " + joinCode));

        User user = userRepository.findById(userId).
                orElseThrow(() -> new NoSuchElementException("Unable to find user with id " + userId));

        if (competition.getParticipants().contains(user)) {
            log.warn("userId={} is already a participant of the competition with joinCode={}",
                    userId, joinCode);
            return mapper.toDTO(competition);
        }

        competition.addParticipant(user);

        Competition updatedCompetition = repository.save(competition);

        log.info("userId={} has joined competition (id={}) using join code", userId, updatedCompetition.getId());
        return mapper.toDTO(updatedCompetition);
    }

    private void validateStartGameWeek(Integer startGameWeek) {
        boolean isDeadlinePassed = gameWeekService.isDeadlinePassed(startGameWeek);

        if (isDeadlinePassed) {
            throw new ValidationException("Unable to create new competition, deadline has passed for gameweek " + startGameWeek);
        }
    }

    private String generateUniqueJoinCode() {
        String joinCode;
        int attempts = 0;
        final int MAX_ATTEMPTS = 10;

        do {
            joinCode = generateJoinCode();
            attempts++;

            if (attempts >= MAX_ATTEMPTS) {
                throw new RuntimeException("Failed to generate unique join code after " + MAX_ATTEMPTS + " attempts");
            }
        } while (repository.existsByJoinCode(joinCode));

        return joinCode;
    }

    private String generateJoinCode() {
        StringBuilder code = new StringBuilder(JOIN_CODE_LENGTH);
        for (int i = 0; i < JOIN_CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return code.toString();
    }
}
