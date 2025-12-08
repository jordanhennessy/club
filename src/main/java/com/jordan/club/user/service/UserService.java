package com.jordan.club.user.service;

import com.jordan.club.common.exception.ValidationException;
import com.jordan.club.common.service.CommonService;
import com.jordan.club.user.dto.UserDTO;
import com.jordan.club.user.entity.User;
import com.jordan.club.user.mapper.UserMapper;
import com.jordan.club.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements CommonService<UserDTO> {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public List<UserDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public UserDTO getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow();
    }

    @Override
    public UserDTO save(UserDTO newDTO) {
        validateEmail(newDTO.getEmail(), null);

        User user = mapper.fromDTO(newDTO);
        User savedUser = repository.save(user);

        log.info("Created new user with email: {}", savedUser.getEmail());
        return mapper.toDTO(savedUser);
    }

    @Override
    public UserDTO update(Long id, UserDTO updatedDTO) {
        User existingUser = repository.findById(id).orElseThrow();

        if (!existingUser.getEmail().equals(updatedDTO.getEmail())) {
            validateEmail(updatedDTO.getEmail(), id);
        }

        existingUser.setEmail(updatedDTO.getEmail());
        existingUser.setUsername(updatedDTO.getUsername());

        User savedUser = repository.save(existingUser);

        log.info("Updated user with id: {}", id);
        return mapper.toDTO(savedUser);
    }

    @Override
    public void delete(Long id) {
        User user = repository.findById(id).orElseThrow();
        repository.delete(user);
        log.info("Deleted user with id: {}", id);
    }

    private void validateEmail(String email, Long excludeId) {
        if (isNull(email) || email.isBlank()) {
            throw new ValidationException("Email cannot be null or empty");
        }

        if (repository.existsByEmail(email)) {
            if (excludeId != null) {
                repository.findByEmail(email).ifPresent(user -> {
                    if (!user.getId().equals(excludeId)) {
                        throw new ValidationException("Email already exists: " + email);
                    }
                });
            } else {
                throw new ValidationException("Email already exists: " + email);
            }
        }
    }
}
