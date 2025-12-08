package com.jordan.club.competition.service;

import com.jordan.club.common.exception.ValidationException;
import com.jordan.club.common.service.CommonService;
import com.jordan.club.competition.dto.CompetitionDTO;
import com.jordan.club.competition.entity.Competition;
import com.jordan.club.competition.enums.CompetitionStatus;
import com.jordan.club.competition.mapper.CompetitionMapper;
import com.jordan.club.competition.repository.CompetitionRepository;
import com.jordan.club.user.entity.User;
import com.jordan.club.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompetitionService implements CommonService<CompetitionDTO> {

    private final CompetitionRepository repository;
    private final CompetitionMapper mapper;
    private final UserRepository userRepository;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int JOIN_CODE_LENGTH = 8;
    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public List<CompetitionDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public CompetitionDTO getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow();
    }

    @Override
    @Transactional
    public CompetitionDTO save(CompetitionDTO newDTO) {
        validateName(newDTO.getName(), null);

        // Auto-generate join code if not provided
        if (isNull(newDTO.getJoinCode()) || newDTO.getJoinCode().isBlank()) {
            newDTO.setJoinCode(generateUniqueJoinCode());
        } else {
            validateJoinCode(newDTO.getJoinCode(), null);
        }

        // Set default status if not provided
        if (isNull(newDTO.getStatus())) {
            newDTO.setStatus(CompetitionStatus.DRAFT);
        }

        Competition competition = mapper.fromDTO(newDTO);
        Competition savedCompetition = repository.save(competition);

        log.info("Created new competition with name: {} and join code: {}",
                savedCompetition.getName(), savedCompetition.getJoinCode());
        return mapper.toDTO(savedCompetition);
    }

    @Override
    @Transactional
    public CompetitionDTO update(Long id, CompetitionDTO updatedDTO) {
        Competition existingCompetition = repository.findById(id).orElseThrow();

        // Validate name if changed
        if (!existingCompetition.getName().equals(updatedDTO.getName())) {
            validateName(updatedDTO.getName(), id);
        }

        // Validate join code if changed
        if (!existingCompetition.getJoinCode().equals(updatedDTO.getJoinCode())) {
            validateJoinCode(updatedDTO.getJoinCode(), id);
        }

        existingCompetition.setName(updatedDTO.getName());
        existingCompetition.setDescription(updatedDTO.getDescription());
        existingCompetition.setStatus(updatedDTO.getStatus());
        existingCompetition.setStartDate(updatedDTO.getStartDate());
        existingCompetition.setEndDate(updatedDTO.getEndDate());
        existingCompetition.setJoinCode(updatedDTO.getJoinCode());

        // Note: participants are NOT updated via the standard update endpoint
        // Use the joinCompetition method instead

        Competition savedCompetition = repository.save(existingCompetition);

        log.info("Updated competition with id: {}", id);
        return mapper.toDTO(savedCompetition);
    }

    @Override
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
    public CompetitionDTO joinCompetition(Long competitionId, Long userId, String joinCode) {
        // Validate inputs
        if (isNull(joinCode) || joinCode.isBlank()) {
            throw new ValidationException("Join code cannot be null or empty");
        }

        // Find competition
        Competition competition = repository.findById(competitionId)
                .orElseThrow();

        // Validate join code
        if (!competition.getJoinCode().equals(joinCode)) {
            throw new ValidationException("Invalid join code");
        }

        // Find user
        User user = userRepository.findById(userId)
                .orElseThrow();

        // Check if user is already a participant
        if (competition.getParticipants().contains(user)) {
            log.info("User {} is already a participant in competition {}", userId, competitionId);
            return mapper.toDTO(competition);
        }

        // Add user to competition using helper method (maintains bidirectional relationship)
        competition.addParticipant(user);

        Competition savedCompetition = repository.save(competition);

        log.info("User {} joined competition {} with join code", userId, competitionId);
        return mapper.toDTO(savedCompetition);
    }

    private void validateName(String name, Long excludeId) {
        if (isNull(name) || name.isBlank()) {
            throw new ValidationException("Name cannot be null or empty");
        }

        if (repository.existsByName(name)) {
            if (excludeId != null) {
                repository.findByName(name).ifPresent(competition -> {
                    if (!competition.getId().equals(excludeId)) {
                        throw new ValidationException("Competition name already exists: " + name);
                    }
                });
            } else {
                throw new ValidationException("Competition name already exists: " + name);
            }
        }
    }

    private void validateJoinCode(String joinCode, Long excludeId) {
        if (isNull(joinCode) || joinCode.isBlank()) {
            throw new ValidationException("Join code cannot be null or empty");
        }

        if (repository.existsByJoinCode(joinCode)) {
            if (excludeId != null) {
                repository.findByJoinCode(joinCode).ifPresent(competition -> {
                    if (!competition.getId().equals(excludeId)) {
                        throw new ValidationException("Join code already exists: " + joinCode);
                    }
                });
            } else {
                throw new ValidationException("Join code already exists: " + joinCode);
            }
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
