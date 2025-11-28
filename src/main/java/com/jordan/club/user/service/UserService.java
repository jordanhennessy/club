package com.jordan.club.user.service;

import com.jordan.club.user.entity.User;
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

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User update(User user, Long id) {
        User existingUser = userRepository.findById(id).orElseThrow();

        if (nonNull(user.getName())) {
            existingUser.setName(user.getName());
        }

        return userRepository.save(existingUser);
    }

    public void delete(Long id) {
        User existingUser = userRepository.findById(id).orElseThrow();
        userRepository.deleteById(id);
    }


}
