package com.jordan.club.user.controller;

import com.jordan.club.common.controller.CommonController;
import com.jordan.club.user.dto.UserDTO;
import com.jordan.club.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController implements CommonController<UserDTO> {

    private final UserService userService;

    @Override
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok().body(userService.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(userService.getById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO newUser) {
        return ResponseEntity.status(CREATED).body(userService.save(newUser));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable("id") Long id,
                                          @RequestBody UserDTO updatedUser) {
        return ResponseEntity.ok().body(userService.update(updatedUser, id));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.ok().body(String.format("Successfully deleted user with id=%s"));
    }
}
