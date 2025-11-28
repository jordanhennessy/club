package com.jordan.club.user.controller;

import com.jordan.club.user.entity.User;
import com.jordan.club.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/status")
    public String getStatus() {
        return "OK";
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getUserById() {
        return null;
    }

    @PostMapping
    public User addUser() {
        return null;
    }

    @PutMapping("/{id}")
    public User updateUser() {
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteUser() {
        
    }

}
