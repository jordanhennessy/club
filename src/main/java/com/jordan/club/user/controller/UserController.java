package com.jordan.club.user.controller;

import com.jordan.club.common.controller.CommonController;
import com.jordan.club.user.dto.UserDTO;
import com.jordan.club.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements CommonController<UserDTO> {

    private final UserService userService;

    @Override
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO user) {
        UserDTO created = userService.save(user);
        return ResponseEntity.status(CREATED).body(created);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(
            @PathVariable("id") Long id,
            @RequestBody UserDTO updatedUser
    ) {
        UserDTO updated = userService.update(id, updatedUser);
        return ResponseEntity.ok(updated);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
