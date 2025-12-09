package com.jordan.club.user.service;

import com.jordan.club.common.exception.ValidationException;
import com.jordan.club.user.dto.request.UpdateUserRequest;
import com.jordan.club.user.dto.response.UserResponse;
import com.jordan.club.user.dto.request.CreateUserRequest;
import com.jordan.club.user.entity.User;
import com.jordan.club.user.mapper.UserMapper;
import com.jordan.club.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public List<UserResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::mapEntityToResponse)
                .toList();
    }

    public UserResponse getById(Long id) {
        return repository.findById(id)
                .map(mapper::mapEntityToResponse)
                .orElseThrow();
    }

    public UserResponse save(UserResponse newDTO) {
        validateEmail(newDTO.getEmail(), null);

        User user = mapper.fromDTO(newDTO);
        User savedUser = repository.save(user);

        log.info("Created new user with email: {}", savedUser.getEmail());
        return mapper.mapEntityToResponse(savedUser);
    }

    public UserResponse save(CreateUserRequest newUser) {
        User newUserEntity = mapper.mapCreateRequestToEntity(newUser);
        User savedUser = repository.save(newUserEntity);

        log.info("New user created, id={}, username={}, email={}",
                savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
        return mapper.mapEntityToResponse(savedUser);
    }

    public UserResponse update(Long id, UpdateUserRequest updatedUser) {
        User existingUser = repository.findById(id).orElseThrow();

        if (nonNull(updatedUser.getEmail())) {
            validateEmail(updatedUser.getEmail(), id);
            existingUser.setEmail(updatedUser.getEmail());
        }

        if (nonNull(updatedUser.getName())) {
            existingUser.setUsername(updatedUser.getName());
        }

        User savedUser = repository.save(existingUser);

        log.info("Updated user with id: {}", id);
        return mapper.mapEntityToResponse(savedUser);
    }

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
