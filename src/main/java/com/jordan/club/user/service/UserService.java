package com.jordan.club.user.service;

import com.jordan.club.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    public List<User> getAll() {
        return Collections.emptyList();
    }

}
