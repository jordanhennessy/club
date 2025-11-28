package com.jordan.club.user.service;

import com.jordan.club.user.dto.UserDTO;
import com.jordan.club.user.entity.User;
import com.jordan.club.user.mapper.UserMapper;
import com.jordan.club.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDTO> getAll() {
        return userRepository.findAll().stream().map(userMapper::toDTO).toList();
    }

    public UserDTO getById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return userMapper.toDTO(user);
    }

    public User save(UserDTO userDTO) {
        return userRepository.save(userMapper.fromDTO(userDTO));
    }

    public UserDTO update(UserDTO userDTO, Long id) {
        User existingUser = userRepository.findById(id).orElseThrow();

        if (nonNull(userDTO.getName())) {
            existingUser.setName(userDTO.getName());
        }

        User savedUser = userRepository.save(existingUser);
        return userMapper.toDTO(savedUser);
    }

    public void delete(Long id) {
        User existingUser = userRepository.findById(id).orElseThrow();
        userRepository.delete(existingUser);
    }


}
