package com.jordan.club.user.mapper;

import com.jordan.club.common.mapper.CommonMapper;
import com.jordan.club.user.dto.UserDTO;
import com.jordan.club.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements CommonMapper<User, UserDTO> {

    @Override
    public User fromDTO(UserDTO dto) {
        return User.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .username(dto.getUsername())
                .build();
    }

    @Override
    public UserDTO toDTO(User entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .username(entity.getUsername())
                .build();
    }
}
