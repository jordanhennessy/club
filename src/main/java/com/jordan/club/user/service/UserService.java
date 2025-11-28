package com.jordan.club.user.service;

import com.jordan.club.common.service.CommonService;
import com.jordan.club.user.dto.UserDTO;
import com.jordan.club.user.entity.User;
import com.jordan.club.user.mapper.UserMapper;
import com.jordan.club.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements CommonService<UserDTO> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDTO> getAll() {
        return userRepository.findAll().stream().map(userMapper::toDTO).toList();
    }

    @Override
    public UserDTO getById(Long id) {
        return userMapper.toDTO(userRepository.findById(id).orElseThrow());
    }

    @Override
    public UserDTO save(UserDTO newUser) {
        User savedUser = userRepository.save(userMapper.fromDTO(newUser));
        return userMapper.toDTO(savedUser);
    }

    @Override
    public UserDTO update(Long id, UserDTO updatedUser) {
        User existingUser = userRepository.findById(id).orElseThrow();

        if (nonNull(updatedUser.getName())) {
            existingUser.setName(updatedUser.getName());
        }

        return userMapper.toDTO(userRepository.save(existingUser));
    }

    @Override
    public void delete(Long id) {
        User existingUser = userRepository.findById(id).orElseThrow();
        userRepository.delete(existingUser);
    }
}
