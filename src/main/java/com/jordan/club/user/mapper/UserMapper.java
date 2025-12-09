package com.jordan.club.user.mapper;

import com.jordan.club.user.dto.response.UserResponse;
import com.jordan.club.user.dto.request.CreateUserRequest;
import com.jordan.club.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User fromDTO(UserResponse dto) {
        return User.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .username(dto.getUsername())
                .build();
    }

    public UserResponse mapEntityToResponse(User entity) {
        return UserResponse.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .username(entity.getUsername())
                .build();
    }

    public User mapCreateRequestToEntity(CreateUserRequest request) {
        return User.builder()
                .username(request.getName())
                .email(request.getEmail())
                .build();
    }

}
